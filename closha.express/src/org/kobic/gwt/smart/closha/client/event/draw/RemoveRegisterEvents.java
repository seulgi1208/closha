package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class RemoveRegisterEvents extends GwtEvent<RemoveRegisterEventsHandler>{

	public static Type<RemoveRegisterEventsHandler> TYPE = new Type<RemoveRegisterEventsHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public RemoveRegisterEvents(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<RemoveRegisterEventsHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(RemoveRegisterEventsHandler handler) {
		// TODO Auto-generated method stub
		handler.removeRegisterEventHandler(this);
	}

}
