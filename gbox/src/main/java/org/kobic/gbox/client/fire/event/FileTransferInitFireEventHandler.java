package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface FileTransferInitFireEventHandler extends EventHandler{
	
	void transferTableBindEvent(FileTransferInitFireEvent event);

}
