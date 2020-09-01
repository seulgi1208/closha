package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class GboxShutdownEvent extends Event<GboxShutdownEventHandler> {

	public final static Type<GboxShutdownEventHandler> TYPE = new Type<GboxShutdownEventHandler>();

	public GboxShutdownEvent() {
		super();
	}

	@Override
	protected void dispatch(GboxShutdownEventHandler handler) {
		// TODO Auto-generated method stub
		handler.terminateFunctionEnable(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<GboxShutdownEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
