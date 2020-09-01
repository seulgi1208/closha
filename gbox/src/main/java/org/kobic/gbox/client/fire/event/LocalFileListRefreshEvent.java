package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class LocalFileListRefreshEvent extends Event<LocalFileListRefreshEventHandler> {

	public final static Type<LocalFileListRefreshEventHandler> TYPE = new Type<LocalFileListRefreshEventHandler>();

	public LocalFileListRefreshEvent() {
		super();
	}

	@Override
	protected void dispatch(LocalFileListRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.localFileListRefresh(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<LocalFileListRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
