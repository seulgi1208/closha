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

import org.kobic.gwt.smart.closha.client.user.exception.UserException;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserController")
public interface UserController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static UserControllerAsync instance;
		public static UserControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(UserController.class);
			}
			return instance;
		}
	}
	
	public String registration(UserDto userDto);
	public boolean isUserId(String userId);
	public String findUserId(String name, String email);
	public int updatePassword(String userId, String password, String newPassword);
	public void getTempPassword(Map<String, String> config, String userId, String email) throws UserException;
	public List<UserDto> getUsers();
}
