package org.kobic.gwt.smart.closha.client.section.parameter.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ParameterInfoRecord  extends ListGridRecord implements IsSerializable {
	
	public ParameterInfoRecord(){
		
	}
	
	public ParameterInfoRecord(String parameter, String value, String desc, String name, String type, String parameterType, String cache, String key){
		setParameter(parameter);
		setValue(value);
		setDesc(desc);
		setName(name);
		setType(type);
		setParameterType(parameterType);
		setCache(cache);
		setKey(key);
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("name");
	}
	
	public void setName(String name) {
		// TODO Auto-generated method stub
		setAttribute("name", name);  
	}
	
	public String getParameter() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parameter");
	}
	
	public void setParameter(String parameter) {
		// TODO Auto-generated method stub
		setAttribute("parameter", parameter);  
	}
	
	public String getValue() {
		// TODO Auto-generated method stub
		return getAttributeAsString("value");
	}
	
	public void setValue(String value) {
		// TODO Auto-generated method stub
		setAttribute("value", value);  
	}

	public String getDesc() {
		// TODO Auto-generated method stub
		return getAttributeAsString("desc");
	}
	
	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		setAttribute("desc", desc);  
	}
	
	public String getType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("type");
	}
	
	public void setType(String type) {
		// TODO Auto-generated method stub
		setAttribute("type", type);  
	}
	
	public String getParameterType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parameter_type");
	}
	
	public void setParameterType(String parameterType) {
		// TODO Auto-generated method stub
		setAttribute("parameter_type", parameterType);  
	}
	
	public String getCache() {
		// TODO Auto-generated method stub
		return getAttributeAsString("cache");
	}
	
	public void setCache(String cache) {
		// TODO Auto-generated method stub
		setAttribute("cache", cache);  
	}
	
	public String getKey() {
		// TODO Auto-generated method stub
		return getAttributeAsString("key");
	}
	
	public void setKey(String key) {
		// TODO Auto-generated method stub
		setAttribute("key", key);  
	}
}
