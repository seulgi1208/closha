package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface GboxShutdownEventHandler  extends EventHandler{
	
	void terminateFunctionEnable(GboxShutdownEvent event);

}
