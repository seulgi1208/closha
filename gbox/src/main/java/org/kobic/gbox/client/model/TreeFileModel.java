package org.kobic.gbox.client.model;

import java.io.Serializable;

import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.common.Constants;

public class TreeFileModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pid;
	private String name;
	private String path;
	private long size;
	private long modified;
	private boolean isDiretory;
	
	public TreeFileModel(){
		this.id = CommonsUtil.getInstance().getUID();
		this.pid = Constants.ROOT_ID;
		this.name = Constants.ROOT_NAME;
	}
	
	public TreeFileModel(String id, String pid, String name, String path,
			long size, long modified, boolean isDirectory) {
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.path = path;
		this.size = size;
		this.modified = modified;
		this.isDiretory = isDirectory;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getModified() {
		return modified;
	}
	public void setModified(long modified) {
		this.modified = modified;
	}
	public boolean isDiretory() {
		return isDiretory;
	}
	public void setDiretory(boolean isDiretory) {
		this.isDiretory = isDiretory;
	}
	public String toString(){
		return getName();
	}
}