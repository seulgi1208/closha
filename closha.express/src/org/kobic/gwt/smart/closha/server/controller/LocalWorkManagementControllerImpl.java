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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.kobic.gwt.smart.closha.client.controller.LocalWorkManagementController;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushServiceEvent;
import org.kobic.gwt.smart.closha.server.handler.InstancePipelineServiceHandler;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.batch.service.LocalBatchProcessService;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.event.service.GWTMessagePushServiceModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.design.ChangeProcessStatus;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;
import org.kobic.gwt.smart.closha.shared.utils.design.XmlDataConverter;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.common.collect.ListMultimap;

import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class LocalWorkManagementControllerImpl extends RemoteEventServiceServlet implements LocalWorkManagementController {

	private static final long serialVersionUID = 1L;
	
	private static InstancePipelineService service = InstancePipelineServiceHandler.getService();
	private static PipelineXmlDataUtil xmlUtils = new PipelineXmlDataUtil();
	
	private static Map<String, Timer> pipelineTimerList = new HashMap<String, Timer>();
	private static Map<String, String> runtimeMap = new HashMap<String, String>();
	
	private static boolean isMailUseage;
	
	public String getRootPath(Map<String, String> config, String userId){
		
//		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
//				+ userId + File.separator
//				+ config.get(Constants.SERVICE_NAME_KEY);
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator + userId;

		return HadoopFileSystem.getInstance(config).getRootDirectoryPath(path);
	}
	
	@Override
	public void pipelineExecute(Map<String, String> config, String instanceOwner, String projectName,
			String instanceID, String pipelineID, String ownerEmail,
			XmlDispatchModel xmlDispatchModel) {
		// TODO Auto-generated method stub
		
		String working_path = getRootPath(config, instanceOwner)
				+ File.separator + config.get(Constants.SERVICE_NAME_KEY)
				+ File.separator + Constants.PROJECT + File.separator
				+ projectName;
		
		runtimeMap.put(Constants.JAVA_KEY, config.get(Constants.JAVA_PATH_KEY));
		runtimeMap.put(Constants.PERL_KEY, config.get(Constants.PERL_PATH_KEY));
		runtimeMap.put(Constants.PYTHON_KEY, config.get(Constants.PYTHON_PATH_KEY));
		runtimeMap.put(Constants.SHELL_KEY, config.get(Constants.SHELL_PATH_KEY));
		runtimeMap.put(Constants.R_KEY, config.get(Constants.R_PATH_KEY));
		
		if(config.get(Constants.MAIL_USE_KEY).equals(Constants.YES)){
			isMailUseage = true;
		}else{
			isMailUseage = false;
		}

		GWTMessagePushServiceEvent theGWTMessagePushServiceEvent = new GWTMessagePushServiceEvent();
		
		GWTMessagePushServiceModel gm = new GWTMessagePushServiceModel();
		gm.setInstanceID(instanceID);
		gm.setPipelineID(pipelineID);
		gm.setProjectName(projectName);
		gm.setUserID(instanceOwner);
		gm.setOwnerEmail(ownerEmail);
		gm.setWorkDir(working_path);
		
		theGWTMessagePushServiceEvent.setDomain(DomainFactory.getDomain(instanceID));
		theGWTMessagePushServiceEvent.setGwtMessagePushServiceModel(gm);
		
		Timer pipelienProcessTimer = new Timer();
		pipelineTimerList.put(instanceID, pipelienProcessTimer);
		
		ProcessManagerEngine task = new ProcessManagerEngine(config,
				theGWTMessagePushServiceEvent, xmlDispatchModel,
				pipelienProcessTimer);

		pipelienProcessTimer.schedule((TimerTask) task, 0, Integer.parseInt(config.get(Constants.WEB_SCHEDULER_TIMING_KEY)));
		
		System.out.println(instanceOwner + ": [" + projectName + " pipeline start]-" + CommonUtilsGwt.getDate());
	}

	public class ProcessManagerEngine extends TimerTask{
		
		private GWTMessagePushServiceEvent theGWTMessagePushServiceEvent;
		private InstancePipelineExeModel instancePipelineJobModel;
		private Timer timer;
		
		private boolean isStart = true;
		private boolean isExecute = false;
		private boolean isTerminate = false;
		
		private String joinParameter = "";
		private String instanceID = ""; 
		private String pipelineID = "";
		private String exeID = "";
		private String instanceOwner = "";
		private String instanceName = "";
		private String ownerEmail = "";
		private String workDir = "";
		private String logFile = "";
		private String appFormat = "";
		private XmlDispatchModel xmlDispatchModel;
		
		private Map<Integer, XmlModuleModel> modulesMap;
		private ListMultimap<Integer, Integer> connectorMultiMap;
		private List<Integer> executeIDs;
		private List<Integer> terminateIDs;
		
		private Map<String, String> config;

		public ProcessManagerEngine(Map<String, String> config,
				GWTMessagePushServiceEvent theGWTMessagePushServiceEvent,
				XmlDispatchModel xmlDispatchModel, Timer timer) {

			this.config = config;
			
			this.timer = timer;
			this.theGWTMessagePushServiceEvent = theGWTMessagePushServiceEvent;
			this.instanceID = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getInstanceID();
			this.pipelineID = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getPipelineID();
			this.instanceOwner = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getUserID();
			this.instanceName = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getProjectName();
			this.ownerEmail = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getOwnerEmail();
			this.workDir = theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getWorkDir();
			this.logFile = workDir + File.separator + instanceName + Constants.LOG_FILE_EXTENSION;
			this.xmlDispatchModel = xmlDispatchModel;
			this.modulesMap = XmlDataConverter.convertModulesListToMap(xmlDispatchModel.getModulesBeanList());
			this.connectorMultiMap = XmlDataConverter.convertConnectionListToListMultiMap(xmlDispatchModel.getConnectionsBeanList());
			this.executeIDs = new ArrayList<Integer>();
			this.terminateIDs = new ArrayList<Integer>();
			this.exeID = CommonUtilsGwt.getUUID();
			
			//instance pipeline state update run and exeID
			service.updateInstancePielineState(instanceOwner, instanceID, exeID, instanceName, Constants.RUNNING_STATUS);
			
			//instance pipeline job insert
			instancePipelineJobModel = new InstancePipelineExeModel();
			instancePipelineJobModel.setPipelineID(pipelineID);
			instancePipelineJobModel.setInstanceID(instanceID);
			instancePipelineJobModel.setExeID(exeID);
			instancePipelineJobModel.setStartDate(CommonUtils.getCurruntTime());
			instancePipelineJobModel.setEndDate(null);
			service.newInstancePipelineJob(instancePipelineJobModel);
			
			//instance pipeline update count
			service.updateInstancePipelineExecuteCount(instanceID);
			
			System.out.println(instanceName + " pipeline execute job id: " + exeID);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isStart){
				//start module linked module execute
				isStart = false;
				executeIDs = activityAction(connectorMultiMap.get(Constants.MODULE_START_KEY));
			}else{
				if(isTerminate){
					workflowTerminate();
				}else{
					if(isExecute){
						System.out.println("before job execute ids : " + executeIDs);
						executeIDs = activityAction(executeIDs);
						System.out.println("after job execute ids : " + executeIDs);
					}
				}	
			}
		}

		public List<Integer> activityAction(List<Integer> targetKeys) {
			// TODO Auto-generated method stub
			for(Integer key : targetKeys){
				setWorkFlowModuleStatus(key, "run");
				executor(key);
			}

			this.terminateIDs.addAll(targetKeys);
			
			return getTargetIDs(targetKeys);
		}

		public List<Integer> getTargetIDs(List<Integer> keys){
			
			System.out.println("target ids: " + keys);

			List<Integer> tmp = new ArrayList<Integer>();
			
			for(Integer key : keys){
				//not contains? add || 
				for(Integer target : connectorMultiMap.get(key)){
					if(!tmp.contains(target) && !terminateIDs.contains(target)){
						tmp.add(target);
					}
				}
				
				if(modulesMap.get(key).getModuleName().toLowerCase().equals("end")){
					isTerminate = true;
				}
			}
			
			return tmp;
		}
		
		public void setWorkFlowModuleStatus(Integer key, String status){
			this.xmlDispatchModel = ChangeProcessStatus.setModuleStatusRunning(this.xmlDispatchModel, key);
			String xml = xmlUtils.updateWorkFlowXML(this.xmlDispatchModel);
			service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
			GWTDataPushService(status, this.xmlDispatchModel.getModulesBeanList());
		}
		
		public void workflowTerminate() {
			// TODO Auto-generated method stub
			
			String xml = xmlUtils.updateWorkFlowXML(this.xmlDispatchModel);
			
			service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
			service.updateInstancePielineState(instanceOwner, instanceID, instanceName, 2);
			service.updateInstancePipelineEndDate(instanceID, exeID, CommonUtils.getCurruntTime());
			
			GWTDataPushService("complete", this.xmlDispatchModel.getModulesBeanList());
			
			//pipeline complete send mail to user
			if(isMailUseage){
				CommonUtils.sendEmail(config, 
						String.format(Messages.TASK_COMPLETE_TITLE, instanceOwner, instanceName), 
						String.format(Messages.TASK_COMPLETE_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
						ownerEmail);
				
				System.out.println(instanceName + " task complete sending email..");
			}
			
			System.out.println("[" + 
					theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getProjectName() + 
					" pipeline complete " + CommonUtilsGwt.getDate() +"..]");
			
			timer.cancel();
		}

		public void executor(Integer key) {
			
			System.out.println(instanceName + " pipeline execute key: " + key);
			
			if(key != 0){
				
				isExecute = false;
				
				System.out.println(instanceName + " start module check: [" + isExecute + "]");
				
				XmlModuleModel xm = modulesMap.get(key);
				
				List<XmlParameterModel> parameters = 
						XmlDataConverter.getParametersModelData(key, this.xmlDispatchModel.getParametersBeanList());
				
				for (XmlParameterModel xp : parameters) {
					if(xm.getLanguage().startsWith("java")){
						joinParameter += " " + xp.getSetupValue();
					}else{
						joinParameter += " " + xp.getName() + " " + xp.getSetupValue();
					}
				}
				
				if(xm.getAppFormat().equals(Constants.SCRIPT_TYPE)){
					if(xm.getLanguage().equals("java")){
						if(CommonUtilsGwt.getExtension(xm.getScriptPath()).equals("jar")){
							xm.setLanguage(runtimeMap.get(Constants.JAVA_KEY) + " -jar");
						}else if(CommonUtilsGwt.getExtension(xm.getScriptPath()).equals("class")){
							xm.setLanguage(runtimeMap.get(Constants.JAVA_KEY));
						}
					}else if(xm.getLanguage().equals("perl")){
						xm.setLanguage(runtimeMap.get(Constants.PERL_KEY));
					}else if(xm.getLanguage().equals("python")){
						xm.setLanguage(runtimeMap.get(Constants.PYTHON_KEY));
					}else if(xm.getLanguage().equals("shell")){
						xm.setLanguage(runtimeMap.get(Constants.SHELL_KEY));
					}else if(xm.getLanguage().equals("r")){
						xm.setLanguage(runtimeMap.get(Constants.R_KEY));
					}
					
					appFormat = xm.getLanguage() + " " + xm.getScriptPath();
				}else{
					appFormat = xm.getCmd();
				}
				
				System.out.println(instanceName + " job execute command: [" + appFormat + joinParameter + "]");
				
				LocalBatchProcessService.execute(appFormat + joinParameter, logFile);	
				
				joinParameter = "";
				appFormat = "";
				
				isExecute = true;
				System.out.println(instanceName + " end module check: [" + isExecute + "]");
			}	
			
			this.xmlDispatchModel = ChangeProcessStatus.setModuleStatusComplete(this.xmlDispatchModel, key);
		}
		
		public void GWTDataPushService(String state, List<XmlModuleModel> xmlModulesModelList) {
			// TODO Auto-generated method stub
			theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().setMessage(state);
			theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().setXmlModulesModelList(xmlModulesModelList);
			addEvent(theGWTMessagePushServiceEvent.getDomain(), theGWTMessagePushServiceEvent);
		}	
	}
	
	@Override
	public boolean pipelineForceStop(String instanceID) {
		// TODO Auto-generated method stub
		Timer timer = pipelineTimerList.get(instanceID);
		
		pipelineTimerList.remove(instanceID);
		
		timer.cancel();
		timer.purge();
		
		if(pipelineTimerList.get(instanceID) == null)
			return true;
		else
			return false;
	}
}