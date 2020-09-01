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
package org.kobic.gwt.smart.closha.client.user.controller;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserControllerAsync {
	public void registration(UserDto userDto, AsyncCallback<String> callback);
	public void isUserId(String userId, AsyncCallback<Boolean> callback);
	public void findUserId(String name, String email, AsyncCallback<String> callback);
	public void updatePassword(String userId, String password, String newPassword, AsyncCallback<Integer> callback);
	public void getTempPassword(Map<String, String> config, String userId, String email, AsyncCallback<Void> callback);
	public void getUsers(AsyncCallback<List<UserDto>> callback);
}
