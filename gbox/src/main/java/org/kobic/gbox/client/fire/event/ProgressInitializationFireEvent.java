package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class ProgressInitializationFireEvent extends Event<ProgressInitializationFireEventHandler> {

	public final static Type<ProgressInitializationFireEventHandler> TYPE = new Type<ProgressInitializationFireEventHandler>();
	
	
	public ProgressInitializationFireEvent() {
		super();
	}

	@Override
	protected void dispatch(ProgressInitializationFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.progressInitialization(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<ProgressInitializationFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
