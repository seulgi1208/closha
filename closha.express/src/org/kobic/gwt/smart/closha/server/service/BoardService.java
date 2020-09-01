package org.kobic.gwt.smart.closha.server.service;

import java.util.HashMap;
import java.util.List;

import org.kobic.gwt.smart.closha.server.dao.BoardDao;
import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class BoardService implements BoardDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Object writeCotentBoard(BoardModel boardModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("board.writeContent", boardModel);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BoardModel> getBoardList(int startNum, int endNum) {
		// TODO Auto-generated method stub
		valueMap.put("startNum", startNum);
		valueMap.put("endNum", endNum);		
		return sqlMapClientTemplate.queryForList("board.getBoardList", valueMap);
	}

	@Override
	public BoardModel getOneContent(int id) {
		// TODO Auto-generated method stub
		return (BoardModel) sqlMapClientTemplate.queryForObject("board.getOneContent", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReplyModel> getOneCommentList(int id) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("board.getOneCommentList", id);
	}

	@Override
	public void updateHitCount(int hitCount, int id) {
		// TODO Auto-generated method stub
		valueMap.put("hitCount", hitCount);
		valueMap.put("id", id);
		sqlMapClientTemplate.update("board.updateHitcount", valueMap);		
	}

	@Override
	public void updateRecommentCount(int recommentCount, int id) {
		// TODO Auto-generated method stub
		valueMap.put("recommandCount", recommentCount);
		valueMap.put("id", id);
		sqlMapClientTemplate.update("board.updateRecommentcount", valueMap);
	}
	
	@Override
	public Object wrtieCommentBoard(ReplyModel boardCommentModel) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.insert("board.writeComment", boardCommentModel);
	}

	@Override
	public void deleteBoardContent(int id) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("board.deleteContent",id);
		deleteBoardComments(id);
	}

	@Override
	public void deleteBoardComments(int linkedNum) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("board.deleteComments", linkedNum);
	}

	@Override
	public void deleteBoardComment(int id) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("board.deleteComment", id);
	}

	@Override
	public void editBoardContent(BoardModel boardModel) {
		// TODO Auto-generated method stub
		valueMap.put("type", boardModel.getType());
		valueMap.put("summary", boardModel.getSummary());
		valueMap.put("content", boardModel.getContent());
		valueMap.put("keyWord", boardModel.getKeyWord());
		valueMap.put("id", boardModel.getId());
		valueMap.put("date", boardModel.getDate());
		sqlMapClientTemplate.update("board.editCotent", valueMap);
	}

	@Override
	public void editBoardComment(ReplyModel boardCommentModel) {
		// TODO Auto-generated method stub
		valueMap.put("comment", boardCommentModel.getComment());
		valueMap.put("date", boardCommentModel.getDate());
		valueMap.put("id", boardCommentModel.getId());
		sqlMapClientTemplate.update("board.editComment", valueMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardModel> getMyBoardList(String writer) {
		// TODO Auto-generated method stub
		valueMap.put("writer", writer);
		return sqlMapClientTemplate.queryForList("board.getMyBoardList", valueMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardModel> searchBoardContent(String searchType, String searchTerm) {
		// TODO Auto-generated method stub
		valueMap.put("searchType", searchType);
		valueMap.put("searchTerm", searchTerm);		
		return sqlMapClientTemplate.queryForList("board.searchBoardList", valueMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardModel> getTypeBoardList(String searchType) {
		// TODO Auto-generated method stub
		valueMap.put("searchType", searchType);		
		return sqlMapClientTemplate.queryForList("board.getTypeBoardList", valueMap);
	}

	@Override
	public int getTotalBoradContentCount() {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClientTemplate.queryForObject("board.getTotalBoardContentNum");
	}
}