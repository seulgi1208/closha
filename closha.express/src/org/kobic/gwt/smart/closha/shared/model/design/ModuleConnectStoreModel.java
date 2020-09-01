package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.orange.links.client.connection.Connection;

public class ModuleConnectStoreModel implements IsSerializable{
	
	private String source;
	private String target;
	private String id;
	private String key;
	private String sourceID;
	private String targetID;
	private Connection connection;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public String getTargetID() {
		return targetID;
	}
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
}
