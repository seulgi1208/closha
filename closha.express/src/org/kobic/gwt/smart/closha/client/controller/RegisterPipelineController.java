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
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.model.module.register.RegisterModuleModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.PipelineModel;
import org.kobic.gwt.smart.closha.shared.model.pipeline.register.RegisterPipelineNetworkGraphModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RegisterPipelineController")
public interface RegisterPipelineController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static RegisterPipelineControllerAsync instance;
		public static RegisterPipelineControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(RegisterPipelineController.class);
			}
			return instance;
		}
	}
	
	public List<PipelineModel> getRegisterPipelinesList();
	public List<PipelineModel> getRegisterPipelinesList(UserDto userDto);
	public RegisterPipelineNetworkGraphModel getLinkedGraphData(int type);
	public List<RegisterModuleModel> getRegisterPipelineModuleList(boolean exceptionModule);
	public List<RegisterModuleModel> getRegisterPipelineModuleList(boolean exceptionModule, UserDto userDto);
	public String insertRegisterPipelineModule(Map<String, String> config, UserDto userDto, RegisterModuleModel registerPipelineModulesModel);
	public void insertRegisterPipelineModule(PipelineModel registerPipelineModel, XmlDispatchModel xmlDispatchModel);
	public void deleteRegisterPipeline(List<String> registerIDs);
	public void deleteRegisterPipelineModule(List<String> moduleIDs);
	public void updateUserModuleCheck(List<String> moduleIDs, boolean isCheck);
}
