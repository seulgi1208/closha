package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class StageReSizeFireEvent extends Event<StageReSizeFireEventHandler> {

	public final static Type<StageReSizeFireEventHandler> TYPE = new Type<StageReSizeFireEventHandler>();
	
	private double width;
	private double height;
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}

	public StageReSizeFireEvent(double width, double height){
		super();
		this.width = width;
		this.height = height;
	}

	@Override
	protected void dispatch(StageReSizeFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.resize(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<StageReSizeFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
