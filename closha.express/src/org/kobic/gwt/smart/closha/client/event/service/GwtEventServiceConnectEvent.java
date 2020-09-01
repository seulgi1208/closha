package org.kobic.gwt.smart.closha.client.event.service;

import com.google.gwt.event.shared.GwtEvent;

public class GwtEventServiceConnectEvent extends GwtEvent<GwtEventServiceConnecttHandler>{

	public static Type<GwtEventServiceConnecttHandler> TYPE = new Type<GwtEventServiceConnecttHandler>();
	
	public String instanceID;
	
	public String getInstanceID() {
		return instanceID;
	}

	public GwtEventServiceConnectEvent(String instanceID){
		this.instanceID = instanceID;
	}
	
	@Override
	public Type<GwtEventServiceConnecttHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(GwtEventServiceConnecttHandler handler) {
		// TODO Auto-generated method stub
		handler.connect(this);
	}
}
