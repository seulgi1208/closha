package org.kobic.gwt.smart.closha.shared.batch.service;

public class LocalBatchProcessService {

	public static void execute(String cmd, String logPath){
		
		try {
			Process process = Runtime.getRuntime().exec(cmd);

			ExternalCommandOutputHandler inputStream = new ExternalCommandOutputHandler(
					process.getInputStream(), logPath);
			ExternalCommandOutputHandler errorStream = new ExternalCommandOutputHandler(
					process.getErrorStream(), logPath);

			inputStream.start();
			errorStream.start();

			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
