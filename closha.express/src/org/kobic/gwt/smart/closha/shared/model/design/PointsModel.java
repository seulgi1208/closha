package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PointsModel implements IsSerializable{
	
	private int left;
	private int top;
	
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	
}
