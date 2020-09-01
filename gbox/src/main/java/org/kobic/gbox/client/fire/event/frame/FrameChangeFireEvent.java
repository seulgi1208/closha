package org.kobic.gbox.client.fire.event.frame;

import com.pennychecker.eventbus.Event;

public class FrameChangeFireEvent extends Event<FrameChangeFireEventHandler> {

	public final static Type<FrameChangeFireEventHandler> TYPE = new Type<FrameChangeFireEventHandler>();
	
	private String frameId;	
	
	public String getFrameId(){
		return frameId;
	}
	
	public FrameChangeFireEvent(String id) {
		super();
		this.frameId = id;
	}

	@Override
	protected void dispatch(FrameChangeFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.registerFireEvent(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<FrameChangeFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
