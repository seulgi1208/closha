package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface RenameFireEventHandler extends EventHandler{
	
	void rename(RenameFireEvent event);

}
