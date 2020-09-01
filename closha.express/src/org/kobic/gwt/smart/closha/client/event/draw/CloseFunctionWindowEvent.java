package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class CloseFunctionWindowEvent extends GwtEvent<CloseFunctionWindowEventHandler>{

	public static Type<CloseFunctionWindowEventHandler> TYPE = new Type<CloseFunctionWindowEventHandler>();
	
	private String projectName;
	private String windowID;
	
	public String getProjectName() {
		return projectName;
	}

	public String getWindowID() {
		return windowID;
	}
	
	public CloseFunctionWindowEvent(String projectName, String windowID){
		this.projectName = projectName;
		this.windowID = windowID;
	}

	@Override
	public Type<CloseFunctionWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(CloseFunctionWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.closeFunctionWindow(this);
	}

}
