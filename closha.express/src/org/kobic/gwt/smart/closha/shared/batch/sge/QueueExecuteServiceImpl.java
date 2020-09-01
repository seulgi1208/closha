package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QueueExecuteServiceImpl implements QueueExecuteService {

	private String[] script;
	
	@Override
	public Process runScript() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ProcessBuilder builder = new ProcessBuilder(script);
		return builder.start();
	}

	@Override
	public List<Integer> extractJobID(Process process) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		StringBuffer out_sb = new StringBuffer();

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while ((line = br.readLine()) != null) {
			out_sb.append(line + "\n");
		}
		br.close();
		isr.close();
		is.close();
		
		int job_id = -1;
		List<Integer> job_ids = new ArrayList<Integer>();
		String[] lines = out_sb.toString().split("\n");
		
		for(String out : lines){
			String[] words = out.split("[ ]");
			if (words.length > 3) {
				if (words[2].matches("\\d+")) {
					job_id = Integer.valueOf(words[2]).intValue();
					job_ids.add(job_id);
				}
			}
		}
		return job_ids;
	}

	@Override
	public List<String> execute(String[] script) throws IOException, InterruptedException{
		// TODO Auto-generated method stub
		this.script = script;
		
		Process proc = this.runScript();	
		List<Integer> ids = this.extractJobID(proc);

		List<String> exeIDs = new ArrayList<String>();
		for(int id : ids){
			exeIDs.add(String.valueOf(id));
		}
		return exeIDs;
	}
}
