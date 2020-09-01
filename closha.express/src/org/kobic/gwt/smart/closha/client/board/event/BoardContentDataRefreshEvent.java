package org.kobic.gwt.smart.closha.client.board.event;

import com.google.gwt.event.shared.GwtEvent;

public class BoardContentDataRefreshEvent extends GwtEvent<BoardContentDataRefreshEventHandler>{

	public static Type<BoardContentDataRefreshEventHandler> TYPE = new Type<BoardContentDataRefreshEventHandler>(); 
	
	private String contentID;
	
	public String getContentID(){
		return contentID;
	}
	
	public BoardContentDataRefreshEvent(String contentID){
		this.contentID = contentID;
	}

	@Override
	public Type<BoardContentDataRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(BoardContentDataRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.viewWindowDataReload(this);
	}
}
