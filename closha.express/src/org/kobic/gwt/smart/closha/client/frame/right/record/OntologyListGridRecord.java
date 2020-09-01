package org.kobic.gwt.smart.closha.client.frame.right.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OntologyListGridRecord extends ListGridRecord implements IsSerializable {
	
	public OntologyListGridRecord(){
		
	}
	
	public OntologyListGridRecord(String id, String name, String type) {
		setId(id);
		setName(name);
		setType(type);
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("id");
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		setAttribute("id", id);  
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("name");
	}
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		setAttribute("name", name);  
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("type");
	}
	
	public void setType(String type) {
		// TODO Auto-generated method stub
		setAttribute("type", type);  
	}
}
