package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface StageReSizeFireEventHandler extends EventHandler{
	
	void resize(StageReSizeFireEvent event);

}
