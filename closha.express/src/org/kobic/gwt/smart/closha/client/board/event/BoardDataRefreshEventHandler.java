package org.kobic.gwt.smart.closha.client.board.event;

import com.google.gwt.event.shared.EventHandler;

public interface BoardDataRefreshEventHandler extends EventHandler{

	void reloadBoardData(BoardDataRefreshEvent event);
	
}

