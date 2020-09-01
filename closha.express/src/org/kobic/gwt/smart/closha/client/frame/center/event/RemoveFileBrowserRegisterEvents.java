package org.kobic.gwt.smart.closha.client.frame.center.event;

import com.google.gwt.event.shared.GwtEvent;

public class RemoveFileBrowserRegisterEvents extends GwtEvent<RemoveFileBrowserRegisterEventsHandler>{

	public static Type<RemoveFileBrowserRegisterEventsHandler> TYPE = new Type<RemoveFileBrowserRegisterEventsHandler>();
	
	@Override
	public Type<RemoveFileBrowserRegisterEventsHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(RemoveFileBrowserRegisterEventsHandler handler) {
		// TODO Auto-generated method stub
		handler.fileBrowserRemoveRegisterEventHandler(this);
	}

}
