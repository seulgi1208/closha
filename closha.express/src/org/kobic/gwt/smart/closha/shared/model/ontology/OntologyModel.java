package org.kobic.gwt.smart.closha.shared.model.ontology;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OntologyModel implements IsSerializable{

	private String ontologyID;
	private String ontologyName;
	private String count;
	private String type;
	
	public String getOntologyID() {
		return ontologyID;
	}
	public void setOntologyID(String ontologyID) {
		this.ontologyID = ontologyID;
	}
	public String getOntologyName() {
		return ontologyName;
	}
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
