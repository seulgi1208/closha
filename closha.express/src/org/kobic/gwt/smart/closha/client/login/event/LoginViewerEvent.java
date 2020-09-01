package org.kobic.gwt.smart.closha.client.login.event;

import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.GwtEvent;

public class LoginViewerEvent extends GwtEvent<LoginViewerEventHandler>{

	public static Type<LoginViewerEventHandler> TYPE = new Type<LoginViewerEventHandler>();
	
	private UserDto userDto;
	
	public UserDto getUserDto(){
		return userDto;
	}
	
	public LoginViewerEvent(UserDto userDto){
		this.userDto = userDto;
	}
	
	@Override
	public Type<LoginViewerEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(LoginViewerEventHandler handler) {
		// TODO Auto-generated method stub
		handler.loginViewerEvent(this);
	}
}
