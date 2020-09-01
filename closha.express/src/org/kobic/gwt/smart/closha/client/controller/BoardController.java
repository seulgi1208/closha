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
package org.kobic.gwt.smart.closha.client.controller;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.board.ReplyModel;
import org.kobic.gwt.smart.closha.shared.model.board.ContentViewModel;
import org.kobic.gwt.smart.closha.shared.model.board.BoardModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("BoardController")
public interface BoardController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BoardControllerAsync instance;
		public static BoardControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(BoardController.class);
			}
			return instance;
		}
	}
	
	public void writeContentBoard(BoardModel boardModel);
	
	public List<BoardModel> getBoardList(int startNum, int endNum);
	
	public List<BoardModel> getMyBoardList(String writer);
	
	public ContentViewModel getDetailBoardView(String id);
	
	public BoardModel getOneBoardContent(String id);
	
	public void updateHitCount(String id);
	
	public void writeCommentBoard(ReplyModel boardCommentModel, String id);
	
	public List<ReplyModel> getBoardCommentList(String id);
	
	public void deleteContent(String id);
	
	public void deleteComments(String linkedNum);
	
	public void deleteComment(String id);
	
	public void editCotent(BoardModel boardModel);
	
	public void editComment(ReplyModel boardCommentModel);
	
	public List<BoardModel> searchCotentList(String searchType, String searchTerm);
	
	public List<BoardModel> getTypeBoardList(String searchType);
	
	public int getTotalBoardContentCount();
}
