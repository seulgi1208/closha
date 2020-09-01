package org.kobic.gwt.smart.closha.client.monitoring.job.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class JobInformationListGridRecord extends ListGridRecord implements IsSerializable{

	public JobInformationListGridRecord(){}
	
	public String getStateImg(String state){
		
		String imgSrc = "";
		
		if(state.equals("r")){
			imgSrc = "closha/icon/history_running.png";
			return imgSrc;
		}else if(state.equals("qw")){
			imgSrc = "closha/icon/history_wait.png";
			return imgSrc;
		}else if(state.equals("Eqw")){
			imgSrc = "closha/icon/history_error.png";
			return imgSrc;
		}else{
			imgSrc = "closha/icon/history_stanby.png";
			return imgSrc;
		}
	}
	
	public String getJobState(String state){
		
		String status = "";
		
		if(state.equals("r")){
			status = "running";
			return status;
		}else if(state.equals("qw")){
			status = "waiting";
			return status;
		}else if(state.equals("Eqw")){
			status = "error";
			return status;
		}else{
			status = "stand by";
			return status;
		}
	}
	
	public JobInformationListGridRecord(String queue, String slot, String jobID, String name,
			String priority, String state, String submissionTime){
		setAttribute("stateImg", (Object)getStateImg(state));
		setQueue(queue);
		setSlots(slot);
		setJobID(jobID);
		setName(name);
		setPriority(priority);
		setState(getJobState(state));
		setSubmissionTime(submissionTime);
	}
	
	public String getQueue() {
		return getAttributeAsString("queue");
	}
	public void setQueue(String queue) {
		setAttribute("queue", queue);  
	}
	public String getSlots() {
		return getAttributeAsString("slot");
	}
	public void setSlots(String slot) {
		setAttribute("slot", slot);  
	}
	public String getJobID() {
		return getAttributeAsString("jobID");
	}
	public void setJobID(String jobID) {
		setAttribute("jobID", jobID);  
	}
	public String getName() {
		return getAttributeAsString("name");
	}
	public void setName(String name) {
		setAttribute("name", name);  
	}
	public String getPriority() {
		return getAttributeAsString("priority");
	}
	public void setPriority(String priority) {
		setAttribute("priority", priority);  
	}
	public String getState() {
		return getAttributeAsString("state");
	}
	public void setState(String state) {
		setAttribute("state", state);  
	}
	public String getSubmissionTime() {
		return getAttributeAsString("submissionTime");
	}
	public void setSubmissionTime(String submissionTime) {
		setAttribute("submissionTime", submissionTime);  
	}

}
