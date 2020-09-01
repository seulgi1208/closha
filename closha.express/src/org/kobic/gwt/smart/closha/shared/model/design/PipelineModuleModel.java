package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PipelineModuleModel implements IsSerializable{
	
	private String name;
	private String desc;
	private String author;
	private String registerDate;
	private String updateDate;
	private String version;
	private String type;
	private String accessID;
	
	public PipelineModuleModel(String name, String desc, String author,
			String registerDate, String updateDate, String version, String type, String accessID) {
		this.name = name;
		this.desc = desc;
		this.author = author;
		this.registerDate = registerDate;
		this.updateDate = updateDate;
		this.version = version;
		this.type = type;
		this.accessID = accessID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getAccessID() {
		return accessID;
	}

	public void setAccessID(String accessID) {
		this.accessID = accessID;
	}
}
