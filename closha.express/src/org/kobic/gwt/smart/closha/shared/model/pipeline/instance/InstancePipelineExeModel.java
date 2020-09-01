package org.kobic.gwt.smart.closha.shared.model.pipeline.instance;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InstancePipelineExeModel implements IsSerializable{

	private String instanceID;
	private String pipelineID;
	private String exeID;
	private String startDate;
	private String endDate;
	
	public String getInstanceID() {
		return instanceID;
	}
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
	public String getPipelineID() {
		return pipelineID;
	}
	public void setPipelineID(String pipelineID) {
		this.pipelineID = pipelineID;
	}
	public String getExeID() {
		return exeID;
	}
	public void setExeID(String exeID) {
		this.exeID = exeID;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
