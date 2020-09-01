package org.kobic.gwt.smart.closha.client.pipeline.design.record;

import java.io.Serializable;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InnerProjectParameterListGridRecord extends ListGridRecord
		implements Serializable {

	private static final long serialVersionUID = 1L;

	public InnerProjectParameterListGridRecord() {
	}

	public InnerProjectParameterListGridRecord(String format, String moduleName,
			String parameterName, String type, String value, String desc) {
		setFormat(format);
		setModuleName(moduleName);
		setParameterName(parameterName);
		setType(type);
		setValue(value);
		setDesc(desc);
	}

	public String getFormat() {
		return getAttributeAsString("format");
	}

	public void setFormat(String format) {
		setAttribute("format", format);
	}

	public String getModuleName() {
		return getAttributeAsString("moduleName");
	}

	public void setModuleName(String moduleName) {
		setAttribute("moduleName", moduleName);
	}

	public String getParameterName() {
		return getAttributeAsString("parameterName");
	}

	public void setParameterName(String parameterName) {
		setAttribute("parameterName", parameterName);
	}

	public String getType() {
		return getAttributeAsString("type");
	}

	public void setType(String type) {
		setAttribute("type", type);
	}

	public String getValue() {
		return getAttributeAsString("value");
	}

	public void setValue(String value) {
		setAttribute("value", value);
	}
	
	public String getDesc() {
		return getAttributeAsString("desc");
	}

	public void setDesc(String desc) {
		setAttribute("desc", desc);
	}
}
