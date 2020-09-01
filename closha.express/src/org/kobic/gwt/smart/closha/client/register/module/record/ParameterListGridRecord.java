package org.kobic.gwt.smart.closha.client.register.module.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ParameterListGridRecord extends ListGridRecord implements IsSerializable {
	
	public ParameterListGridRecord(){
		
	}
	
	public ParameterListGridRecord(String datatype, String parameterName,
			String defaultValue, String description) {
		setDataType(datatype);
		setParameterName(parameterName);
		setDefaultValue(defaultValue);
		setDescription(description);
	}
	
	public String getDataType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("datatype");
	}
	
	public void setDataType(String datatype) {
		// TODO Auto-generated method stub
		setAttribute("datatype", datatype);  
	}
	
	public String getParameterName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parameterName");
	}
	
	public void setParameterName(String parameterName) {
		// TODO Auto-generated method stub
		setAttribute("parameterName", parameterName);  
	}
	
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return getAttributeAsString("defaultValue");
	}
	
	public void setDefaultValue(String defaultValue) {
		// TODO Auto-generated method stub
		setAttribute("defaultValue", defaultValue);  
	}
	
	public String getDescription() {
		// TODO Auto-generated method stub
		return getAttributeAsString("description");
	}
	
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		setAttribute("description", description);  
	}
}
