package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface FileTransferStatusFireEventHandler extends EventHandler{
	
	void transferDataBindEvent(FileTransferStatusFireEvent event);

}
