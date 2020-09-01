package org.kobic.gbox.client.controller;

import java.util.List;
import java.util.Map;

import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;

import com.pennychecker.eventbus.HandlerManager;

import rapidant.model.client.RapidantClient;
import rapidant.model.client.future.RapidantTransferFuture;

public interface RapidantServiceController {
	
	public static class Util {
		
		private static RapidantServiceController instance;
		
		public static RapidantServiceController getInstance(){
			if (instance == null) {
				instance = new RapidantServiceControllerImpl();
			}
			return instance;
		}
	}
	
	public boolean connection(RapidantClient client);
	public boolean connection(RapidantClient client, String method);
	public boolean disconnection(RapidantClient client);
	public List<TableFileModel> getRemoteFileList(RapidantClient client, String path);
	public List<TreeFileModel> getRemoteDirList(RapidantClient client, String path);
	public RapidantTransferFuture dataTransferSend(RapidantClient client,  String path, String[] name, int fairness);
	public RapidantTransferFuture dataTransferReceive(RapidantClient client,  String path, String[] name, int fairness);
	public boolean removes(RapidantClient client, HandlerManager eventBus, List<String> path);
	public boolean rename(RapidantClient client, HandlerManager eventBus, Map<String, String> path);
	public boolean existDir(RapidantClient client, String parentPath, String name);
	public boolean makeDir(RapidantClient client, String path);
	
}
