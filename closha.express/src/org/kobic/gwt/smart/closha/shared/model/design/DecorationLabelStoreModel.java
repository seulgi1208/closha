package org.kobic.gwt.smart.closha.shared.model.design;

import org.kobic.gwt.smart.closha.client.pipeline.design.component.ProcessStatusLabel;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DecorationLabelStoreModel implements IsSerializable{
	
	private String key;
	private ProcessStatusLabel statusLabel;
	private String moduleName;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ProcessStatusLabel getStatusLabel() {
		return statusLabel;
	}
	public void setStatusLabel(ProcessStatusLabel statusLabel) {
		this.statusLabel = statusLabel;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
