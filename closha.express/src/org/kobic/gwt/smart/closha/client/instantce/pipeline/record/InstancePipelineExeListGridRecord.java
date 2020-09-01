package org.kobic.gwt.smart.closha.client.instantce.pipeline.record;

import java.io.Serializable;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InstancePipelineExeListGridRecord extends ListGridRecord implements
		Serializable {

	private static final long serialVersionUID = 1L;

	public InstancePipelineExeListGridRecord() {
	}

	public InstancePipelineExeListGridRecord(String instanceID, String pipelineID,
			String exeID, String startDate, String endDate) {

		setInstanceID(instanceID);
		setPipelineID(pipelineID);
		setExeID(exeID);
		setStartDate(startDate);
		setEndDate(endDate);
		
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

	public String getStartDate() {
		return getAttributeAsString("startDate");
	}

	public void setStartDate(String startDate) {
		setAttribute("startDate", startDate);
	}
	
	public String getEndDate() {
		return getAttributeAsString("endDate");
	}

	public void setEndDate(String endDate) {
		setAttribute("endDate", endDate);
	}
}
