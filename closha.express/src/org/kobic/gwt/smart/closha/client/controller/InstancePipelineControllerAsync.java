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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InstancePipelineControllerAsync {
	public void getInstancePipeline(String instanceID, AsyncCallback<InstancePipelineModel> callback);
	
	public void isInstancePiplineExist(String userID, String instancePipeline, AsyncCallback<Boolean> callback);

	public void updateWorkFlowXML(String instanceOwner, String instanceName,
			String instanceID, XmlDispatchModel xmlDispatchModel, AsyncCallback<Void> callback);

	public void resetWorkFlowXML(String instanceOwner,
			String instanceID, String instanceName, String pipelineID, boolean isRegister, AsyncCallback<XmlDispatchModel> callback);
	
	public void updateInstancePipelineStatus(String instanceOwner, String instanceID,
			String instanceName, int state, AsyncCallback<Integer> callback);
	
	public void updateInstancePipelineRegister(String instanceID, boolean register, AsyncCallback<Integer> callback);
	
	public void getUserInstancePipelines(Map<String, String> config, String instanceOwner, AsyncCallback<List<InstancePipelineModel>> callback);
	
	public void getUserInstancePipelines(Map<String, String> config, String instanceOwner, int status, AsyncCallback<List<InstancePipelineModel>> callback);
	
	public void getInstancePipelineXMLDispatchModel(String instanceID, String projectName, AsyncCallback<XmlDispatchModel> callback);

	public void getInstancePipelineJobHistoryData(String instanceID, AsyncCallback<List<InstancePipelineExeModel>> callback);
	
	public void getPrameterParserData(String key, String module, String parameter, AsyncCallback<List<XmlParameterModel>> callback);
	
	public void getRegisterPiplineXML(String pipelineID, AsyncCallback<String> callback);
	
	public void getInstancePipelineXML(String instanceID, AsyncCallback<String> callback);
	
	public void removeInstancePipelineExecuteHistory(String instanceID, List<String> executeIDs, AsyncCallback<Void> callback);
}
