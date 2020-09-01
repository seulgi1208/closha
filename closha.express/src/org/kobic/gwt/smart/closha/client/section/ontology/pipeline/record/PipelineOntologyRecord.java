package org.kobic.gwt.smart.closha.client.section.ontology.pipeline.record;

import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.tree.TreeNode;

public class PipelineOntologyRecord extends TreeNode implements IsSerializable {
	
	public PipelineOntologyRecord(){
	}
	
	public PipelineOntologyRecord(String pipelineID, String registerID, String pipelineName, String pipelineDesc, String pipelineAuthor, 
			String registerDate, String updateDate, String pipelineXML, String linkedKey, String ontologyID, String version, String icon){
		setPipelineID(pipelineID);
		setRegisterID(registerID);
		setPipelineName(pipelineName);
		setPipelineDesc(pipelineDesc);
		setPipelineAuthor(pipelineAuthor);
		setRegisterDate(registerDate);
		setUpdateDate(updateDate);
		setPipelineXML(pipelineXML);
		setLinkedKey(linkedKey);
		setOntologyID(ontologyID);
		setVersion(version);
		setAttribute("icon", icon);
		
		if(ontologyID.equals(Constants.ROOT_ID)){
			setAttribute("pipelineViewName", pipelineName);
		}else{
			//setAttribute("pipelineViewName", pipelineName + ".v." + version + " (author : " + pipelineAuthor + ")");
			setAttribute("pipelineViewName", pipelineName);
		}		
	}

	public String getPipelineID() {
		return getAttributeAsString("pipelineID");
	}
	public void setPipelineID(String pipelineID) {
		setAttribute("pipelineID", pipelineID);  
	}
	public String getRegisterID() {
		return getAttributeAsString("registerID");
	}
	public void setRegisterID(String registerID) {
		setAttribute("registerID", registerID);  
	}
	public String getPipelineName() {
		return getAttributeAsString("pipelineName");
	}
	public void setPipelineName(String pipelineName) {
		setAttribute("pipelineName", pipelineName);  
	}
	public String getPipelineDesc() {
		return getAttributeAsString("pipelineDesc");
	}
	public void setPipelineDesc(String pipelineDesc) {
		setAttribute("pipelineDesc", pipelineDesc);  
	}
	public String getPipelineAuthor() {
		return getAttributeAsString("pipelineAuthor");
	}
	public void setPipelineAuthor(String pipelineAuthor) {
		setAttribute("pipelineAuthor", pipelineAuthor);  
	}
	public String getRegisterDate() {
		return getAttributeAsString("registerDate");
	}
	public void setRegisterDate(String registerDate) {
		setAttribute("registerDate", registerDate);  
	}
	public String getUpdateDate() {
		return getAttributeAsString("updateDate");
	}
	public void setUpdateDate(String updateDate) {
		setAttribute("updateDate", updateDate);  
	}
	public String getPipelineXML() {
		return getAttributeAsString("pipelineXML");
	}
	public void setPipelineXML(String pipelineXML) {
		setAttribute("pipelineXML", pipelineXML);  
	}
	public String getLinkedKey() {
		return getAttributeAsString("linkedKey");
	}
	public void setLinkedKey(String linkedKey) {
		setAttribute("linkedKey", linkedKey);  
	}
	public String getOntologyID() {
		return getAttributeAsString("ontologyID");
	}
	public void setOntologyID(String ontologyID) {
		setAttribute("ontologyID", ontologyID);  
	}
	public String getVersion() {
		return getAttributeAsString("version");
	}
	public void setVersion(String version) {
		setAttribute("version", version);  
	}
}