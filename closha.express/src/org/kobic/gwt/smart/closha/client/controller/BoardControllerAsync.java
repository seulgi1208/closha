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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BoardControllerAsync {
	public void writeContentBoard(BoardModel boardModel, AsyncCallback<Void> callback);
	
	public void getBoardList(int startNum, int endNum, AsyncCallback<List<BoardModel>> callback);
	
	public void getMyBoardList(String writer, AsyncCallback<List<BoardModel>> callback);
	
	public void getDetailBoardView(String id, AsyncCallback<ContentViewModel> callback);
	
	public void getOneBoardContent(String id, AsyncCallback<BoardModel> callback);
	
	public void updateHitCount(String id, AsyncCallback<Void> callback);
	
	public void writeCommentBoard(ReplyModel boardCommentModel, String id, AsyncCallback<Void> callback);
	
	public void getBoardCommentList(String id, AsyncCallback<List<ReplyModel>> callback);
	
	public void deleteContent(String id, AsyncCallback<Void> callback);
	
	public void deleteComments(String linkedNum, AsyncCallback<Void> callback);
	
	public void deleteComment(String id, AsyncCallback<Void> callback);
	
	public void editCotent(BoardModel boardModel, AsyncCallback<Void> callback);
	
	public void editComment(ReplyModel boardCommentModel, AsyncCallback<Void> callback);
	
	public void searchCotentList(String searchType, String searchTerm, AsyncCallback<List<BoardModel>> callback);
	
	public void getTypeBoardList(String searchType, AsyncCallback<List<BoardModel>> callback);
	
	public void getTotalBoardContentCount(AsyncCallback<Integer> callback);
}
