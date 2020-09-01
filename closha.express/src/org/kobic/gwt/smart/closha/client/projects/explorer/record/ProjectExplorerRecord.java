package org.kobic.gwt.smart.closha.client.projects.explorer.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProjectExplorerRecord extends TreeNode implements IsSerializable {

	public ProjectExplorerRecord() {

	}

	public ProjectExplorerRecord(String parentID, String projectID, String pipelineID, String projectName, 
			String projectDesc, String projectOwner, String createDate, int status, String icon) {
		setParentID(parentID);
		setProjectID(projectID);
		setPipelineID(pipelineID);
		setProjectName(projectName);
		setProjectDesc(projectDesc);
		setProjectOwner(projectOwner);
		setCreateDate(createDate);
		setStatus(status);
		setIcon(icon);
	}
	
	public String getParentID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parentID");
	}
	
	public void setParentID(String parentID) {
		// TODO Auto-generated method stub
		setAttribute("parentID", parentID);  
	}
	
	public String getProjectID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("projectID");
	}
	
	public void setProjectID(String projectID) {
		// TODO Auto-generated method stub
		setAttribute("projectID", projectID);  
	}
	
	public String getPipelineID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("pipelineID");
	}
	
	public void setPipelineID(String pipelineID) {
		// TODO Auto-generated method stub
		setAttribute("pipelineID", pipelineID);  
	}

	public String getProjectName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("projectName");
	}
	
	public void setProjectName(String projectName) {
		// TODO Auto-generated method stub
		setAttribute("projectName", projectName);  
	}
	
	public String getProjectDesc() {
		// TODO Auto-generated method stub
		return getAttributeAsString("projectDesc");
	}
	
	public void setProjectDesc(String projectDesc) {
		// TODO Auto-generated method stub
		setAttribute("projectDesc", projectDesc);  
	}
	
	public String getProjectOwner() {
		// TODO Auto-generated method stub
		return getAttributeAsString("projectOwner");
	}
	
	public void setProjectOwner(String projectOwner) {
		// TODO Auto-generated method stub
		setAttribute("projectOwner", projectOwner);  
	}
	
	public String getCreateDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("createDate");
	}
	
	public void setCreateDate(String createDate) {
		// TODO Auto-generated method stub
		setAttribute("createDate", createDate);  
	}
	
	public int getStatus() {
		// TODO Auto-generated method stub
		return getAttributeAsInt("status");
	}
	
	public void setStatus(int status) {
		// TODO Auto-generated method stub
		setAttribute("status", status);  
	}
	public String getIcon() {
		return getAttributeAsString("icon");
	}
	public void setIcon(String icon) {
		setAttribute("icon", icon);  
	}
}
