package org.kobic.gwt.smart.closha.client.event.content;

import com.google.gwt.event.shared.GwtEvent;

public class ClearTransferListGridEvent extends GwtEvent<ClearTransferListGridEventHandler>{

	public static Type<ClearTransferListGridEventHandler> TYPE = new Type<ClearTransferListGridEventHandler>();
	
	@Override
	public Type<ClearTransferListGridEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ClearTransferListGridEventHandler handler) {
		// TODO Auto-generated method stub
		handler.clearTransferListGridEvent(this);
	}

}
