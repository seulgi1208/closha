package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface PathPropertiesFireEventHandler extends EventHandler{
	
	void getSelectPathProperties(PathPropertiesFireEvent event);

}
