package org.kobic.gbox.client.model;

import java.io.Serializable;

import org.kobic.gbox.client.common.CommonsUtil;

public class TableFileModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String path;
	private long size;
	private long modified;
	private boolean isDiretory;
	private String status;

	public TableFileModel() {

	}

	public TableFileModel(String name, String path, long size, long modified, boolean isDirectory) {
		this.name = name;
		this.path = path;
		this.size = size;
		this.modified = modified;
		this.isDiretory = isDirectory;
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

	public String getVname() {
		return getName() + "|" + (isDiretory() ? "icons/folder.png" : "icons/page_white.png");
	}

	public String getType() {
		return isDiretory() ? "Dir" : "File";
	}

	public String getDate() {
		return CommonsUtil.getInstance().timeStampToDate(getModified());
	}

	public String getCalculation() {
		if (!isDiretory) {
			return CommonsUtil.getInstance().humanReadableByteCount(getSize(), true);
		} else {
			return "";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}