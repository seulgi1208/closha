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
package org.kobic.gwt.smart.closha.server.controller.login;

import java.io.File;
import java.util.Map;

import org.apache.thrift.TException;
import org.kobic.gwt.smart.closha.client.login.controller.LoginController;
import org.kobic.gwt.smart.closha.client.login.exception.LoginException;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.ksso.thrift.client.ClientHandler;
import org.kobic.sso.client.model.Member;
import org.kobic.sso.client.model.UserAccount;
import org.kobic.sso.member.service.MemberService;
import org.kobic.sso.member.service.MemberServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginControllerImpl extends RemoteServiceServlet implements LoginController {

	private static final long serialVersionUID = 1L;

	@Override
	public UserDto login(Map<String, String> config, String userId, String password) throws LoginException {
		// TODO Auto-generated method stub
		
		MemberService service = new MemberServiceImpl();
		
		UserAccount userAccount = new UserAccount();
		userAccount.setMemberId(userId);
		userAccount.setMemberPassword(password);
		Member member = service.loginAccount(userAccount);
		
		boolean login = member != null ? true : false;
		
		if(login){
			
			member = service.getMember(member.getMemberNo(), member.getMemberId());
			
			UserDto userDto = new UserDto();
			userDto.setId(Integer.parseInt(member.getMemberNo()));
			userDto.setUserName(member.getMemberNm());
			userDto.setUserId(member.getMemberId());
			userDto.setPassword(member.getMemberPassword());
			userDto.setOrganization(member.getMemberPstinst());
			userDto.setPosition(member.getMemberOfcps());
			userDto.setEmailAdress(member.getMemberEmail());
			userDto.setRegdate(member.getMemberRegDt());
			userDto.setLastLogin(member.getMemberLastLoginDt());
			userDto.setTel(member.getMemberTel());
			userDto.setHp(member.getMemberMbtl());
			userDto.setFax(null);
			userDto.setAdmin(service.isAdmin(member.getMemberNo()));
			
			//isAdmin add session 
			getThreadLocalRequest().getSession().setAttribute(Constants.IS_ADMIN_SESSION_KEY, userDto.isAdmin());
			
			return userDto;
			
		}else{
			throw new LoginException("Login failed. please try again.");
		}
		
//		ClientHandler handler = new ClientHandler();
//		
//		UserModel dto = null;
//		try {
//			dto = handler.getUser(userId);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(dto == null){
//			throw new LoginException("ID you have entered does not exist.");
//		}else{
//			if(dto.getPassword().equals(password)){
//				
//				UserDto userDto = new UserDto();
//				userDto.setId(dto.getId());
//				userDto.setUserName(dto.getUser_name());
//				userDto.setUserId(dto.getUser_id());
//				userDto.setPassword(dto.getPassword());
//				userDto.setOrganization(dto.getOrganization());
//				userDto.setPosition(dto.getPosition());
//				userDto.setEmailAdress(dto.getEmail_adress());
//				userDto.setRegdate(dto.getRegdate());
//				userDto.setLastLogin(dto.getLast_login());
//				userDto.setTel(dto.getTel());
//				userDto.setHp(dto.getHp());
//				userDto.setFax(dto.getFax());
//				userDto.setAdmin(dto.isAdmin());
//				
//				//isAdmin add session 
//				getThreadLocalRequest().getSession().setAttribute(Constants.IS_ADMIN_SESSION_KEY, dto.isAdmin());
//				
//				return userDto;
//			}else{
//				throw new LoginException("Password does not match.");
//			}
//		}
	}

	@SuppressWarnings("unused")
	private void checkDirectory(Map<String, String> config, String userId) {
		// TODO Auto-generated method stub
		
		String home_path = config.get(Constants.HDFS_DIR_KEY) + File.separator + userId;
		
		if (!HadoopFileSystem.getInstance(config).exist(
				home_path + File.separator
						+ config.get(Constants.SERVICE_NAME_KEY)
						+ File.separator + Constants.PROJECT)) {
			HadoopFileSystem.getInstance(config).makeDirectory(
					home_path + File.separator
							+ config.get(Constants.SERVICE_NAME_KEY)
							+ File.separator + Constants.PROJECT);
		}
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
}