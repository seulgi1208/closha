package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface RequestFileTransferWithRapidantEventHandler extends EventHandler{
	
	void requestEvent(RequestFileTransferWithRapidantEvent event);

}
