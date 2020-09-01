package org.kobic.gwt.smart.closha.client.board.event;

import com.google.gwt.event.shared.GwtEvent;

public class BoardDataRefreshEvent extends GwtEvent<BoardDataRefreshEventHandler>{

	public static Type<BoardDataRefreshEventHandler> TYPE = new Type<BoardDataRefreshEventHandler>();
	
	@Override
	public Type<BoardDataRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(BoardDataRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.reloadBoardData(this);
	}
}
