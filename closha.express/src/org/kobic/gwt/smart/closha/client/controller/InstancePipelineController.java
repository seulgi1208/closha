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
import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineExeModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("InstancePipelineController")
public interface InstancePipelineController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static InstancePipelineControllerAsync instance;

		public static InstancePipelineControllerAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(InstancePipelineController.class);
			}
			return instance;
		}
	}

	public InstancePipelineModel getInstancePipeline(String instanceID);
	
	public boolean isInstancePiplineExist(String userID, String instancePipeline);

	public void updateWorkFlowXML(String instanceOwner, String instanceName,
			String instanceID, XmlDispatchModel xmlDispatchModel);

	public XmlDispatchModel resetWorkFlowXML(String instanceOwner,
			String instanceID, String instanceName, String pipelineID, boolean isRegister);
	
	public int updateInstancePipelineStatus(String instanceOwner, String instanceID,
			String instanceName, int state);
	
	public int updateInstancePipelineRegister(String instanceID, boolean register);
	
	public List<InstancePipelineModel> getUserInstancePipelines(Map<String, String> config, String instanceOwner);
	
	public List<InstancePipelineModel> getUserInstancePipelines(Map<String, String> config, String instanceOwner, int status);
	
	public XmlDispatchModel getInstancePipelineXMLDispatchModel(String instanceID, String projectName);

	public List<InstancePipelineExeModel> getInstancePipelineJobHistoryData(String instanceID);
	
	public List<XmlParameterModel> getPrameterParserData(String key, String module, String parameter);
	
	public String getRegisterPiplineXML(String pipelineID);
	
	public String getInstancePipelineXML(String instanceID);
	
	public void removeInstancePipelineExecuteHistory(String instanceID, List<String> executeIDs);
}
