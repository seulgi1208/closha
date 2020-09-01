package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class XmlParameterModel implements IsSerializable {

	private String id;
	private String key;
	private String name;
	private String value;
	private String setupValue;
	private String description;
	private String type;
	private String module;
	private String parameterType;
	private String extensions;

	public XmlParameterModel() {
	}

	public XmlParameterModel(String id, String key, String name, String value,
			String setupValue, String description, String module,
			String parameterType, String extensions) {

		this.id = id;
		this.key = key;
		this.name = name;
		this.value = value;
		this.setupValue = setupValue;
		this.description = description;
		this.module = module;
		this.parameterType = parameterType;
		this.extensions = extensions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSetupValue() {
		return setupValue;
	}

	public void setSetupValue(String setupValue) {
		this.setupValue = setupValue;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	@Override
	public String toString() {
		return "XmlParameterModel [id=" + id + ", key=" + key + ", name="
				+ name + ", value=" + value + ", setupValue=" + setupValue
				+ ", description=" + description + ", type=" + type
				+ ", module=" + module + ", parameterType=" + parameterType
				+ ", extensions=" + extensions + "]";
	}

}
