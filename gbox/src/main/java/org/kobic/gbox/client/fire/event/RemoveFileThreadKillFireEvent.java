package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class RemoveFileThreadKillFireEvent extends Event<RemoveFileThreadKillFireEventHandler> {

	public final static Type<RemoveFileThreadKillFireEventHandler> TYPE = new Type<RemoveFileThreadKillFireEventHandler>();

	public RemoveFileThreadKillFireEvent(){
		super();
	}

	@Override
	protected void dispatch(RemoveFileThreadKillFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.kill(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<RemoveFileThreadKillFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
