package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.batch.service.BatchProcessService;

public class QueueManagerServiceImpl implements QueueManagerService{

	private String[] deleteCMD(String jobID){
		return new String[]{"qdel", jobID};
	}
	
	@Override
	public String queueDelete(String jobID) {
		// TODO Auto-generated method stub
		return BatchProcessService.executor(deleteCMD(jobID));
	}

	@Override
	public String queueDeletes(List<String> jobIDs) {
		// TODO Auto-generated method stub
		StringBuffer outLines = new StringBuffer();

		for (String jobID : jobIDs) {
			outLines.append(queueDelete(jobID));
		}

		return outLines.toString();
	}

}
