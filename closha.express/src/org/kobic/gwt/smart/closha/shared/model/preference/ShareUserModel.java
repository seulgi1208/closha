package org.kobic.gwt.smart.closha.shared.model.preference;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ShareUserModel implements IsSerializable{
	
	private String userID;
	private String email;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
