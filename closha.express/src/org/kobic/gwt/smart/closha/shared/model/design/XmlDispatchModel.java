package org.kobic.gwt.smart.closha.shared.model.design;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class XmlDispatchModel implements IsSerializable{
	
	private String id;
	private String name;
	private String author;
	private String version;
	private String description;
	private List<XmlModuleModel> modulesBeanList;
	private List<XmlParameterModel> parametersBeanList;
	private List<XmlConnectLinkedModel> connectionsBeanList;
	
	public List<XmlModuleModel> getModulesBeanList() {
		return modulesBeanList;
	}
	public void setModulesBeanList(List<XmlModuleModel> modulesBeanList) {
		this.modulesBeanList = modulesBeanList;
	}
	public List<XmlParameterModel> getParametersBeanList() {
		return parametersBeanList;
	}
	public void setParametersBeanList(List<XmlParameterModel> parametersBeanList) {
		this.parametersBeanList = parametersBeanList;
	}
	public List<XmlConnectLinkedModel> getConnectionsBeanList() {
		return connectionsBeanList;
	}
	public void setConnectionsBeanList(List<XmlConnectLinkedModel> connectionsBeanList) {
		this.connectionsBeanList = connectionsBeanList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
