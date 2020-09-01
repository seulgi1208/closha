package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class StorageUsageFireEvent extends Event<StorageUsageFireEventHandler> {

	public final static Type<StorageUsageFireEventHandler> TYPE = new Type<StorageUsageFireEventHandler>();

	public StorageUsageFireEvent() {
		super();
	}

	@Override
	protected void dispatch(StorageUsageFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.storageUsageRefresh(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<StorageUsageFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
