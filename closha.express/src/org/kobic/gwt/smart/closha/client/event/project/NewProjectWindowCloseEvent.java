package org.kobic.gwt.smart.closha.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class NewProjectWindowCloseEvent extends GwtEvent<NewProjectWindowCloseEventHandler>{

	public static Type<NewProjectWindowCloseEventHandler> TYPE = new Type<NewProjectWindowCloseEventHandler>();

	public NewProjectWindowCloseEvent(){
	}
	
	@Override
	public Type<NewProjectWindowCloseEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(NewProjectWindowCloseEventHandler handler) {
		// TODO Auto-generated method stub
		handler.setNewProjectWindowClose(this);
	}
}
