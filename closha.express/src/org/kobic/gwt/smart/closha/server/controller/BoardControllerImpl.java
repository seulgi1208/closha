/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.kobic.gwt.smart.closha.server.controller;

import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.BoardController;
import org.kobic.gwt.smart.closha.server.service.BoardService;
import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.model.board.ContentViewModel;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BoardControllerImpl extends RemoteServiceServlet implements BoardController {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void writeContentBoard(BoardModel boardModel) {
		// TODO Auto-generated method stub
		boardModel.setDate(CommonUtils.getCurruntTime());
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.writeCotentBoard(boardModel);
	}

	@Override
	public List<BoardModel> getBoardList(int startNum, int endNum) {
		// TODO Auto-generated method stub
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getBoardList(startNum, endNum);
	}

	@Override
	public ContentViewModel getDetailBoardView(String id) {
		// TODO Auto-generated method stub
		
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		
		ContentViewModel boardViewModel = new ContentViewModel();
		boardViewModel.setBoardModel(service.getOneContent(idx));
		boardViewModel.setCommentList(service.getOneCommentList(idx));
	
		return boardViewModel;
	}

	@Override
	public void updateHitCount(String id) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		BoardModel boardModel = service.getOneContent(idx);
		int hitCount = Integer.parseInt(boardModel.getHitCount())+1;
		service.updateHitCount(hitCount, idx);
	}

	@Override
	public List<ReplyModel> getBoardCommentList(String id) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getOneCommentList(idx);
	}

	@Override
	public void writeCommentBoard(ReplyModel boardCommentModel, String id) {
		// TODO Auto-generated method stub
		
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		
		int idx = Integer.parseInt(id);
		
		BoardModel boardModel = service.getOneContent(idx);
		int recommentCount = Integer.parseInt(boardModel.getRecommandCount())+1;
		service.updateRecommentCount(recommentCount, idx);
		
		boardCommentModel.setDate(CommonUtils.getCurruntTime());
		service.wrtieCommentBoard(boardCommentModel);
	}

	@Override
	public void deleteContent(String id) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.deleteBoardContent(idx);
	}

	@Override
	public void deleteComments(String linkedNum) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(linkedNum);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.deleteBoardComments(idx);
	}

	@Override
	public void deleteComment(String id) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.deleteBoardComment(idx);
	}

	@Override
	public void editCotent(BoardModel boardModel) {
		// TODO Auto-generated method stub
		boardModel.setDate(CommonUtils.getCurruntTime());
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.editBoardContent(boardModel);
	}

	@Override
	public BoardModel getOneBoardContent(String id) {
		// TODO Auto-generated method stub
		int idx = Integer.parseInt(id);
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getOneContent(idx);
	}

	@Override
	public void editComment(ReplyModel boardCommentModel) {
		// TODO Auto-generated method stub
		boardCommentModel.setDate(CommonUtils.getCurruntTime());
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		service.editBoardComment(boardCommentModel);
	}

	@Override
	public List<BoardModel> getMyBoardList(String writer) {
		// TODO Auto-generated method stub
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getMyBoardList(writer);
	}

	@Override
	public List<BoardModel> searchCotentList(String searchType, String searchTerm) {
		// TODO Auto-generated method stub
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.searchBoardContent(searchType, searchTerm);
	}

	@Override
	public List<BoardModel> getTypeBoardList(String searchType) {
		// TODO Auto-generated method stub
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getTypeBoardList(searchType);
	}

	@Override
	public int getTotalBoardContentCount() {
		// TODO Auto-generated method stub
		BoardService service = (BoardService) SpringContextHelper.getBean(getServletContext(), "boardService");
		return service.getTotalBoradContentCount();
	}
}
