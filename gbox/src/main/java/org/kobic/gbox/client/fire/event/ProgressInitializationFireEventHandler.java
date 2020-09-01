package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface ProgressInitializationFireEventHandler extends EventHandler{
	
	void progressInitialization(ProgressInitializationFireEvent event);

}
