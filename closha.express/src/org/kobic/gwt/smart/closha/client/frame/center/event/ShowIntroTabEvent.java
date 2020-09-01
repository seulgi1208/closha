package org.kobic.gwt.smart.closha.client.frame.center.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowIntroTabEvent extends GwtEvent<ShowIntroTabEventHandler>{

	public static Type<ShowIntroTabEventHandler> TYPE = new Type<ShowIntroTabEventHandler>();
	
	public ShowIntroTabEvent(){
	}
	
	@Override
	public Type<ShowIntroTabEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowIntroTabEventHandler handler) {
		// TODO Auto-generated method stub
		handler.introTabCallEvent(this);
	}

}