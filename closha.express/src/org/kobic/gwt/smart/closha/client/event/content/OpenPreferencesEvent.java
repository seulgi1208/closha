package org.kobic.gwt.smart.closha.client.event.content;

import com.google.gwt.event.shared.GwtEvent;

public class OpenPreferencesEvent extends GwtEvent<OpenPreferencesEventHandler>{
	
	public static Type<OpenPreferencesEventHandler> TYPE = new Type<OpenPreferencesEventHandler>();
	
	public OpenPreferencesEvent(){
		
	}

	@Override
	public Type<OpenPreferencesEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(OpenPreferencesEventHandler handler) {
		// TODO Auto-generated method stub
		handler.getOpenPreferences(this);
	}
}
