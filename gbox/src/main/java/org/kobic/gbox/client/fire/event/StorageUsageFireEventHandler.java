package org.kobic.gbox.client.fire.event;

import com.pennychecker.eventbus.EventHandler;

public interface StorageUsageFireEventHandler extends EventHandler{
	
	void storageUsageRefresh(StorageUsageFireEvent event);

}
