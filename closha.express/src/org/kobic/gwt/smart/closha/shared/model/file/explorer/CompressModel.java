package org.kobic.gwt.smart.closha.shared.model.file.explorer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CompressModel implements IsSerializable{

	private String parentPath;
	private String path;
	private String name;
	private String size;
	private String modify;
	
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
}
