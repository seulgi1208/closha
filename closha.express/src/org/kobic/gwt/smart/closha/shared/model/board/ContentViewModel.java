package org.kobic.gwt.smart.closha.shared.model.board;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContentViewModel implements IsSerializable{
	
	private BoardModel boardModel;
	private List<ReplyModel> commentList;
	
	public BoardModel getBoardModel() {
		return boardModel;
	}
	public void setBoardModel(BoardModel boardModel) {
		this.boardModel = boardModel;
	}
	public List<ReplyModel> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<ReplyModel> commentList) {
		this.commentList = commentList;
	}

}
