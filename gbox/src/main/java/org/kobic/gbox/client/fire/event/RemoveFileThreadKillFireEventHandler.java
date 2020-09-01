package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface RemoveFileThreadKillFireEventHandler extends EventHandler{
	
	void kill(RemoveFileThreadKillFireEvent event);

}
