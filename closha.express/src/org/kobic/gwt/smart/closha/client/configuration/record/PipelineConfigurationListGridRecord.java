package org.kobic.gwt.smart.closha.client.configuration.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PipelineConfigurationListGridRecord extends ListGridRecord
		implements IsSerializable {

	public PipelineConfigurationListGridRecord() {

	}

	public PipelineConfigurationListGridRecord(String registerID,
			String pipelineName, String pipelineDesc, String registerDate,
			String updateDate, String version, String author, int pipelineType) {

		setRegisterID(registerID);
		setPipelineName(pipelineName);
		setPipelineDesc(pipelineDesc);
		setRegisterDate(registerDate);
		setUpdateDate(updateDate);
		setVersion(version);
		setAuthor(author);
		setPipelineType(pipelineType);
		
		if(pipelineType == 0){
			setAttribute("pipeline_icon", "closha/icon/chart_organisation.png");
		}else if(pipelineType == 1){
			setAttribute("pipeline_icon", "closha/icon/pipeline.png");
		}else if(pipelineType == 2){
			setAttribute("pipeline_icon", "closha/icon/hosting.png");
		}
	}

	public String getRegisterID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("registerID");
	}

	public void setRegisterID(String registerID) {
		// TODO Auto-generated method stub
		setAttribute("registerID", registerID);
	}

	public String getPipelineName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("pipelineName");
	}

	public void setPipelineName(String pipelineName) {
		// TODO Auto-generated method stub
		setAttribute("pipelineName", pipelineName);
	}

	public String getPipelineDesc() {
		// TODO Auto-generated method stub
		return getAttributeAsString("pipelineDesc");
	}

	public void setPipelineDesc(String pipelineDesc) {
		// TODO Auto-generated method stub
		setAttribute("pipelineDesc", pipelineDesc);
	}

	public String getRegisterDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("registerDate");
	}

	public void setRegisterDate(String registerDate) {
		// TODO Auto-generated method stub
		setAttribute("registerDate", registerDate);
	}

	public String getUpdateDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("updateDate");
	}

	public void setUpdateDate(String updateDate) {
		// TODO Auto-generated method stub
		setAttribute("updateDate", updateDate);
	}

	public String getVersion() {
		// TODO Auto-generated method stub
		return getAttributeAsString("version");
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
		setAttribute("version", version);
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return getAttributeAsString("author");
	}

	public void setAuthor(String author) {
		// TODO Auto-generated method stub
		setAttribute("author", author);
	}
	
	public int getPipelineType() {
		// TODO Auto-generated method stub
		return getAttributeAsInt("pipelineType");
	}

	public void setPipelineType(int pipelineType) {
		// TODO Auto-generated method stub
		setAttribute("pipelineType", pipelineType);
	}
}
