package org.kobic.gwt.smart.closha.client.register.module.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModuleParameterListGridRecord extends ListGridRecord implements
		IsSerializable {

	public ModuleParameterListGridRecord() {

	}

	public ModuleParameterListGridRecord(String parameterType, String dataType,
			String parameterName, String defaultValue) {
		
		setParameterType(parameterType);
		setDataType(dataType);
		setParameterName(parameterName);
		setDefaultValue(defaultValue);
	}

	public String getParameterType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parameterType");
	}

	public void setParameterType(String parameterType) {
		// TODO Auto-generated method stub
		setAttribute("parameterType", parameterType);
	}
	
	public String getDataType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("dataType");
	}

	public void setDataType(String dataType) {
		// TODO Auto-generated method stub
		setAttribute("dataType", dataType);
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
}
