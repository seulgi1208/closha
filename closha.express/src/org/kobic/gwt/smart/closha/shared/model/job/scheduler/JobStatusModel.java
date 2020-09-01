package org.kobic.gwt.smart.closha.shared.model.job.scheduler;

import com.google.gwt.user.client.rpc.IsSerializable;

public class JobStatusModel implements IsSerializable{
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
