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
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.client.controller.InstancePipelineController;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.server.service.RegisterPipelineService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InstancePipelineControllerImpl extends RemoteServiceServlet implements InstancePipelineController {

	private static final long serialVersionUID = 1L;
	
	private PipelineXmlDataUtil xmlUtils = new PipelineXmlDataUtil();

	@Override
	public String getRegisterPiplineXML(String pipelineID){
		RegisterPipelineService service = (RegisterPipelineService) SpringContextHelper.getBean(getServletContext(), "registerService");
		return service.getRegisterPipelineXML(pipelineID).getPipelineXML();
	}
	
	@Override
	public String getInstancePipelineXML(String instanceID) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return service.getInstancePipelineXML(instanceID);
	}
	
	@Override
	public InstancePipelineModel getInstancePipeline(String instanceID) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return service.getInstancePipelineFromProject(instanceID);
	}

	@Override
	public void updateWorkFlowXML(String instanceOwner, String instanceName,
			String instanceID, XmlDispatchModel xmlDispatchModel) {
		// TODO Auto-generated method stub
		String xml = xmlUtils.updateWorkFlowXML(xmlDispatchModel);
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
	}

	@Override
	public XmlDispatchModel resetWorkFlowXML(String instanceOwner,
			String instanceID, String instanceName, String pipelineID, boolean isRegister) {
		// TODO Auto-generated method stub
		
		String xml = "";
		
		if(isRegister){
			xml = getRegisterPiplineXML(pipelineID);
		}else{
			xml = getInstancePipelineXML(instanceID);
		}		
		
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
		return xmlUtils.drawParserXML(xml);
	}

	@Override
	public int updateInstancePipelineStatus(String instanceOwner,
			String instanceID, String instanceName, int state) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return service.updateInstancePielineState(instanceOwner, instanceID, instanceName, state);
	}

	private List<InstancePipelineModel> getProjectPath(Map<String, String> config, List<InstancePipelineModel> list, String instanceOwner){
		
		String path = config.get(Constants.HDFS_URL_KEY) + config.get(Constants.HDFS_DIR_KEY) + Constants.FILE_SEPERATOR
				+ instanceOwner + Constants.FILE_SEPERATOR
				+ config.get(Constants.SERVICE_NAME_KEY) + Constants.FILE_SEPERATOR + Constants.PROJECT + Constants.FILE_SEPERATOR;
		
		for(int i = 0; i < list.size(); i++){
			list.get(i).setProjectPath(path + list.get(i).getInstanceName());
		}
		
		return list;
	}
	
	@Override
	public int updateInstancePipelineRegister(String instanceID, boolean register) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return service.updateInstancePipelineRegister(instanceID, register);
	}
	
	@Override
	public List<InstancePipelineModel> getUserInstancePipelines(Map<String, String> config, String instanceOwner) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return getProjectPath(config, service.getOwnerInstancePipelines(instanceOwner), instanceOwner);
	}

	@Override
	public List<InstancePipelineModel> getUserInstancePipelines(Map<String, String> config,String instanceOwner, int status) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return getProjectPath(config, service.getOwnerInstancePipelines(instanceOwner, status), instanceOwner);
	}

	@Override
	public XmlDispatchModel getInstancePipelineXMLDispatchModel(String instanceID,
			String projectName) {
		// TODO Auto-generated method stub
		return xmlUtils.drawParserXML(getInstancePipelineXML(instanceID));
	}

	@Override
	public List<InstancePipelineExeModel> getInstancePipelineJobHistoryData(
			String instanceID) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		return service.getInstancePielineJobHistory(instanceID);
	}

	@Override
	public List<XmlParameterModel> getPrameterParserData(String key, String module, String parameter) {
		// TODO Auto-generated method stub
		
		String parameters[] = new String[3];
		String parametersStore[] = null;
		List<XmlParameterModel> parameterList = new ArrayList<XmlParameterModel>();
		
		if(parameter.split("[|]").length != 0){
			if(parameter.matches(".*&.*")){
				parametersStore = new String[parameter.split("&").length];
				parametersStore = parameter.split("&");
				for(String temp : parametersStore){
					parameters = temp.split("[|]");
					parameterList.add(setParameterEelements(parameters, key, module));
				}
			}else{
				parameters = parameter.split("[|]");
				parameterList.add(setParameterEelements(parameters, key, module));
			}
		}
		
		return parameterList;
	}
	
	private XmlParameterModel setParameterEelements(String[] parameters, String key, String module){
		
		XmlParameterModel xp = new XmlParameterModel();
		xp.setId(CommonUtils.getUUID());
		xp.setModule(module);
		xp.setKey(key);
		xp.setName(parameters[0].split("[:]")[0]);
		xp.setValue(parameters[1]);
		
		if(parameters[0].split("[:]")[2].equals("combo")){
			xp.setSetupValue(parameters[1].split(",")[0]);
		}else{
			xp.setSetupValue(parameters[1]);
		}
		
		xp.setDescription(parameters[0].split("[:]")[1]);
		xp.setType(parameters[0].split("[:]")[2]);
		xp.setParameterType(parameters[0].split("[:]")[3]);
		xp.setExtensions(parameters[0].split("[:]")[4]);
		
		return xp;
	}

	@Override
	public void removeInstancePipelineExecuteHistory(String instanceID,
			List<String> executeIDs) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		service.deleteInstancePipelineExecuteHistory(instanceID, executeIDs);
	}

	@Override
	public boolean isInstancePiplineExist(String userID, String instancePipeline) {
		// TODO Auto-generated method stub
		InstancePipelineService service = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		boolean res = service.isInstancePipelineExist(userID, instancePipeline);
		
		System.out.println("Presence of project : [" + res + "]");
		
		return res;
	}
}