package org.kobic.gwt.smart.closha.shared.model.pipeline.instance;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InstancePipelineModel implements IsSerializable{

	private String instanceID;
	private String pipelineID;
	private String exeID;
	private String pipelineName;
	private String instanceOwner;
	private String ownerEmail;
	private String instanceName;
	private String instanceDesc;
	private String instanceXML;
	private int status;
	private int exeCount;
	private String createDate;
	private String projectPath;
	private boolean register;
	
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
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
	public String getPipelineName() {
		return pipelineName;
	}
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	public String getInstanceOwner() {
		return instanceOwner;
	}
	public void setInstanceOwner(String instanceOwner) {
		this.instanceOwner = instanceOwner;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getInstanceDesc() {
		return instanceDesc;
	}
	public void setInstanceDesc(String instanceDesc) {
		this.instanceDesc = instanceDesc;
	}
	public String getInstanceXML() {
		return instanceXML;
	}
	public void setInstanceXML(String instanceXML) {
		this.instanceXML = instanceXML;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getExeCount() {
		return exeCount;
	}
	public void setExeCount(int exeCount) {
		this.exeCount = exeCount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public boolean isRegister() {
		return register;
	}
	public void setRegister(boolean register) {
		this.register = register;
	}
}
