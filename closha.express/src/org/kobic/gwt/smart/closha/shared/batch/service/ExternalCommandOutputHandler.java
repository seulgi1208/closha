package org.kobic.gwt.smart.closha.shared.batch.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

public class ExternalCommandOutputHandler extends Thread {

	private InputStream inpStr;
	private String path;

	public ExternalCommandOutputHandler(InputStream inpStr, String path) {
		this.inpStr = inpStr;
		this.path = path;
	}

	public void run() {
		try {
			FileWriter fileWriter = new FileWriter(path, true);
			BufferedWriter file = new BufferedWriter(fileWriter);
			
			InputStreamReader inpStrd = new InputStreamReader(inpStr);
			BufferedReader buffRd = new BufferedReader(inpStrd);

			String line = null;

			while ((line = buffRd.readLine()) != null) {

				try {
					file.write("[" + CommonUtilsGwt.getDate() + "]: " + line);
			        file.newLine();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			buffRd.close();
			file.close();
			fileWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
