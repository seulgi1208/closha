package org.kobic.gwt.smart.closha.shared.model.job.monitoring;

import com.google.gwt.user.client.rpc.IsSerializable;

public class JobMonitorModel implements IsSerializable{
	
	private String title;
	private String value_1;
	private String value_2;
	private String value_3;
	private String value_4;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue_1() {
		return value_1;
	}
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_2() {
		return value_2;
	}
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getValue_3() {
		return value_3;
	}
	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}
	public String getValue_4() {
		return value_4;
	}
	public void setValue_4(String value_4) {
		this.value_4 = value_4;
	}
}
