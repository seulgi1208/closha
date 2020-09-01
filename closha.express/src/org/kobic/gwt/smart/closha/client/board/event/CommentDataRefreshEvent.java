package org.kobic.gwt.smart.closha.client.board.event;

import com.google.gwt.event.shared.GwtEvent;

public class CommentDataRefreshEvent extends GwtEvent<CommentDataRefreshEventHandler>{

	public static Type<CommentDataRefreshEventHandler> TYPE = new Type<CommentDataRefreshEventHandler>(); 
	
	private String linkedNum;
	
	public String getLinkedNum(){
		return linkedNum;
	}
	
	public CommentDataRefreshEvent(String linkedNum){
		this.linkedNum = linkedNum;
	}

	@Override
	public Type<CommentDataRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(CommentDataRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.commentListGridDataReload(this);
	}
}
