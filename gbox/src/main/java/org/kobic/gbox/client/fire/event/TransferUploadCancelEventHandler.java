package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface TransferUploadCancelEventHandler extends EventHandler{
	
	void transferCancel(TransferUploadCancelEvent event);

}
