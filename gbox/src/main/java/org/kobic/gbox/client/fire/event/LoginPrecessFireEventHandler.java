package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface LoginPrecessFireEventHandler extends EventHandler{
	
	void loginFireEvent(LoginPrecessFireEvent event);

}
