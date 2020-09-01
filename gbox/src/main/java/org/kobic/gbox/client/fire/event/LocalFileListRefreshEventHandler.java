package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface LocalFileListRefreshEventHandler extends EventHandler{
	
	void localFileListRefresh(LocalFileListRefreshEvent event);

}
