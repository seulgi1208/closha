package org.kobic.gwt.smart.closha.client.board.event;

import com.google.gwt.event.shared.EventHandler;

public interface CommentDataRefreshEventHandler extends EventHandler{

	void commentListGridDataReload(CommentDataRefreshEvent event);
	
}