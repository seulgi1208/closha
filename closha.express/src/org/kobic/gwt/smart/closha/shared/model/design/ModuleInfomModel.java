package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModuleInfomModel implements IsSerializable{
	
	private String left;
	private String top;
	private String id;
	private String identifier;
	private String name;
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
