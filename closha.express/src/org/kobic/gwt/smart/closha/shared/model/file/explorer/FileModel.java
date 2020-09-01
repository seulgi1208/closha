package org.kobic.gwt.smart.closha.shared.model.file.explorer;


import com.google.gwt.user.client.rpc.IsSerializable;

public class FileModel implements IsSerializable{

	private String id;
	private String pId;
	private String name;
	private String size;
	private String lastModifyDate;
	private String lastAccessDate;
	private String type;
	private String path;
	private String pPath;
	private String user;
	private String group;
	private boolean isDic;
	private String icon;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
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
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getLastAccessDate() {
		return lastAccessDate;
	}
	public void setLastAccessDate(String lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getpPath() {
		return pPath;
	}
	public void setpPath(String pPath) {
		this.pPath = pPath;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public boolean isDic() {
		return isDic;
	}
	public void setDic(boolean isDic) {
		this.isDic = isDic;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}