package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface RemoteFileListRefreshEventHandler extends EventHandler{
	
	void remoteFileListRefresh(RemoteFileListRefreshEvent event);

}
