package org.kobic.gwt.smart.closha.shared.batch.sge;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.job.scheduler.HostsModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobStatusModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobInformationModel;

public interface QueueStateService {

	public List<String> getJobIDs();

	public List<JobInformationModel> getQueueState();
	
	public List<JobStatusModel> getJobDetailInfo(String jobID);

	public List<HostsModel> getClusterHostInfo();
	
	public boolean qstateCheckUp(List<String> jobIDs);
}
