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
package org.kobic.gwt.smart.closha.server.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.kobic.gwt.smart.closha.client.user.controller.UserController;
import org.kobic.gwt.smart.closha.client.user.exception.UserException;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.ksso.thrift.autowise.service.UserModel;
import org.kobic.ksso.thrift.client.ClientHandler;
import org.kobic.sso.client.model.Member;
import org.kobic.sso.member.service.MemberService;
import org.kobic.sso.member.service.MemberServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserControllerImpl extends RemoteServiceServlet implements UserController {

	private static final long serialVersionUID = 1L;

	@Override
	public String registration(UserDto userDto) {
		// TODO Auto-generated method stub
		
		ClientHandler handler = new ClientHandler();
		
		UserModel user = new UserModel();
		user.setId(userDto.getId());
		user.setUser_name(userDto.getUserName());
		user.setUser_id(userDto.getUserId());
		user.setPassword(userDto.getPassword());
		user.setOrganization(userDto.getOrganization());
		user.setPosition(userDto.getPosition());
		user.setEmail_adress(userDto.getEmailAdress());
		user.setRegdate(userDto.getRegdate());
		user.setLast_login(userDto.getLastLogin());
		user.setTel(userDto.getTel());
		user.setHp(userDto.getHp());
		user.setFax(userDto.getFax());
		user.setAdmin(userDto.isAdmin());
		
		String res = null;
		
		try {
			res = handler.addUser(user);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public boolean isUserId(String userId) {
		// TODO Auto-generated method stub
		
		ClientHandler handler = new ClientHandler();
		
		boolean res = false;
		
		try {
			res = handler.getUser(userId) == null ? true : false;
		} catch (TException e) {
			// TODO Auto-generated catch block
			res = true;
		}
		
		return res;
	}

	@Override
	public int updatePassword(String userId, String password, String newPassword) {
		// TODO Auto-generated method stub
		
		ClientHandler handler = new ClientHandler();
		
		int res = -1;

		try {
			res = handler.updatePassword(userId, password, newPassword);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		
		List<UserDto> list = new ArrayList<UserDto>();
		
		MemberService service = new MemberServiceImpl();
		
		for(Member member : service.getAllMember()){
			
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
			userDto.setAdmin(false);
			
			list.add(userDto);
		}
		
//		ClientHandler handler = new ClientHandler();
//		
//		List<UserDto> list = new ArrayList<UserDto>();
//		
//		try {
//			for(UserModel dto : handler.getUsers()){
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
//				list.add(userDto);
//				
//			}
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return list;
	}

	@Override
	public String findUserId(String name, String email) {
		// TODO Auto-generated method stub
		
		ClientHandler handler = new ClientHandler();
		
		UserModel userDto = null;
		try {
			userDto = handler.findUser(name, email);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(userDto == null){
			return null;
		}else{
			return userDto.getUser_id();
		}
	}

	@Override
	public void getTempPassword(Map<String, String> config, String userId, String email) throws UserException{
		// TODO Auto-generated method stub
		
		ClientHandler handler = new ClientHandler();
		
		try {
			if(handler.getUser(userId) != null){
				String tempPassword = handler.getTempPassword(userId, email);
				CommonUtils.sendEmail(config, String.format(Messages.FIND_PASSWORD_MAIL_TITLE, userId), 
						String.format(Messages.FIND_PASSWORD_MAIL_MESSAGE, userId, tempPassword), email);
			}else{
				throw new UserException("ID you have entered does not exist.");
			}
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}