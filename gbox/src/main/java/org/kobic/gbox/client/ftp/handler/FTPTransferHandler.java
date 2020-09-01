package org.kobic.gbox.client.ftp.handler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.common.TimeChecker;
import org.kobic.gbox.client.controller.FTPClientServiceController;
import org.kobic.gbox.client.fire.event.FileTransferInitFireEvent;
import org.kobic.gbox.client.fire.event.FileTransferStatusFireEvent;
import org.kobic.gbox.client.fire.event.RemoteFileListRefreshEvent;
import org.kobic.gbox.client.fire.event.StorageUsageFireEvent;
import org.kobic.gbox.client.model.TableTransferModel;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;
import org.kobic.gbox.client.utils.FTPUtils;
import org.kobic.gbox.utils.client.GBoxUtilsClient;
import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.HandlerManager;

import javafx.application.Platform;
import javafx.fxml.Initializable;

public class FTPTransferHandler extends Thread implements Initializable {

	private HandlerManager eventBus;

	private Member member;
	private FTPClient client;

	private File file;
	private List<String> transferFile;

	private Map<String, TableTransferModel> map;
	private TimeChecker mTimeChecker;

	private String id;
	
	private int type;
	private double totalSpeed = 0;
	private long totalSize;

	public FTPTransferHandler() {

	}

	public FTPTransferHandler(HandlerManager eventBus, File file, FTPClient client, Member member, int type, String id) {

		this.eventBus = eventBus;
		this.file = file;
		this.client = client;
		this.member = member;
		this.id = id;
		this.type = type;

		map = new HashMap<String, TableTransferModel>();
		mTimeChecker = new TimeChecker();
		transferFile = new ArrayList<String>();
	}

	private void initInfo(File file) {

		final TableTransferModel tm = new TableTransferModel(id, Constants.UPLOAD, "F", file.getName(),
				"Waiting", CommonsUtil.getInstance().humanReadableByteCount(file.length(), true),
				file.getAbsolutePath(), -1.0, "0 MB", 0L, 0L, file.length());

		map.put(file.getAbsolutePath(), tm);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				eventBus.fireEvent(new FileTransferInitFireEvent(tm));
			}
		});
	}

	@Override
	public void run(){
		// TODO Auto-generated method stub

		System.out.println(type);

		initInfo(file);

		FTPUtils ftp = new FTPUtils(client, map, mTimeChecker, eventBus);

		/**
		 * upload
		 */
		String source = RemotePresenter.path + "/" + file.getName();
		String target = RemotePresenter.path.replace(Constants.RAPIDANT_PATH, "") + "/";

		boolean isSuccess = false;

		if (file.isFile()) {
			isSuccess = ftp.upload(file, target);
		} else {
			isSuccess = ftp.uploadDirectory(file.getAbsolutePath(), target);
		}
		
		if (isSuccess == true) {
			transferFile.add(source);
			totalSize = file.length();
		}

		FTPClientServiceController.Util.getInstance().disconnection(client);

		/**
		 * finish
		 */
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// refresh
				eventBus.fireEvent(new StorageUsageFireEvent());

				for (Map.Entry<String, TableTransferModel> elem : map.entrySet()) {

					TableTransferModel tm = map.get(elem.getKey());
					tm.setTransferStatus(Constants.END);
					tm.setTransferSize(tm.getLength());
					tm.setProgress(1.0);
					tm.setStatus("Complete");

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							eventBus.fireEvent(new FileTransferStatusFireEvent(tm));
						}
					});
				}

				// HDFS 파일 동기화 요청
				GBoxUtilsClient gBoxUtilsClient = new GBoxUtilsClient();

				if (gBoxUtilsClient.isAlive()) {
					gBoxUtilsClient.multiDistCp(transferFile,
							RemotePresenter.path.replace(Constants.RAPIDANT_PATH, Constants.HDFS_ROOT_PATH));
				} else {
					CommonsUtil.getInstance().send("FATAL", 0, 0, 0, "82", "distcp deamon down", member,
							file.getName());
				}

				totalSpeed = ftp.getTotalSpeed();

				CommonsUtil.getInstance().send("COMPLETE", totalSize, totalSize, totalSpeed, "1818", "FTP", member,
						file.getName());

				eventBus.fireEvent(new RemoteFileListRefreshEvent());
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
}