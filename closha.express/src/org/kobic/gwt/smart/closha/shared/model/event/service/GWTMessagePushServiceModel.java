package org.kobic.gwt.smart.closha.shared.model.event.service;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GWTMessagePushServiceModel implements IsSerializable{

	private String projectName;
	private String userID;
	private String instanceID;
	private String pipelineID;
	private String ownerEmail;
	private String message;
	private String workDir;
	
	private List<XmlModuleModel> xmlModulesModelList;
	
	public List<XmlModuleModel> getXmlModulesModelList() {
		return xmlModulesModelList;
	}
	public void setXmlModulesModelList(List<XmlModuleModel> xmlModulesModelList) {
		this.xmlModulesModelList = xmlModulesModelList;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWorkDir() {
		return workDir;
	}
	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}
}
