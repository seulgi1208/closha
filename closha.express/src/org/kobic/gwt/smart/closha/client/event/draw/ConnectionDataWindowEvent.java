package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ConnectionDataWindowEvent extends GwtEvent<ConnectionDataWindowEventHandler>{

	public static Type<ConnectionDataWindowEventHandler> TYPE = new Type<ConnectionDataWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ConnectionDataWindowEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<ConnectionDataWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ConnectionDataWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.connectionDataWindowEvent(this);
	}

}
