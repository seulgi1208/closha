package org.kobic.gwt.smart.closha.shared.model.pipeline;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PipelineModel implements IsSerializable{
	
	private String pipelineID;
	private String registerID;
	private String pipelineName;
	private String pipelineDesc;
	private String pipelineAuthor;
	private String registerDate;
	private String updateDate;
	private String pipelineXML;
	private String linkedKey;
	private String ontologyID;
	private String version;
	private int pipelineType;
	
	public PipelineModel(){
		
	}
	
	public PipelineModel(String pipelineID, String registerID, String pipelineName, String pipelineDesc, String pipelineAuthor,
			String registerDate, String updateDate, String pipelineXML, String linkedKey, String ontologyID, String version, int PipelineType){
		this.pipelineID = pipelineID;
		this.registerID = registerID;
		this.pipelineName = pipelineName;
		this.pipelineDesc = pipelineDesc;
		this.pipelineAuthor = pipelineAuthor;
		this.registerDate = registerDate;
		this.updateDate = updateDate;
		this.pipelineXML = pipelineXML;
		this.linkedKey = linkedKey;
		this.ontologyID = ontologyID;
		this.version = version;
		this.pipelineType = PipelineType;
	}

	public String getPipelineID() {
		return pipelineID;
	}

	public void setPipelineID(String pipelineID) {
		this.pipelineID = pipelineID;
	}

	public String getRegisterID() {
		return registerID;
	}

	public void setRegisterID(String registerID) {
		this.registerID = registerID;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getPipelineDesc() {
		return pipelineDesc;
	}

	public void setPipelineDesc(String pipelineDesc) {
		this.pipelineDesc = pipelineDesc;
	}

	public String getPipelineAuthor() {
		return pipelineAuthor;
	}

	public void setPipelineAuthor(String pipelineAuthor) {
		this.pipelineAuthor = pipelineAuthor;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getPipelineXML() {
		return pipelineXML;
	}

	public void setPipelineXML(String pipelineXML) {
		this.pipelineXML = pipelineXML;
	}

	public String getLinkedKey() {
		return linkedKey;
	}

	public void setLinkedKey(String linkedKey) {
		this.linkedKey = linkedKey;
	}

	public String getOntologyID() {
		return ontologyID;
	}

	public void setOntologyID(String ontologyID) {
		this.ontologyID = ontologyID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getPipelineType() {
		return pipelineType;
	}

	public void setPipelineType(int pipelineType) {
		this.pipelineType = pipelineType;
	}

		
}
