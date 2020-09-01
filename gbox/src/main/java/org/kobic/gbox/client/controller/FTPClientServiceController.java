package org.kobic.gbox.client.controller;

import org.apache.commons.net.ftp.FTPClient;

public interface FTPClientServiceController {

	public static class Util {
		
		private static FTPClientServiceController instance;
		
		public static FTPClientServiceController getInstance(){
			if (instance == null) {
				instance = new FTPClientServiceControllerImpl();
			}
			return instance;
		}
	}
	
	public FTPClient connection();
	public FTPClient connection(FTPClient ftpClient);
	public boolean disconnection(FTPClient ftpClient);
	
}
