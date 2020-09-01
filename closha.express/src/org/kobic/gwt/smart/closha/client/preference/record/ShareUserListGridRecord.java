package org.kobic.gwt.smart.closha.client.preference.record;

import java.io.Serializable;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ShareUserListGridRecord  extends ListGridRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public ShareUserListGridRecord(){
		
	}
	
	public ShareUserListGridRecord(String instanceID, String instanceName){
		setInstanceID(instanceID);
		setInstanceName(instanceName);
	}
	
	public String getInstanceID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("instanceID");
	}
	
	public void setInstanceID(String instanceID) {
		// TODO Auto-generated method stub
		setAttribute("instanceID", instanceID);  
	}
	
	public String getInstanceName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("instanceName");
	}
	
	public void setInstanceName(String instanceName) {
		// TODO Auto-generated method stub
		setAttribute("instanceName", instanceName);  
	}

}
