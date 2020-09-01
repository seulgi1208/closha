package org.kobic.gwt.smart.closha.shared.model.parameter;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParameterModel implements IsSerializable{
	
	private String key;
	private String setupValue;
	private boolean isExistCache;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSetupValue() {
		return setupValue;
	}
	public void setSetupValue(String setupValue) {
		this.setupValue = setupValue;
	}
	public boolean isExistCache() {
		return isExistCache;
	}
	public void setExistCache(boolean isExistCache) {
		this.isExistCache = isExistCache;
	}
}
