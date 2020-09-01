package org.kobic.gwt.smart.closha.client.pipeline.design.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModuleLinkedListGridRecord extends ListGridRecord implements IsSerializable {
	
	public ModuleLinkedListGridRecord(){
		
	}
	
	public ModuleLinkedListGridRecord(String left, String top, String id, String identifier, String name, String type){
		setLeft(left);
		setTop(top);
		setId(id);
		setIdentifier(identifier);
		setName(name);
		setType(type);
		
		if(name.trim().toLowerCase().equals("start")){
			setAttribute("module", "closha/icon/start_module.png");
		}else if(name.trim().toLowerCase().equals("end")){
			setAttribute("module", "closha/icon/end_module.png");
		}else{
			setAttribute("module", "closha/icon/modules_2.png");
		}
	}
	
	public String getLeft() {
		// TODO Auto-generated method stub
		return getAttributeAsString("left");
	}
	
	public void setLeft(String left) {
		// TODO Auto-generated method stub
		setAttribute("left", left);  
	}
	
	public String getTop() {
		// TODO Auto-generated method stub
		return getAttributeAsString("top");
	}
	
	public void setTop(String top) {
		// TODO Auto-generated method stub
		setAttribute("top", top);  
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("id");
	}
	
	public void setId(String id) {
		// TODO Auto-generated method stub
		setAttribute("id", id);  
	}
	
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return getAttributeAsString("identifier");
	}
	
	public void setIdentifier(String identifier) {
		// TODO Auto-generated method stub
		setAttribute("identifier", identifier);  
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
