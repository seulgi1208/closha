package org.kobic.gbox.client.fire.event.frame;

import com.pennychecker.eventbus.EventHandler;

public interface FrameChangeFireEventHandler extends EventHandler{
	void registerFireEvent(FrameChangeFireEvent event);
}
