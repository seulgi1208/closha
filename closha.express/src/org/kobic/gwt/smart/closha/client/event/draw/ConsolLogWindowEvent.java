package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ConsolLogWindowEvent extends GwtEvent<ConsolLogWindowEventHandler>{

	public static Type<ConsolLogWindowEventHandler> TYPE = new Type<ConsolLogWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ConsolLogWindowEvent(String projectName){
		this.projectName = projectName;
	}

	@Override
	public Type<ConsolLogWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ConsolLogWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.consolLogWindow(this);
	}

}
