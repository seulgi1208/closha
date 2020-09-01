package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.kobic.gwt.smart.closha.shared.batch.service.BatchProcessService;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.HostsModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobStatusModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobInformationModel;

public class QueueStateServiceImpl implements QueueStateService{
	
	private StringTokenizer token;
	private List<String> tokenList;
	
	private List<String> getStringTokens(String outLine){
		
		token = new StringTokenizer(outLine);
		tokenList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			tokenList.add(token.nextToken());
		}		
		return tokenList;
	}

	@Override
	public List<String> getJobIDs() {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[]{"qstat"};
		
		String[] outLines = BatchProcessService.executor(cmd).split("\n");
		List<String> jobIDs = new ArrayList<String>();

		if (outLines.length > 2) {
			for (int i = 2; i < outLines.length; i++) {
				jobIDs.add(getStringTokens(outLines[i]).get(0));
			}
		}
		return jobIDs;
	}

	@Override
	public List<JobInformationModel> getQueueState() {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[]{"qstat"};
		
		String[] outLines = BatchProcessService.executor(cmd).split("\n");
		
		List<JobInformationModel> list = new ArrayList<JobInformationModel>();
		
		if (outLines.length > 2) {
			for (int i = 2; i < outLines.length; i++) {
				
				JobInformationModel sm = new JobInformationModel();

				sm.setJobID(getStringTokens(outLines[i]).get(0));
				sm.setPriority(getStringTokens(outLines[i]).get(1));
				sm.setName(getStringTokens(outLines[i]).get(2));
				sm.setState(getStringTokens(outLines[i]).get(4));
				sm.setSubmissionDate(getStringTokens(outLines[i]).get(5)  
						+ " (" + getStringTokens(outLines[i]).get(6) + ")");
				
				if(tokenList.size() == 9){
					sm.setQueue(getStringTokens(outLines[i]).get(7));
					sm.setSlots(getStringTokens(outLines[i]).get(8));
				}else{
					sm.setQueue("");
					sm.setSlots(getStringTokens(outLines[i]).get(7));
				}

				list.add(sm);
			}
		}
		
		return list;
	}

	@Override
	public List<JobStatusModel> getJobDetailInfo(String jobID) {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[]{"qstat", "-j", jobID};
		
		String[] outLines = BatchProcessService.executor(cmd).split("\n");
		List<JobStatusModel> list = new ArrayList<JobStatusModel>();

		if (outLines.length > 1) {
			for (int i = 1; i < outLines.length; i++) {
				JobStatusModel sm = new JobStatusModel();
				sm.setKey(getStringTokens(outLines[i]).get(0));
				
				if(getStringTokens(outLines[i]).size() == 2){
					sm.setValue(getStringTokens(outLines[i]).get(1));
				}else{
					sm.setValue("");
				}
				list.add(sm);
			}
		}
		return list;
	}


	@Override
	public List<HostsModel> getClusterHostInfo() {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[]{"qhost"};
		
		String[] outLines = BatchProcessService.executor(cmd).split("\n");
		List<HostsModel> list = new ArrayList<HostsModel>();
		
		if(outLines.length > 2){
			for(int i = 2; i < outLines.length; i++){
				HostsModel sh = new HostsModel();
				
				sh.setHost(getStringTokens(outLines[i]).get(0));
				sh.setCpu(getStringTokens(outLines[i]).get(2));
				sh.setLoad(getStringTokens(outLines[i]).get(3));
				sh.setMemTo(getStringTokens(outLines[i]).get(4));
				sh.setMemUse(getStringTokens(outLines[i]).get(5));
				sh.setSwapTo(getStringTokens(outLines[i]).get(6));
				sh.setSwapUse(getStringTokens(outLines[i]).get(7));
				
				list.add(sh);
			}
		}
		return list;
	}

	@Override
	public boolean qstateCheckUp(List<String> jobIDs) {
		// TODO Auto-generated method stub
		List<String> qIDs = new ArrayList<String>();
		String jobID = "";
		String id = "";
		
		qIDs = getJobIDs();
		
		for(int i = 0; i < jobIDs.size(); i++){
			if (!qIDs.contains(jobIDs.get(i))) {
				jobID = jobIDs.get(i);
				for (Iterator<String> it = jobIDs.iterator(); it.hasNext();) {
					id = it.next();
					if (id.equals(jobID)) {
						it.remove();
					}
				}
			}
		}
		
		if(jobIDs.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
