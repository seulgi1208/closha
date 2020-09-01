package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ModuleConnectionInfomModel implements IsSerializable{
	
	private String startID;
	private String endID;
	private String startModuleName;
	private String endModuleName;
	private String startKey;
	private String endKey;
	private int[][] points;
	
	public String getStartID() {
		return startID;
	}
	public void setStartID(String startID) {
		this.startID = startID;
	}
	public String getEndID() {
		return endID;
	}
	public void setEndID(String endID) {
		this.endID = endID;
	}
	public int[][] getPoints() {
		return points;
	}
	public void setPoints(int[][] points) {
		this.points = points;
	}
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getEndKey() {
		return endKey;
	}
	public void setEndKey(String endKey) {
		this.endKey = endKey;
	}
	public String getStartModuleName() {
		return startModuleName;
	}
	public void setStartModuleName(String startModuleName) {
		this.startModuleName = startModuleName;
	}
	public String getEndModuleName() {
		return endModuleName;
	}
	public void setEndModuleName(String endModuleName) {
		this.endModuleName = endModuleName;
	}
}
