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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.kobic.gwt.smart.closha.client.controller.WorkManagementController;
import org.kobic.gwt.smart.closha.client.event.gwt.GWTMessagePushServiceEvent;
import org.kobic.gwt.smart.closha.server.handler.InstancePipelineServiceHandler;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueExecuteService;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueExecuteServiceImpl;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateService;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateServiceImpl;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;
import org.kobic.gwt.smart.closha.shared.file.service.impl.UnixFileSystem;
import org.kobic.gwt.smart.closha.shared.model.design.XmlConnectLinkedModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.event.service.GWTMessagePushServiceModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.scheduler.BusinessProcessManagementService;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;
import org.kobic.gwt.smart.closha.shared.utils.design.XmlDataConverter;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import com.google.gwt.thirdparty.guava.common.collect.ArrayListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class WorkManagementControllerImpl extends RemoteEventServiceServlet implements WorkManagementController {
	    
	private static final long serialVersionUID = 1L;
	
	private static InstancePipelineService service = InstancePipelineServiceHandler.getService();
	private static PipelineXmlDataUtil xmlUtils = new PipelineXmlDataUtil();
	
	private static Map<String, Timer> pipelineTimerList = new HashMap<String, Timer>();
	
	public String getRootPath(Map<String, String> config, String userId){
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
				+ userId + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY);
		
		return HadoopFileSystem.getInstance(config).getRootDirectoryPath(path);
	}
	

	@Override
	public void pipelineExecute(Map<String, String> config, String instanceOwner, String projectName,
			String instanceID, String pipelineID, String ownerEmail,
			XmlDispatchModel xmlDispatchBean) {
		// TODO Auto-generated method stub
		
		String working_path = config.get(Constants.LOG_DIR_KEY)
				+ File.separator + Constants.LOG + File.separator
				+ instanceOwner + File.separator + projectName + "_log";
		
		System.out.println(projectName + ": working dir [" + working_path + "]\tscheduler timer [" + config.get(Constants.WEB_SCHEDULER_TIMING_KEY) + "]");
		
		if(!UnixFileSystem.getInstance().exist(working_path)){
			UnixFileSystem.getInstance().makeLogDirectory(working_path);
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
		
		File log = new File(working_path + File.separator + projectName + ".log");
		
		BusinessProcessManagementService task = new BusinessProcessManagerEngine(
				config,
				theGWTMessagePushServiceEvent,
				XmlDataConverter.convertModulesList(xmlDispatchBean.getModulesBeanList()),
				xmlDispatchBean.getConnectionsBeanList(),
				xmlDispatchBean.getParametersBeanList(), 
				pipelienProcessTimer, log);
		
		pipelienProcessTimer.schedule((TimerTask) task, 0, Integer.parseInt(config.get(Constants.ENGINE_SCHEDULER_TIMING_KEY)));
		
		System.out.println("User: [" + instanceOwner + "]\tproject name: [" + projectName + "]\texecute date: " + CommonUtils.getCurruntTime());
		
		try {
			FileUtils.writeStringToFile(log, "User: [" + instanceOwner + "]\tProject Name: [" + projectName + "]\tJob Start Time on CLOSHA: " + CommonUtils.getCurruntTime(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class BusinessProcessManagerEngine extends TimerTask implements BusinessProcessManagementService{

		private GWTMessagePushServiceEvent theGWTMessagePushServiceEvent;
		
		private QueueStateService qstateService;
		private QueueExecuteService batchExecute;
		
		private Map<String, String> config;
		
		private Map<String, XmlModuleModel> moduleList;
		private List<XmlConnectLinkedModel> linkedList;
		private List<XmlParameterModel> parameterList;
		
		private Multimap<String, String> linkedMap = ArrayListMultimap.create();
		private Multimap<String, XmlParameterModel> parameterMap = ArrayListMultimap.create();
		
		private Timer timer;
		
		private List<String> jobIDs;
		private List<String> targetList;
		private List<String> sourceList;
		private List<String> terminateList;
		
		private boolean start = true;
		private boolean occur_err = false;
		
		private String joinParameter = "";
		private String instanceID = ""; 
		private String pipelineID = "";
		private String exeID = "";
		private String instanceOwner = "";
		private String instanceName = "";
		private String ownerEmail = "";
		private String workDir = "";
		
		private InstancePipelineExeModel instancePipelineJobModel;
				
		private String xml = "";
		
		private File log;
		
		public BusinessProcessManagerEngine(
				Map<String, String> config,
				GWTMessagePushServiceEvent theGWTMessagePushServiceEvent,
				Map<String, XmlModuleModel> modules,
				List<XmlConnectLinkedModel> linked,
				List<XmlParameterModel> parameter, Timer timer, File log){

			this.config = config;
			
			this.moduleList = modules;
			this.linkedList = linked;
			this.parameterList = parameter;
			this.timer = timer;
			
			terminateList = new ArrayList<String>();
			sourceList = new ArrayList<String>();
			jobIDs = new ArrayList<String>();
			
			qstateService = new QueueStateServiceImpl();
			batchExecute = new QueueExecuteServiceImpl();
			
			this.theGWTMessagePushServiceEvent = theGWTMessagePushServiceEvent;
			
			this.instanceID = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getInstanceID();
			this.pipelineID = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getPipelineID();
			this.instanceOwner = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getUserID();
			this.instanceName = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getProjectName();
			this.ownerEmail = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getOwnerEmail();
			this.workDir = theGWTMessagePushServiceEvent
					.getGwtMessagePushServiceModel().getWorkDir();
			
			this.exeID = CommonUtilsGwt.getUUID();
			
			this.log = log;
			
			for(XmlConnectLinkedModel link : linkedList){
				linkedMap.put(link.getTarget(), link.getSource());
			}
			
			for(XmlParameterModel param : parameterList){
				parameterMap.put(param.getKey(), param);
			}
			
			service.updateInstancePielineState(instanceOwner, instanceID, exeID, instanceName, Constants.RUNNING_STATUS);
			
			//instance pipeline job insert
			instancePipelineJobModel = new InstancePipelineExeModel();
			instancePipelineJobModel.setPipelineID(pipelineID);
			instancePipelineJobModel.setInstanceID(instanceID);
			instancePipelineJobModel.setExeID(exeID);
			instancePipelineJobModel.setStartDate(CommonUtils.getCurruntTime());
			instancePipelineJobModel.setEndDate(null);
			
			service.newInstancePipelineJob(instancePipelineJobModel);
			
			//instance pipeline update execute count
			service.updateInstancePipelineExecuteCount(instanceID);
			
			System.out.println(instanceName + ": pipeline execute job id [" + exeID + "]");
			
			try {
				FileUtils.writeStringToFile(log, instanceName + ": Pipeline Excute Job ID: [" + exeID + "]", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(start){
				activityAction(init());
				
				try {
					FileUtils.writeStringToFile(log, "START Module Start Time: " + CommonUtils.getCurruntTime(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				if(qstateService.qstateCheckUp(jobIDs)){
					jobIDs.clear();
					
					List<String> targetIDs = executeStep(sourceList);
					
					terminateList.addAll(sourceList);
					
					System.out.println(instanceName
							+ ": terminate module list ["
							+ terminateList.toString() + "]");
					
					clearSourceID();	
					
					if(targetIDs.size() == 0){
						terminate();
					}else{
						activityAction(targetIDs);
					}
				}else{
					System.out.println("previous step " + targetList.toString()
							+ "(" + jobIDs.toString() + ")"
							+ " analysis module of the process is running!!-"
							+ CommonUtilsGwt.getDate());
				}
			}
		}
		
		@Override
		public XmlDispatchModel getCompleteModuleList(XmlDispatchModel xm, List<String> targetIDs){
			
			for(String targetId : targetIDs){
				
				System.out.println(instanceName + ": [" + targetId + "] complete!-" + CommonUtilsGwt.getDate());
				
				if(CommonUtilsGwt.changeType(targetId) != 1){
					
					for(XmlModuleModel mm : xm.getModulesBeanList()){
						if(mm.getKey().equals(targetId)){
							mm.setStatus("Complete");
						}
					}
				}
			}
			return xm;
		}
		
		@Override
		public XmlDispatchModel getRunningModuleList(XmlDispatchModel xm, List<String> targetIDs){
			
			for(String key : targetIDs){
				for(XmlModuleModel mm : xm.getModulesBeanList()){
					if(key.equals(mm.getKey())){
						mm.setStatus("Running");
					}
				}
			}
			return xm;
		}
		
		@Override
		public XmlDispatchModel getErrModuleList(XmlDispatchModel xm,
				String targetIDs) {
			// TODO Auto-generated method stub
			for(XmlModuleModel mm : xm.getModulesBeanList()){
				if(targetIDs.equals(mm.getKey())){
					mm.setStatus("Err");
				}
			}
			return xm;
		}	
		
		@Override
		public XmlDispatchModel terminateModuleList(XmlDispatchModel xm){
			
			for(String key : terminateList){
				for(XmlModuleModel mm : xm.getModulesBeanList()){
					if(key.equals(mm.getKey())){
						mm.setStatus("Complete");
					}
				}
			}
			return xm;
		}
		
		@Override
		public XmlDispatchModel getXMLDispatchModel(){
			xml = service.getInstancePipelineFromProject(instanceID).getInstanceXML();
			XmlDispatchModel xmlDispatchModel = xmlUtils.drawParserXML(xml);
			return xmlDispatchModel;
		}

		private void downloadFromCacheToHDFS(XmlDispatchModel xmlDispatchModel, List<String> targetIDs){
			
			System.out.println("[" + xmlDispatchModel.getName() + "] : result output put HDFS start..");
			
			boolean isDownloadCache;
			
			List<String> cachePath = new ArrayList<String>();
			
			for(String targetID : targetIDs){
				for(String key : linkedMap.get(targetID)){
					for(XmlParameterModel param : parameterMap.get(key)){
						if(param.getParameterType().equals("OUTPUT") && 
								(param.getType().equals("FILE") || param.getType().equals("FOLDER")) ){
							cachePath.add(param.getSetupValue());	
						}	
					}
					
				}
			}
			
			for(String cache_path : cachePath){
				System.out.println("cache ==> HDFS: [" + cache_path + "]");
			}
			
			if(cachePath.size() != 0){
				CacheLiteClient client = new CacheLiteClient();
				isDownloadCache = client.downloading_cache(cachePath);
				
				System.out.println(cachePath + ": put HDFS res ==> [" + String.valueOf(isDownloadCache) + "]");
				
				if (!isDownloadCache) {
					err(xmlDispatchModel);
				}
			}
		}
		
		@Override
		public void activityAction(List<String> targetIDs) {
			// TODO Auto-generated method stub
			
			XmlDispatchModel xmlDispatchModel = getXMLDispatchModel();

			downloadFromCacheToHDFS(xmlDispatchModel, targetIDs);
			
			for(String targetID : targetIDs){
				
				System.out.println(instanceName + ": next execute target module keys [" + targetID + "]");
				
				addSourceID(targetID);
				jobStart(targetID, xmlDispatchModel);
			}
			
			if(!occur_err){
				System.out.println(instanceName + ": teminate module list ["
						+ terminateList.toString() + "]\tnext target module list ["
						+ targetIDs.toString() + "]");
				
				xmlDispatchModel = getCompleteModuleList(xmlDispatchModel, terminateList);
				xmlDispatchModel = getRunningModuleList(xmlDispatchModel, targetIDs);
				
				String xml = xmlUtils.updateWorkFlowXML(xmlDispatchModel);
				
				service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				gwtStateDataEventService("run", xmlDispatchModel.getModulesBeanList());
			}
		}

		public void err(XmlDispatchModel xmlDispatchModel) {
			// TODO Auto-generated method stub
			
			occur_err = true;
		
			String xml = xmlUtils.updateWorkFlowXML(xmlDispatchModel);
			
			service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
			service.updateInstancePielineState(instanceOwner, instanceID, instanceName, 3);
			service.updateInstancePipelineEndDate(instanceID, exeID, CommonUtils.getCurruntTime());
			
			//pipeline error send mail to user
			CommonUtils.sendEmail(config, 
					String.format(Messages.TASK_ERROR_TITLE, instanceOwner, instanceName), 
					String.format(Messages.TASK_ERROR_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
					ownerEmail);
			
			System.out.println("[" + 
					theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getProjectName() + 
					" pipeline err " + CommonUtilsGwt.getDate() +"..]");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			gwtStateDataEventService("error", xmlDispatchModel.getModulesBeanList());
			
			timer.cancel();
			timer.purge();
		}
		
		@Override
		public void terminate() {
			// TODO Auto-generated method stub
						
			XmlDispatchModel xmlDispatchModel = getXMLDispatchModel();
			xmlDispatchModel = terminateModuleList(xmlDispatchModel);
			String xml = xmlUtils.updateWorkFlowXML(xmlDispatchModel);
			
			service.updateInstancePipelineXML(instanceOwner, instanceID, instanceName, xml);
			service.updateInstancePielineState(instanceOwner, instanceID, instanceName, 2);
			service.updateInstancePipelineEndDate(instanceID, exeID, CommonUtils.getCurruntTime());
			
			gwtStateDataEventService("complete", xmlDispatchModel.getModulesBeanList());
			
			//pipeline complete send mail to user
			CommonUtils.sendEmail(config, 
					String.format(Messages.TASK_COMPLETE_TITLE, instanceOwner, instanceName), 
					String.format(Messages.TASK_COMPLETE_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
					ownerEmail);
			
			System.out.println("[" + 
					theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().getProjectName() + 
					" pipeline complete " + CommonUtilsGwt.getDate() +"..]");
			
			timer.cancel();
			timer.purge();
		}

		@Override
		public List<String> init() {
			// TODO Auto-generated method stub
			start = false;
			return executeStep(XmlDataConverter.getConnectionModelData("1", linkedList));
		}

		@Override
		public List<String> executeStep(Collection<XmlConnectLinkedModel> connections) {
			// TODO Auto-generated method stub
			
			targetList = new ArrayList<String>();
			
			for(XmlConnectLinkedModel connection : connections){
				if(!targetList.contains(connection.getTarget())){
					targetList.add(connection.getTarget());
				}
			}
			
			return targetList;
		}

		@Override
		public List<String> executeStep(List<String> sourceIDs) {
			// TODO Auto-generated method stub
			List<String> targetIDs = new ArrayList<String>();
			
			for(String sourceID : sourceIDs){
				for(String target : executeStep(XmlDataConverter.getConnectionModelData(sourceID, linkedList))){
					if(!targetIDs.contains(target)){
						targetIDs.add(target);
					}
				}
			}
			return targetIDs;
		}

		@Override
		public void addSourceID(String sourceID) {
			// TODO Auto-generated method stub
			if(!sourceList.contains(sourceID)){
				sourceList.add(sourceID);
			}
		}

		@Override
		public void clearSourceID() {
			// TODO Auto-generated method stub
			sourceList.clear();
		}
		
		@Override
		public List<String> getSoruceID() {
			// TODO Auto-generated method stub
			return sourceList;
		}

		@Override
		public boolean checkUp(String key) {
			// TODO Auto-generated method stub
			XmlModuleModel xm = moduleList.get(key);
			if(xm.getModuleDesc().toLowerCase().equals("end module")){
				return true;
			}else{
				return false;
			}
		}
		
		@Override
		public boolean checkUp(List<String> keys) {
			// TODO Auto-generated method stub
			
			boolean result = false;
			
			for(String key : keys){
				XmlModuleModel xm = moduleList.get(key);
				if(xm.getModuleDesc().toLowerCase().equals("end module")){
					result = true;
				}
			}
			return result;
		}

		@Override
		public List<String> jobStart(String stepKey, XmlDispatchModel xmlDispatchModel) {
			// TODO Auto-generated method stub
			
			if(checkUp(stepKey)){
				
				System.out.println(instanceName + ": [END] module skip!!-" + CommonUtilsGwt.getDate());
				
				try {
					FileUtils.writeStringToFile(log, "END Module Start Time: " + CommonUtils.getCurruntTime(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return jobIDs;
				
			}else{
				XmlModuleModel xm = moduleList.get(stepKey);
				
				List<XmlParameterModel> parameters = XmlDataConverter.getParametersModelData(stepKey, parameterList);
				
				for (XmlParameterModel xp : parameters) {
					if(xp.getSetupValue().length() != 0){
						joinParameter += " " + xp.getSetupValue().trim();
					}else{
						System.out.println("Required Parameter Error Occured [Critical]");
					}
				}
				
				try {

					if(xm.getAppFormat().equals(Constants.SCRIPT_TYPE)){
						
						/**
						 * cache loading..
						 */
						if(xm.getType().toUpperCase().equals("LINUX")){
							
							List<String> file = new ArrayList<String>();
							
							for (XmlParameterModel xp : parameters) {
								if(xp.getParameterType().toUpperCase().equals("INPUT")){
									if(xp.getType().toUpperCase().equals("FILE") || xp.getType().toUpperCase().equals("FOLDER") || xp.getType().toUpperCase().equals("REFERENCE")){
										System.out.println(xp.getParameterType() + ":" + xp.getType() + ":" + xp.getName() + ":" + xp.getSetupValue());
										file.add(xp.getSetupValue());
									}
								}
							}
							
							System.out.println("input file size: [" + file.size() +"]\n" + file.toString());

							boolean isLoadingCache = false;
							
							CacheLiteClient client = new CacheLiteClient();
							isLoadingCache = client.loading_cache(file);
							
							System.out.println("data load to cache res ==> [" + String.valueOf(isLoadingCache) + "]");

							if (!isLoadingCache) {
								err(getErrModuleList(xmlDispatchModel, stepKey));
							}else{
								for(String f : file){
									if(!client.isCacheExist(f)){
										System.out.println("[" + f + "] The cache data file does not exist.");
										err(getErrModuleList(xmlDispatchModel, stepKey));
									}
								}
							}
						}
						
						//병렬이냐..
						if(xm.isParallel()){
							
							System.out.println(xmlDispatchModel.getName() + " is parallel module!");
							
							String path = null;
							String ext = null;
							String extList = null;
							
							for(int i = 0; i < parameters.size(); i++){
								XmlParameterModel xp = parameters.get(i);
								if(xp.getParameterType().toUpperCase().equals("INPUT")){
									if(xp.getType().toUpperCase().equals("FOLDER")){
										path = xp.getSetupValue();
										extList = xp.getExtensions().replaceAll("\\p{Z}", "");
									}
								}
							}
							
							List<File> files = new ArrayList<File>();
							
							System.out.println("input parallel path : [" + path + "]");
							
							for(File file : new File(path).listFiles()){
								
								if(!file.exists() || file.isHidden() || file.isDirectory()){
									continue;
								}else{
									
									if(extList.equals("*")){
										files.add(file);
									}else{
										//인풋에 대한 입력 확장자가 있을 경우 해당 확장자만을 리스트에 저장
										ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
										if(Arrays.asList(extList.split("[,]")).contains(ext)){
											files.add(file);
										}
									}
								}
							}
							
							Collections.sort(files);
							
							System.out.println("-parallel input file list\n---------------------------------------");
							
							for(int i = 0; i < files.size(); i++){
								System.out.println(files.get(i).getAbsolutePath());
							}
							
							boolean is_alignment = xm.isAlignment();
							
							for(int i =0; i < files.size(); i++){
								
								LinkedList<String> list = new LinkedList<String>();
								
								String name = instanceOwner + "^" + xm.getModuleName();
								
								list.add("qsub");
								list.add("-e");
								list.add(workDir);
								list.add("-o");
								list.add(workDir);
								list.add("-V");
								list.add("-S");
								
								if(xm.getLanguage().equals(Constants.JAVA_KEY)){
									list.add(config.get(Constants.JAVA_PATH_KEY));
								}else if(xm.getLanguage().equals(Constants.PERL_KEY)){
									list.add(config.get(Constants.PERL_PATH_KEY));
								}else if(xm.getLanguage().equals(Constants.PYTHON_KEY)){
									list.add(config.get(Constants.PYTHON_PATH_KEY));
								}else if(xm.getLanguage().equals(Constants.SHELL_KEY)){
									list.add(config.get(Constants.SHELL_PATH_KEY));
								}else if(xm.getLanguage().equals(Constants.R_KEY)){
									list.add(config.get(Constants.R_PATH_KEY));
								}
								
								if(xm.isMulti()){
									list.add("-pe");
									list.add("pe_slots");
									list.add("6");
								}
								
								list.add("-N");
								list.add(name);
								
								System.out.println("module type: " + xm.getType());
								
								list.add(xm.getScriptPath().replace("/BiO/bioexpress/closha/script", "/BiO/bioexpress/closha/script_dev"));
								
								System.out.println("==============================" + is_alignment);
								
								for (XmlParameterModel xp : parameters) {
									if(xp.getParameterType().toUpperCase().equals("INPUT") && xp.getType().toUpperCase().equals("FOLDER")){
										
										if(is_alignment){
											File file_1 = files.get(i);
											File file_2 = files.get(i+1);
											list.add(file_1.getAbsolutePath());
											list.add(file_2.getAbsolutePath());
											i = i + 1;
										}else{
											File file = files.get(i);
											list.add(file.getAbsolutePath());
										}
										
									}else{
										list.add(xp.getSetupValue());
									}
								}
								
								System.out.println("[" + xmlDispatchModel.getName() + "] job execute command ==> " + list.toString());
								
								String[] cmds = list.toArray(new String[list.size()]);
								
								try {
									FileUtils.writeStringToFile(log, xm.getModuleAuthor() + " Job Start Command: " + list.toString() + "\tJob Start Time: " + CommonUtils.getCurruntTime(), true);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								try{
									jobIDs.addAll(batchExecute.execute(cmds));
								}catch(IOException e){
									System.out.println("error command: " + Arrays.asList(cmds).toString());
								}
								
								System.out.println("[" + xmlDispatchModel.getName() + "] job ids ==> " + jobIDs.toString());
							}
							
						}else{

							LinkedList<String> list = new LinkedList<String>();
							
							String name = instanceOwner + "^" + xm.getModuleName();
							
							list.add("qsub");
							list.add("-e");
							list.add(workDir);
							list.add("-o");
							list.add(workDir);
							list.add("-V");
							list.add("-S");
							
							if(xm.getLanguage().equals(Constants.JAVA_KEY)){
								list.add(config.get(Constants.JAVA_PATH_KEY));
							}else if(xm.getLanguage().equals(Constants.PERL_KEY)){
								list.add(config.get(Constants.PERL_PATH_KEY));
							}else if(xm.getLanguage().equals(Constants.PYTHON_KEY)){
								list.add(config.get(Constants.PYTHON_PATH_KEY));
							}else if(xm.getLanguage().equals(Constants.SHELL_KEY)){
								list.add(config.get(Constants.SHELL_PATH_KEY));
							}else if(xm.getLanguage().equals(Constants.R_KEY)){
								list.add(config.get(Constants.R_PATH_KEY));
							}
							
							if(xm.isMulti()){
								list.add("-pe");
								list.add("pe_slots");
								list.add("6");
							}
							
							list.add("-N");
							list.add(name);
							
							System.out.println(xm.getType());
							
							//임시로 스크립트 경로를 변경했다 추후 원복.
							System.out.println(instanceName
									+ " job execute command: " + list.toString() + xm.getScriptPath().replace("/BiO/hadoop/closha/script", "/BiO/hadoop/closha/script_dev")
									+ joinParameter + "]");
							
							System.out.println(xm.getScriptPath());
							System.out.println(xm.isParallel());
							
							list.add(xm.getScriptPath().replace("/BiO/hadoop/closha/script", "/BiO/hadoop/closha/script_dev"));
							
							//parameter 정보에 이름을 제외하고 생성한다.
							for (XmlParameterModel xp : parameters) {
								list.add(xp.getSetupValue());
							}
							
							System.out.println(list.toString());
							
							String[] cmds = list.toArray(new String[list.size()]);
							
							try {
								FileUtils.writeStringToFile(log, xm.getModuleAuthor() + " Job Start Command: " + list.toString() + "\tJob Start Time: " + CommonUtils.getCurruntTime(), true);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							try{
								jobIDs.addAll(batchExecute.execute(cmds));
							}catch(IOException e){
								System.out.println("error command: [" + cmds.toString() + "]");
							}
						}
					}else{
						
						String temp = "qsub -b y -l hadoop -N %s \"%s\"";
						
						System.out.println(instanceName
								+ " job execute command: ["
								+ String.format(temp, xm.getModuleName(),
										xm.getCmd() + " " + joinParameter) + "]");
				
						String[] cmds = null;
						
						if(xm.isMulti()){
							cmds = new String[]{"qsub", "-e", workDir, "-o", workDir, "-V", "-pe", "pe_slots 6", "-b", "y", "-l", "hadoop", "-N", xm.getModuleName(), "\"", xm.getCmd(), joinParameter, "\""};
						}else{
							cmds = new String[]{"qsub", "-e", workDir, "-o", workDir, "-V", "-b", "y", "-l", "hadoop", "-N", xm.getModuleName(), "\"", xm.getCmd(), joinParameter, "\""};
						}
						
						try {
							FileUtils.writeStringToFile(log, xm.getModuleAuthor() + " Job Start Command: " + cmds.toString() + "\tJob Start Time: " + CommonUtils.getCurruntTime(), true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						jobIDs.addAll(batchExecute.execute(cmds));
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
					CommonUtils.sendEmail(config, 
							String.format(Messages.TASK_ERROR_TITLE, instanceOwner, instanceName), 
							String.format(Messages.TASK_ERROR_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
							ownerEmail);
					
					e.printStackTrace();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
					CommonUtils.sendEmail(config, 
							String.format(Messages.TASK_ERROR_TITLE, instanceOwner, instanceName), 
							String.format(Messages.TASK_ERROR_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
							ownerEmail);
					
					e.printStackTrace();
				}
				
				joinParameter = "";
				
				try {
					FileUtils.writeStringToFile(log, xm.getModuleAuthor() + " Job ID List: " + jobIDs.toString(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return jobIDs;
			}
		}

		@Override
		public void gwtStateDataEventService(String state, List<XmlModuleModel> xmlModulesModelList) {
			// TODO Auto-generated method stub
			
			theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().setMessage(state);
			theGWTMessagePushServiceEvent.getGwtMessagePushServiceModel().setXmlModulesModelList(xmlModulesModelList);
			addEvent(theGWTMessagePushServiceEvent.getDomain(), theGWTMessagePushServiceEvent);
			
		}
	}

	@Override
	public boolean pipelineForceStop(String instanceOwner, String instanceID, Map<String, String> config, String instanceName, String ownerEmail) {
		// TODO Auto-generated method stub
		Timer timer = pipelineTimerList.get(instanceID);
		
		pipelineTimerList.remove(instanceID);
		
		timer.cancel();
		timer.purge();
		
		CommonUtils.sendEmail(config, 
				String.format(Messages.TASK_TERMINATE_TITLE, instanceOwner, instanceName), 
				String.format(Messages.TASK_TERMINATE_MESSAGE, instanceName, instanceName, CommonUtils.getCurruntTime()),
				ownerEmail);
		
		if(pipelineTimerList.get(instanceID) == null)
			return true;
		else
			return false;
	}
}