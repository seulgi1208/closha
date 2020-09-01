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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegisterPipelineControllerAsync {
	public void getRegisterPipelinesList(AsyncCallback<List<PipelineModel>> callback);
	public void getRegisterPipelinesList(UserDto userDto, AsyncCallback<List<PipelineModel>> callback);
	public void getLinkedGraphData(int type, AsyncCallback<RegisterPipelineNetworkGraphModel> callback);
	public void getRegisterPipelineModuleList(boolean exceptionModule, AsyncCallback<List<RegisterModuleModel>> callback);
	public void getRegisterPipelineModuleList(boolean exceptionModule, UserDto userDto, AsyncCallback<List<RegisterModuleModel>> callback);
	public void insertRegisterPipelineModule(Map<String, String> config, UserDto userDto, RegisterModuleModel registerPipelineModulesModel, AsyncCallback<String> callback);
	public void insertRegisterPipelineModule(PipelineModel registerPipelineModel, XmlDispatchModel xmlDispatchModel, AsyncCallback<Void> callback);
	public void deleteRegisterPipeline(List<String> registerIDs, AsyncCallback<Void> callback);
	public void deleteRegisterPipelineModule(List<String> moduleIDs, AsyncCallback<Void> callback);
	public void updateUserModuleCheck(List<String> moduleIDs, boolean isCheck, AsyncCallback<Void> callback);
}
