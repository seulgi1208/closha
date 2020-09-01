package org.kobic.gwt.smart.closha.client.event.content;

import com.google.gwt.event.shared.GwtEvent;

public class PreferencesRemoveRegisterEvents extends GwtEvent<PreferencesRemoveRegisterEventsHandler>{

	public static Type<PreferencesRemoveRegisterEventsHandler> TYPE = new Type<PreferencesRemoveRegisterEventsHandler>();
	
	@Override
	public Type<PreferencesRemoveRegisterEventsHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PreferencesRemoveRegisterEventsHandler handler) {
		// TODO Auto-generated method stub
		handler.preferencesRemoveRegisterEvents(this);
	}

}
