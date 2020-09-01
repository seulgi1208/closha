package org.kobic.gwt.smart.closha.shared.model.preference;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ShareInstancePipelineModel implements IsSerializable{
	
	private String instanceName;
	private String instanceID;
	
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getInstanceID() {
		return instanceID;
	}
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
}
