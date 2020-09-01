package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class ProtocolChangeFireEvent extends Event<ProtocolChangeFireEventHandler> {

	public final static Type<ProtocolChangeFireEventHandler> TYPE = new Type<ProtocolChangeFireEventHandler>();
	
	public ProtocolChangeFireEvent() {
		super();
	}

	@Override
	protected void dispatch(ProtocolChangeFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.protocolChange(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<ProtocolChangeFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
