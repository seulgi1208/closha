package org.kobic.gwt.smart.closha.shared.batch.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BatchProcessService {

	public static String executor(String expCMD) {
		
		System.out.println("system command: " + expCMD);

		Runtime runtime = Runtime.getRuntime();
		StringBuffer outLines = new StringBuffer();
		String outLine = null;

		try {
			Process process = runtime.exec(expCMD);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			while ((outLine = br.readLine()) != null) {
				
				System.out.println(outLine);
				
				outLines.append(outLine + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outLines.toString();
	}
	
	public static String executor(String expCMD[]) {

		StringBuffer outLines = new StringBuffer();

		ProcessBuilder builder = new ProcessBuilder(expCMD);

		Process process;
		
		try {
			process = builder.start();
			
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null) {
				
				System.out.println(line);
				
				outLines.append(line + "\n");
			}
			br.close();
			isr.close();
			is.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outLines.toString();
	}
}