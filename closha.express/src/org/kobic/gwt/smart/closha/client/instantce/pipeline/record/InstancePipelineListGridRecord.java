package org.kobic.gwt.smart.closha.client.instantce.pipeline.record;

import java.io.Serializable;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InstancePipelineListGridRecord extends ListGridRecord implements
		Serializable {

	private static final long serialVersionUID = 1L;

	public InstancePipelineListGridRecord() {
	}

	public InstancePipelineListGridRecord(String instanceID, String pipelineID,
			String exeID, String pipelineName, String instanceOwner,
			String ownerEmail, String instanceName, String instanceDesc,
			String instanceXML, int status, int exeCount, String createDate, String stateImg, String projectPath) {

		setInstanceID(instanceID);
		setPipelineID(pipelineID);
		setExeID(exeID);
		setPipelineName(pipelineName);
		setInstanceOwner(instanceOwner);
		setOwnerEmail(ownerEmail);
		setInstanceName(instanceName);
		setInstanceDesc(instanceDesc);
		setInstanceXML(instanceXML);
		setStatus(status);
		setExeCount(exeCount);
		setCreateDate(createDate);
		setAttribute("stateImg", stateImg);
		setProjectPath(projectPath);
	}

	public String getInstanceID() {
		return getAttributeAsString("instanceID");
	}

	public void setInstanceID(String instanceID) {
		setAttribute("instanceID", instanceID);
	}

	public String getPipelineID() {
		return getAttributeAsString("pipelineID");
	}

	public void setPipelineID(String pipelineID) {
		setAttribute("pipelineID", pipelineID);
	}

	public String getExeID() {
		return getAttributeAsString("exeID");
	}

	public void setExeID(String exeID) {
		setAttribute("exeID", exeID);
	}

	public String getPipelineName() {
		return getAttributeAsString("pipelineName");
	}

	public void setPipelineName(String pipelineName) {
		setAttribute("pipelineName", pipelineName);
	}

	public String getInstanceOwner() {
		return getAttributeAsString("instanceOwner");
	}

	public void setInstanceOwner(String instanceOwner) {
		setAttribute("instanceOwner", instanceOwner);
	}

	public String getOwnerEmail() {
		return getAttributeAsString("ownerEmail");
	}

	public void setOwnerEmail(String ownerEmail) {
		setAttribute("ownerEmail", ownerEmail);
	}

	public String getInstanceName() {
		return getAttributeAsString("instanceName");
	}

	public void setInstanceName(String instanceName) {
		setAttribute("instanceName", instanceName);
	}

	public String getInstanceDesc() {
		return getAttributeAsString("instanceDesc");
	}

	public void setInstanceDesc(String instanceDesc) {
		setAttribute("instanceDesc", instanceDesc);
	}

	public String getInstanceXML() {
		return getAttributeAsString("instanceXML");
	}

	public void setInstanceXML(String instanceXML) {
		setAttribute("instanceXML", instanceXML);
	}

	public int getStatus() {
		return getAttributeAsInt("status");
	}

	public void setStatus(int status) {
		setAttribute("status", status);
	}

	public int getExeCount() {
		return getAttributeAsInt("exeCount");
	}

	public void setExeCount(int exeCount) {
		setAttribute("exeCount", exeCount);
	}

	public String getCreateDate() {
		return getAttributeAsString("createDate");
	}

	public void setCreateDate(String createDate) {
		setAttribute("createDate", createDate);
	}
	
	public String getProjectPath() {
		return getAttributeAsString("projectPath");
	}

	public void setProjectPath(String projectPath) {
		setAttribute("projectPath", projectPath);
	}
}
