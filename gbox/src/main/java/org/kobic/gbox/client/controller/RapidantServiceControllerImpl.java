package org.kobic.gbox.client.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kobic.gbox.client.common.Constants;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;
import org.kobic.gbox.client.transfer.local.presenter.LocalPresenter;
import org.kobic.gbox.client.transfer.remote.presenter.RemotePresenter;

import com.pennychecker.eventbus.HandlerManager;

import rapidant.common.future.RapidantFuture;
import rapidant.common.future.RapidantFutureHandler;
import rapidant.common.type.RapidantFairnessType;
import rapidant.common.type.RapidantGuaranteeType;
import rapidant.common.value.RapidantFairness;
import rapidant.model.client.RapidantClient;
import rapidant.model.client.command.file.RapidantClientRemoteFileDeleteCommand;
import rapidant.model.client.command.file.RapidantClientRemoteFileListCommand;
import rapidant.model.client.command.file.RapidantClientRemoteFileMkdirCommand;
import rapidant.model.client.command.file.RapidantClientRemoteFileRenameCommand;
import rapidant.model.client.command.transfer.RapidantClientReceiveCommand;
import rapidant.model.client.command.transfer.RapidantClientSendCommand;
import rapidant.model.client.event.connection.RapidantClientConnectEvent;
import rapidant.model.client.event.file.RapidantClientFileDeleteEvent;
import rapidant.model.client.event.file.RapidantClientFileListEvent;
import rapidant.model.client.event.file.RapidantClientFileMkdirEvent;
import rapidant.model.client.event.file.RapidantClientFileRenameEvent;
import rapidant.model.client.future.RapidantTransferFuture;
import rapidant.model.client.value.RapidantClientTCPConnection;
import rapidant.model.client.value.RapidantClientUDPConnection;
import rapidant.model.common.value.RapidantAnonymousAuthentication;
import rapidant.model.common.value.RapidantFile;

public class RapidantServiceControllerImpl implements RapidantServiceController{

	private static boolean isConnection = false;
	public boolean isMove = false;
	
	private RapidantClientRemoteFileListCommand remoteFileListCommand;
	private RapidantClientRemoteFileDeleteCommand remoteFileDeleteCommand;
	
	final static Logger logger = Logger.getLogger(RapidantServiceControllerImpl.class);

	@Override
	public boolean connection(RapidantClient client, String method) {
		// TODO Auto-generated method stub
		
		if(!isConnection){
			
			RapidantClientConnectEvent connect = null;
			
			if(method.equals("TCP/IP")){
				
				RapidantClientTCPConnection connection = new RapidantClientTCPConnection();
				connection.setHost(Constants.SERVER_ADDRESS);
				connection.setPort(Constants.SERVER_TCP_PORT);
				connection.setFairnessPort(Constants.SERVER_TCP_FAIRNESS_PORT);
				connection.setConnectionTimeout(10000);
				connection.setAuthentication(new RapidantAnonymousAuthentication());
				connection.setUseSecurity(false);
				connection.setSocketBufferSize(1024 * 1024 * 8);
				connection.setDiskBufferSize(1024 * 1024 * 1);
				connection.setNumberOfStream(10);
				
				connect = client.connect(connection).await();
				
			}else if(method.equals("UDP")){
				RapidantClientUDPConnection connection = new RapidantClientUDPConnection();
				connection.setHost(Constants.SERVER_ADDRESS).setPort(Constants.SERVER_UDP_PORT)
					.setFairnessPort(Constants.SERVER_UDP_FAIRNESS_PORT);
				connect = client.connect(connection).await();
			}
			
			if(connect.isSuccessful()){
				isConnection = true;
			}
			return isConnection;
		}else{
			return isConnection;
		}
	}
	
	@Override
	public boolean connection(RapidantClient client) {
		// TODO Auto-generated method stub
		if(!isConnection){
			RapidantClientTCPConnection connection = new RapidantClientTCPConnection();
			connection.setHost(Constants.SERVER_ADDRESS);
			connection.setPort(Constants.SERVER_TCP_PORT);
			connection.setFairnessPort(Constants.SERVER_TCP_FAIRNESS_PORT);
			connection.setConnectionTimeout(10000);
			connection.setAuthentication(new RapidantAnonymousAuthentication());
			connection.setUseSecurity(false);
			connection.setSocketBufferSize(1024 * 1024 * 8);
			connection.setDiskBufferSize(1024 * 1024 * 1);
			connection.setNumberOfStream(10);

			RapidantFuture<RapidantClientConnectEvent> future =  client.connect(connection);
			
			RapidantClientConnectEvent event = future.await();

			if(event.isSuccessful()){
				isConnection = true;
				logger.info("User ID: [" + event.getId() + "], User Type: [" + event.getUser().getType() + "]");
			}
			return isConnection;
		}else{
			return isConnection;
		}
	}

	@Override
	public boolean disconnection(RapidantClient client) {
		// TODO Auto-generated method stub
		
		boolean res = client.disconnect().await().isSuccessful();
		
		logger.warn("Result of the repetitive disconnection operation: [" + res + "]");
		
		if(res){
			isConnection = false;
		}
		return isConnection;
	}
	
	@Override
	public List<TreeFileModel> getRemoteDirList(RapidantClient client, String path) {
		// TODO Auto-generated method stub

		remoteFileListCommand = new RapidantClientRemoteFileListCommand();
		remoteFileListCommand.setDirectory(path);

		RapidantClientFileListEvent event = client.execute(remoteFileListCommand).await();

		if (event.isSuccessful()) {

			List<TreeFileModel> list = new ArrayList<TreeFileModel>();

			for (RapidantFile rf : event.getFiles()) {
				if (rf.isDirectory() && !rf.getName().startsWith(".")) {
					
					TreeFileModel fm = new TreeFileModel();
					fm.setName(rf.getName());
					fm.setPath(rf.getPath());
					fm.setSize(rf.getSize());
					fm.setModified(rf.getModified());
					fm.setDiretory(rf.isDirectory());

					list.add(fm);
				}
			}

			return list;
		}
		
		return null;
	}

	@Override
	public List<TableFileModel> getRemoteFileList(RapidantClient client, String path) {
		// TODO Auto-generated method stub
		
		remoteFileListCommand = new RapidantClientRemoteFileListCommand();
		remoteFileListCommand.setDirectory(path);
		
		RapidantClientFileListEvent event = client.execute(remoteFileListCommand).await();
		
		if(event.isSuccessful()){
			
			List<TableFileModel> list = new ArrayList<TableFileModel>();
			
			for(RapidantFile rf : event.getFiles()){
				TableFileModel fm = new TableFileModel();
				fm.setName(rf.getName());
				fm.setPath(rf.getPath());
				fm.setSize(rf.getSize());
				fm.setModified(rf.getModified());
				fm.setDiretory(rf.isDirectory());
				
				list.add(fm);
			}
			
			return list;
		}
		
		return null;
	}

	@Override
	public RapidantTransferFuture dataTransferSend(RapidantClient client, String path, String[] name, int fairness) {
		// TODO Auto-generated method stub

		logger.info("User's current path: [" + path + "], Transfer data list: [" + String.join(",", name) + "]");
		 
		RapidantClientSendCommand sendCommand = new RapidantClientSendCommand();
		
		if(fairness == Constants.FAIRNESS_DEFAULT){
			sendCommand.setFairness(RapidantFairness.DEFAULT);
		}else if(fairness == Constants.FAIRNESS_WEIGHTED){
			sendCommand.setMaxSpeed(1000f);
		}else{
			sendCommand.setMaxSpeed(fairness);
			sendCommand.setFairness(RapidantFairness.valueOf(RapidantFairnessType.FIXED, fairness));
		}
		
		sendCommand.setSourceDirectory(path);
		sendCommand.setSourceFiles(Arrays.asList(name));
		sendCommand.setTargetDirectory(RemotePresenter.path);
		sendCommand.setTargetFiles(Arrays.asList(name));
		sendCommand.setOverwrite(false);
		sendCommand.setGuarantee(RapidantGuaranteeType.MD5_CHECK);
		
		RapidantTransferFuture future = client.transfer(sendCommand);

		return future;
	}
	
	@Override
	public RapidantTransferFuture dataTransferReceive(RapidantClient client, String path, String[] name, int fairness) {
		// TODO Auto-generated method stub
		
		logger.info("Cloud remote access path: [" + path + "], Transfer data list: [" + String.join(",", name) + "]");
		 
		RapidantClientReceiveCommand receiveCommand = new RapidantClientReceiveCommand();
		
		if(fairness == Constants.FAIRNESS_DEFAULT){
			receiveCommand.setFairness(RapidantFairness.DEFAULT);
		}else if(fairness == Constants.FAIRNESS_WEIGHTED){
			receiveCommand.setMaxSpeed(1000f);
		}else{
			receiveCommand.setMaxSpeed(fairness);
			receiveCommand.setFairness(RapidantFairness.valueOf(RapidantFairnessType.FIXED, fairness));
		}
		
		receiveCommand.setSourceDirectory(path);
		receiveCommand.setSourceFiles(Arrays.asList(name));
		receiveCommand.setTargetDirectory(LocalPresenter.path);
		receiveCommand.setTargetFiles(Arrays.asList(name));
		receiveCommand.setOverwrite(false);
		receiveCommand.setGuarantee(RapidantGuaranteeType.MD5_CHECK);

		RapidantTransferFuture future = client.transfer(receiveCommand);

		return future;
	}

	@Override
	public boolean removes(RapidantClient client, HandlerManager eventBus, List<String> path) {
		// TODO Auto-generated method stub
		
		remoteFileDeleteCommand = new RapidantClientRemoteFileDeleteCommand();
		
		boolean result = false;
		
		remoteFileDeleteCommand.setFiles(path);
		RapidantClientFileDeleteEvent event = client.execute(remoteFileDeleteCommand).await();
		
		result = event.isSuccessful();
		
		if(!result){
			return false;
		}
				
		return result;
	}

	@Override
	public boolean existDir(RapidantClient client, String parentPath, String name) {
		// TODO Auto-generated method stub
		
		RapidantClientRemoteFileListCommand command = new RapidantClientRemoteFileListCommand();
		command.setDirectory(parentPath);
		
		RapidantClientFileListEvent event = client.execute(command).await();
		
		List<String> dn = null;
		
		if(event.isSuccessful()){
			
			dn = new ArrayList<String>();
			
			for(RapidantFile rf : event.getFiles()){
				if(rf.isDirectory()){
					dn.add(rf.getName());
				}
			}
		}
		
		return dn.contains(name);
	}

	@Override
	public boolean makeDir(RapidantClient client, String path) {
		// TODO Auto-generated method stub
		
		RapidantClientRemoteFileMkdirCommand command = new RapidantClientRemoteFileMkdirCommand();
		command.setDirectories(Arrays.asList(path));

		RapidantClientFileMkdirEvent event = client.execute(command).await();
		
		return event.isSuccessful();
	}
	
	

	@Override
	public boolean rename(RapidantClient client, HandlerManager eventBus, Map<String, String> path) {
		// TODO Auto-generated method stub
		
		RapidantClientRemoteFileRenameCommand command = new RapidantClientRemoteFileRenameCommand();
		command.setFiles(path);
		RapidantFuture<RapidantClientFileRenameEvent> future = client.execute(command);
		
		future.setHandler(new RapidantFutureHandler<RapidantClientFileRenameEvent>() {		
			@Override
			public void handle(RapidantClientFileRenameEvent result) {
				// TODO Auto-generated method stub
				if(result.isSuccessful()) {
					logger.info("file move result: [" + result.isSuccessful() + "]");
					isMove = result.isSuccessful();
				}
			}
		});
		
		return isMove;
	}
}