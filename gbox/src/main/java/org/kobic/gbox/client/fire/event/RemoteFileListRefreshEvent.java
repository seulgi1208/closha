package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.Event;

public class RemoteFileListRefreshEvent extends Event<RemoteFileListRefreshEventHandler> {

	public final static Type<RemoteFileListRefreshEventHandler> TYPE = new Type<RemoteFileListRefreshEventHandler>();

	public RemoteFileListRefreshEvent() {
		super();
	}

	@Override
	protected void dispatch(RemoteFileListRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.remoteFileListRefresh(this);;
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<RemoteFileListRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
