package org.kobic.gwt.smart.closha.client.monitoring.job.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class JobStatusListGridRecord extends ListGridRecord implements IsSerializable{
	
	public JobStatusListGridRecord(){}
	
	public JobStatusListGridRecord(String key, String value){
		setKey(key);
		setValue(value);
	}
	
	public String getKey() {
		return getAttributeAsString("key");
	}
	public void setKey(String key) {
		setAttribute("key", key);  
	}
	public String getValue() {
		return getAttributeAsString("value");
	}
	public void setValue(String value) {
		setAttribute("value", value);  
	}

}
