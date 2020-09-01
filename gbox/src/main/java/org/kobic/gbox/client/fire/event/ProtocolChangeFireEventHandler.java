package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface ProtocolChangeFireEventHandler extends EventHandler{
	
	void protocolChange(ProtocolChangeFireEvent event);

}
