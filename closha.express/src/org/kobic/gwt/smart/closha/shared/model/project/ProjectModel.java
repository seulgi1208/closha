package org.kobic.gwt.smart.closha.shared.model.project;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProjectModel implements IsSerializable{
	
	private String projectID;
	private String pipelineID;
	private String projectName;
	private String projectDesc;
	private String projectOwner;
	private int assign;
	private boolean eCheck;
	private String createDate;
	private String status;
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getPipelineID() {
		return pipelineID;
	}
	public void setPipelineID(String pipelineID) {
		this.pipelineID = pipelineID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public int getAssign() {
		return assign;
	}
	public void setAssign(int assign) {
		this.assign = assign;
	}
	public boolean geteCheck() {
		return eCheck;
	}
	public void seteCheck(boolean eCheck) {
		this.eCheck = eCheck;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}
