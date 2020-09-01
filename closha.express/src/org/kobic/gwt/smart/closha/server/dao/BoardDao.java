package org.kobic.gwt.smart.closha.server.dao;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;

public interface BoardDao {
	
	Object writeCotentBoard(BoardModel boardModel);
	
	List<BoardModel> getBoardList(int startNum, int endNum);
	
	List<BoardModel> getMyBoardList(String writer);
	
	int getTotalBoradContentCount();
	
	BoardModel getOneContent(int id);
	
	List<ReplyModel> getOneCommentList(int id);
	
	void updateHitCount(int hitCount, int id);
	
	void updateRecommentCount(int recommentCount, int id);
	
	Object wrtieCommentBoard(ReplyModel boardCommentModel);
	
	void deleteBoardContent(int id);
	
	void deleteBoardComments(int linkedNum);
	
	void deleteBoardComment(int id);
	
	void editBoardContent(BoardModel boardModel);
	
	void editBoardComment(ReplyModel boardCommentModel);
	
	List<BoardModel> searchBoardContent(String searchType, String searchTerm);
	
	List<BoardModel> getTypeBoardList(String searchType);
}
