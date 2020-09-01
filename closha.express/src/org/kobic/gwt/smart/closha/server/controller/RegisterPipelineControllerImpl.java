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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.RegisterPipelineController;
import org.kobic.gwt.smart.closha.server.service.OntologyService;
import org.kobic.gwt.smart.closha.server.service.RegisterPipelineService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.register.RegisterPipelineNetworkGraphModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RegisterPipelineControllerImpl extends RemoteServiceServlet implements RegisterPipelineController {

	private static final long serialVersionUID = 1L;

	@Override
	public List<PipelineModel> getRegisterPipelinesList() {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		return service.getRegisterPipelinesList();
	}
	

	@Override
	public List<PipelineModel> getRegisterPipelinesList(UserDto userDto) {
		// TODO Auto-generated method stub
		
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		
		if(userDto.isAdmin()){
			return service.getRegisterPipelinesList();
		}else {
			return service.getSelectRegisterPipelinesList(0);
		}
	}

	@Override
	public RegisterPipelineNetworkGraphModel getLinkedGraphData(int type) {
		// TODO Auto-generated method stub
		
		boolean isAdmin = (boolean) getThreadLocalRequest().getSession().getAttribute(Constants.IS_ADMIN_SESSION_KEY);
		
		RegisterPipelineNetworkGraphModel lm =  new RegisterPipelineNetworkGraphModel();
		
		OntologyService os = (OntologyService) SpringContextHelper.getBean(getServletContext(), "ontologyService");
		lm.setOntology(os.getOntology(type, isAdmin));
		
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		lm.setRegisterPipeline(service.getRegisterPipelinesList());
		lm.setRegisterPipelineModule(service.getRegisterPipelineModuleList());
		
		return lm;
	}

	@Override
	public List<RegisterModuleModel> getRegisterPipelineModuleList(
			boolean exceptionModule) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");

		if(exceptionModule){
			
			List<RegisterModuleModel> list = service.getRegisterPipelineModuleList();
			
			for(Iterator<RegisterModuleModel> it = list.iterator(); it.hasNext();){
				
				RegisterModuleModel rm = it.next();
				
				if (rm.getModuleName().toLowerCase().equals("start")
						|| rm.getModuleName().toLowerCase().equals("end")) {
					it.remove();
				}
			}
			
			return list;			
		}else{
			return service.getRegisterPipelineModuleList();
		}
	}
	
	@Override
	public List<RegisterModuleModel> getRegisterPipelineModuleList(
			boolean exceptionModule, UserDto userDto) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");

		boolean isAdmin = (boolean) getThreadLocalRequest().getSession().getAttribute(Constants.IS_ADMIN_SESSION_KEY);
		
		if(exceptionModule){
			
			List<RegisterModuleModel> list = new ArrayList<RegisterModuleModel>();
			
			if(isAdmin){
				list = service.getRegisterPipelineModuleList();
			}else{
				list = service.getSelectRegisterPipelineModuleList(userDto.getUserId());
			}
			
			for(Iterator<RegisterModuleModel> it = list.iterator(); it.hasNext();){
				
				RegisterModuleModel rm = it.next();
				
				if (rm.getModuleName().toLowerCase().equals("start")
						|| rm.getModuleName().toLowerCase().equals("end")) {
					it.remove();
				}
			}
			
			return list;
			
		}else{
			return service.getRegisterPipelineModuleList();
		}
	}

	@Override
	public String insertRegisterPipelineModule(
			Map<String, String> config, UserDto userDto, RegisterModuleModel registerPipelineModulesModel) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		service.registerPipelineModules(registerPipelineModulesModel);
		
		//일반 사용자 모듈 들록일 경우 메일 발송
		if(!userDto.isAdmin()){
			CommonUtils.sendEmail(config, 
					String.format(Messages.MODULE_REGISTER_TITLE, userDto.getUserId(), registerPipelineModulesModel.getModuleName()), 
					String.format(Messages.MODULE_REGISTER_MESSAGE, registerPipelineModulesModel.getModuleName(), userDto.getUserId(), CommonUtils.getCurruntTime()),
					userDto.getEmailAdress());
		}
		
		return registerPipelineModulesModel.getModuleName();
	}

	@Override
	public void insertRegisterPipelineModule(
			PipelineModel registerPipelineModel, XmlDispatchModel xmlDispatchModel) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		
		PipelineXmlDataUtil xmlUtils = new PipelineXmlDataUtil();
		registerPipelineModel.setPipelineXML(xmlUtils.updateWorkFlowXML(xmlDispatchModel));
		
		service.registerPipeline(registerPipelineModel);
	}

	@Override
	public void deleteRegisterPipeline(List<String> registerIDs) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		
		for(String registerID : registerIDs){
			service.deleteRegisterPipeline(registerID);
		}
	}

	@Override
	public void deleteRegisterPipelineModule(List<String> moduleIDs) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		
		for(String moduleID : moduleIDs){
			service.deleteRegisterPipelineModule(moduleID);
		}
	}

	@Override
	public void updateUserModuleCheck(List<String> moduleIDs, boolean isCheck) {
		// TODO Auto-generated method stub
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		
		for(String moduleID : moduleIDs){
			service.updateUserModuleCheck(moduleID, isCheck);
		}
	}
}