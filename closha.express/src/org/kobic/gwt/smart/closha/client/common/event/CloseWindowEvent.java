package org.kobic.gwt.smart.closha.client.common.event;

import com.google.gwt.event.shared.GwtEvent;

public class CloseWindowEvent extends GwtEvent<CloseWindowEventHandler>{

	public static Type<CloseWindowEventHandler> TYPE = new Type<CloseWindowEventHandler>();
	
	@Override
	public Type<CloseWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(CloseWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.closeLoginWinodw(this);
	}

}
