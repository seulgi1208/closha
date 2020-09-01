package org.kobic.gbox.client.rapidant.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.component.ShowAlertDialogs;
import org.kobic.gbox.client.fire.event.FileTransferStatusFireEvent;
import org.kobic.gbox.client.fire.event.LocalFileListRefreshEvent;
import org.kobic.gbox.client.fire.event.RemoteFileListRefreshEvent;
import org.kobic.gbox.client.fire.event.StorageUsageFireEvent;
import org.kobic.gbox.client.model.TableTransferModel;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;
import org.kobic.gbox.utils.client.GBoxUtilsClient;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import rapidant.common.error.RapidantErrorCode;
import rapidant.model.client.event.transfer.RapidantClientTransferAutoconfigureEvent;
import rapidant.model.client.event.transfer.RapidantClientTransferFinishEvent;
import rapidant.model.client.event.transfer.RapidantClientTransferProgressEvent;
import rapidant.model.client.event.transfer.RapidantClientTransferStartEvent;
import rapidant.model.client.future.RapidantTransferFutureHandlerAdapter;

public class TransferFutureHandler extends RapidantTransferFutureHandlerAdapter {
	
	final static Logger logger = Logger.getLogger(TransferFutureHandler.class);
	
	private HandlerManager eventBus;
	private TransferResultSet rs;
	private String[] name;
	private int type;
	private Member member;

	private TableTransferModel tm;
	
	public TransferFutureHandler(){
		
	}
	
	public TransferFutureHandler(HandlerManager eventBus, String[] name, int type, TableTransferModel tm, Member member){
		this.eventBus = eventBus; 
		this.name = name;
		this.type = type;
		this.tm = tm;
		this.member = member;
	}

	public void configured(RapidantClientTransferAutoconfigureEvent event) {
		logger.info("Transfer configured: [" + event + "]");
	}
	
	public void started(RapidantClientTransferStartEvent event) {
		
		tm.setSize(String.valueOf(event.getTotalSize()));
		eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
	}
	
	public void progressed(RapidantClientTransferProgressEvent event) {
		
		logger.info(event.toString());

		tm.setSpeed(CommonsUtil.getInstance().humanReadableByteCount((long) event.getTransferSpeed(), true));
		tm.setTransferStatus(Constants.RUN);
		tm.setElapsedTime(event.getElapsedTime());
		tm.setTotalSize(event.getTotalSize());
		tm.setTransferSize(event.getTransferredSize());
		tm.setTransferSpeed(event.getTransferSpeed());
		tm.setProgressedRatio(event.getProgressedRatio());
		
		eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
	}
	
	public void finished(RapidantClientTransferFinishEvent event) {
		
		if (event.isSuccessful()) {
			
			//파일 전송 완료 후 파일 동기화 RCP 통신 실행.
			
			List<String> path = new ArrayList<String>();
			
			for(String f : name){
				logger.info(RemotePresenter.path.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH) + "\t" + RemotePresenter.path + "/" + f);
				path.add(RemotePresenter.path + "/" + f);
			}
			
			tm.setTransferStatus(Constants.END);
			tm.setTransferSize(event.getTotalSize());
			tm.setProgress(1.0);
			tm.setStatus("Complete");
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
					eventBus.fireEvent(new StorageUsageFireEvent());
				}
			});
			
			this.rs = new TransferResultSet(event.isSuccessful(),
					event.getCode(), event.getReason());
			
			GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();
			
			logger.info("gboxUtilsClient is Alive => " + gBoxUtilsClient.isAlive());
			
			if(type == Constants.UPLOAD){
				
				if(gBoxUtilsClient.isAlive()){
					
					logger.info(member.getMemberId() + " request upload path: [" + path + "]");
					
					logger.info("Hue's root path: ["+RemotePresenter.path.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH)+"]");
					
					gBoxUtilsClient.multiDistCp(path, RemotePresenter.path.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH));
					
				}else{
					CommonsUtil.getInstance().send("FATAL", event.getTotalSize(), event.getTotalSize(), event.getTransferSpeed(), "82", "distcp deamon down", member, StringUtils.join(name, ","));
				}
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						eventBus.fireEvent(new RemoteFileListRefreshEvent());
					}
				});
			}else{
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						eventBus.fireEvent(new LocalFileListRefreshEvent());
					}
				});
			}

			CommonsUtil.getInstance().send("COMPLETE", event.getTotalSize(), event.getTotalSize(), event.getTransferSpeed(), event.getCode().code(), event.getReason(), member, StringUtils.join(name, ","));
			gBoxUtilsClient.close();
			
			logger.info("[" + StringUtils.join(name, ",") + "] complete.");
			
		} else {
			
			CommonsUtil.getInstance().send("ERROR", event.getTotalSize(), 0L, event.getTransferSpeed(), event.getCode().code(), event.getReason(), member, StringUtils.join(name, ","));
			
			this.rs = new TransferResultSet(event.isSuccessful(),
					event.getCode(), event.getReason());
			
			if(RapidantErrorCode.CT02_USER_CANCELED.equals(event.getCode())){
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//상태 새로고침
						eventBus.fireEvent(new StorageUsageFireEvent());
						
						tm.setTransferStatus(Constants.CANCEL);
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
							}
						});
	
						eventBus.fireEvent(new RemoteFileListRefreshEvent());
					}
				});
				
				new ShowAlertDialogs(AlertType.ERROR, null, "Transmission the data file has successfully been suspended. Please try again. File List: " + Arrays.deepToString(name));
				
			}else{
				new ShowAlertDialogs(AlertType.ERROR, null, "An error has occurred while transferring a data file. Please try again. File List: " + Arrays.deepToString(name));
			}
		}
	}

	public TransferResultSet getResult() {
		return rs;
	}
}