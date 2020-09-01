package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class TransferringWithRAPIDANTEvent extends Event<TransferringWithRAPIDANTEventHandler> {

	public final static Type<TransferringWithRAPIDANTEventHandler> TYPE = new Type<TransferringWithRAPIDANTEventHandler>();

	public TransferringWithRAPIDANTEvent() {
		super();
	}

	@Override
	protected void dispatch(TransferringWithRAPIDANTEventHandler handler) {
		// TODO Auto-generated method stub
		handler.transfferingWithRAPIDANT(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<TransferringWithRAPIDANTEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
