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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.kobic.gwt.smart.closha.client.controller.ProjectController;
import org.kobic.gwt.smart.closha.server.handler.InstancePipelineServiceHandler;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.server.service.ProjectService;
import org.kobic.gwt.smart.closha.server.service.RegisterPipelineService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareInstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareUserModel;
import org.kobic.gwt.smart.closha.shared.model.project.ProjectModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectControllerImpl extends RemoteServiceServlet implements ProjectController {
	
	private static final long serialVersionUID = 1L;

	private final String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
	
	@Override
	public InstancePipelineModel makeNewProject(ProjectModel projectModel, String email) {
		// TODO Auto-generated method stub
//		ProjectService pService = (ProjectService) SpringContextHelper.getBean(getServletContext(), "projectService");
		RegisterPipelineService rService = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		InstancePipelineService iService = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		
		projectModel.setProjectID(CommonUtils.getUUID());
		projectModel.setCreateDate(CommonUtils.getCurruntTime());
		projectModel.setProjectName(projectModel.getProjectName().replaceAll(match, "_").replaceAll("\\p{Z}", "_"));
		projectModel.setProjectDesc(projectModel.getProjectDesc().replaceAll(match, "_"));
		
		System.out.println(projectModel.getProjectName() + ":" + projectModel.getProjectDesc());
		
		boolean isNew = false;
		
		if(projectModel.getPipelineID().equals(Constants.NEW_PROJECT_DESIGN_ID)){
			isNew = true;
			projectModel.setPipelineID(UUID.uuid());
		}

		InstancePipelineModel iModel = new InstancePipelineModel();
		
		if(isNew){
			iModel = getNewProjectDesignInstancePipelineModel(projectModel, email);
		}else{
			
			PipelineModel rModel = rService.getRegisterPipelineXML(projectModel.getPipelineID());
	
			iModel.setInstanceID(projectModel.getProjectID());
			iModel.setPipelineID(projectModel.getPipelineID());
			iModel.setPipelineName(rModel.getPipelineName());
			iModel.setInstanceOwner(projectModel.getProjectOwner());
			iModel.setOwnerEmail(email);
			iModel.setInstanceName(projectModel.getProjectName());
			iModel.setInstanceDesc(projectModel.getProjectDesc());
			iModel.setCreateDate(projectModel.getCreateDate());
			iModel.setStatus(0);
			iModel.setExeID(null);
			
			//여기서 등록 경로를 변경해준다. 아이디 + 프로젝트 
			PipelineXmlDataUtil pu = new PipelineXmlDataUtil();
			
			XmlDispatchModel xmlDispatchModel = pu.drawParserXML(rModel.getPipelineXML());
			
			String path = null;
			String tmp[] = null;
			String value = "";
			
			for(XmlParameterModel pm : xmlDispatchModel.getParametersBeanList()){
				
				if(pm.getType().equals("FILE") || pm.getType().equals("FOLDER") || pm.getType().equals("REFERENCE")){
				
					//System.out.println(pm.getType() + ":" + pm.getModule() + ":" + pm.getName() + ":" + pm.getSetupValue());
					
					path = pm.getSetupValue();
					tmp = path.split("/");
					
					if(tmp.length >= 6 && !tmp[2].equals("kobic_public")){
						
						tmp[5] = projectModel.getProjectName();
						tmp[2] = projectModel.getProjectOwner();
						
						value = StringUtils.join(tmp, "/");
						
						pm.setSetupValue(value);
					}
				}
			}
			
			String updateXML = pu.updateWorkFlowXML(xmlDispatchModel);
			
			/**
			 * 완료
			 */
			
			iModel.setInstanceXML(updateXML);
			iModel.setExeCount(0);	
			iModel.setRegister(true);
		}	
		
//		pService.makeNewProject(projectModel);
		iService.newInstancePipeline(iModel);
		
		return iModel;
	}

	@Override
	public List<ProjectModel> getUserProjectList(String userID) {
		// TODO Auto-generated method stub
		ProjectService service = (ProjectService) SpringContextHelper.getBean(getServletContext(), "projectService");
		return service.getProjectList(userID);
	}

	@Override
	public boolean existUserProjectCheck(String userID, String projectName) {
		// TODO Auto-generated method stub
		ProjectService service = (ProjectService) SpringContextHelper.getBean(getServletContext(), "projectService");
		return service.existUserProject(userID, projectName);
	}

	@Override
	public void sharingProjects(Map<String, String> config, String userID, String email, 
			List<ShareInstancePipelineModel> sharedProjectList,
			List<ShareUserModel> shareUserList) {
		// TODO Auto-generated method stub
		
		System.out.println("Sharing the analysis pipeline will start.");
		
		String shareProjectName = null;
		
		for(ShareInstancePipelineModel sm : sharedProjectList){
			for(ShareUserModel sharedUser : shareUserList){
				
				shareProjectName = sm.getInstanceName() + "_" + userID + "_share";
				
				//project directory to directory copy
				CommonUtils.projectSharedCopy(config, userID, sharedUser.getUserID(), shareProjectName);
				
				//database update
				sharedInsertInstancePipielineData(sm.getInstanceID(), shareProjectName, sharedUser, userID);
			}
		}

		List<String> pipeline = new ArrayList<String>();
		List<String> user = new ArrayList<String>();
		
		for(ShareInstancePipelineModel in : sharedProjectList){
			pipeline.add(in.getInstanceName());
		}
		
		for(ShareUserModel su : shareUserList){
			user.add(su.getUserID());
		}
		
		//email 전송
		CommonUtils.sendEmail(config, String.format(
				Messages.SHARE_PROJECT_TITLE, userID, 
				StringUtils.join(pipeline, ",")), String.format(
				Messages.SHARE_PROJECT_MESSAGE,
				StringUtils.join(pipeline, ","),
				StringUtils.join(pipeline, ","),
				StringUtils.join(user, ",")), email);
	}
	
	private InstancePipelineModel getNewProjectDesignInstancePipelineModel(
			ProjectModel projectModel, String email) {
		
		InstancePipelineModel iModel = new InstancePipelineModel();
		iModel.setInstanceID(projectModel.getProjectID());
		iModel.setPipelineID(projectModel.getPipelineID());
		iModel.setPipelineName(Constants.NEW_PROJECT_DESIGN);
		iModel.setInstanceOwner(projectModel.getProjectOwner());
		iModel.setOwnerEmail(email);
		iModel.setInstanceName(projectModel.getProjectName());
		iModel.setInstanceDesc(projectModel.getProjectDesc());
		iModel.setCreateDate(projectModel.getCreateDate());
		iModel.setStatus(0);
		iModel.setExeID(null);
		iModel.setInstanceXML(getDefaultXML(projectModel));
		iModel.setExeCount(0);
		iModel.setRegister(false);
		
		return iModel;
	}
	
	private String getDefaultXML(ProjectModel projectModel){
		
		String path = ProjectControllerImpl.class.getResource("/conf/template.xml").getPath();
		
		String xml = "";
		
		try {

			xml = String.format(FileUtils.readFileToString(new File(path)),
					projectModel.getProjectID(), projectModel.getProjectName(),
					projectModel.getProjectOwner(),
					projectModel.getProjectDesc(), UUID.uuid(), UUID.uuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml;
	}
	
	private void sharedInsertInstancePipielineData(String instanceID, String instanceName, 
			ShareUserModel shareUserModel, String userID){
		
		InstancePipelineService iService = InstancePipelineServiceHandler.getService();
//		ProjectService pService = ProjectServiceHandler.getService();
		
		String uniqID = CommonUtils.getUUID();
		String date = CommonUtils.getCurruntTime();
		
		InstancePipelineModel iModel = iService.getInstancePipelineFromProject(instanceID);
		iModel.setInstanceID(uniqID);
		iModel.setExeID(null);
		iModel.setInstanceOwner(shareUserModel.getUserID());
		iModel.setOwnerEmail(shareUserModel.getEmail());
		iModel.setInstanceName(instanceName);
		iModel.setStatus(0);
		iModel.setExeCount(0);
		iModel.setCreateDate(date);
		
		PipelineXmlDataUtil pu = new PipelineXmlDataUtil();
		
		XmlDispatchModel xmlDispatchModel = pu.drawParserXML(iModel.getInstanceXML());
		
		String path = null;
		String tmp[] = null;
		String value = "";
		
		for(XmlParameterModel pm : xmlDispatchModel.getParametersBeanList()){
			if(pm.getType().equals("FILE") || pm.getType().equals("FOLDER")){
				
				path = pm.getSetupValue();
				tmp = path.split("/");
				
				if(tmp.length >= 6 && !tmp[2].equals("kobic_public")){
					
					tmp[5] = instanceName;
					tmp[2] = shareUserModel.getUserID();
					
					value = StringUtils.join(tmp, "/");
					
					pm.setSetupValue(value);
				}
			}
		}
		
		String updateXML = pu.updateWorkFlowXML(xmlDispatchModel);
		
		iModel.setInstanceXML(updateXML);
		
		iService.newInstancePipeline(iModel);
		
//		ProjectModel pModel = pService.getProject(instanceID);
//		pModel.setProjectID(uniqID);
//		pModel.setProjectOwner(shareUserModel.getUserID());
//		pModel.setProjectName(projectName);
//		pModel.setCreateDate(date);
//
//		pService.makeNewProject(pModel);
	}
}