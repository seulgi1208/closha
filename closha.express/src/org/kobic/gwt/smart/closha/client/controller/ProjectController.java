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

import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareInstancePipelineModel;
import org.kobic.gwt.smart.closha.shared.model.preference.ShareUserModel;
import org.kobic.gwt.smart.closha.shared.model.project.ProjectModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ProjectController")
public interface ProjectController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ProjectControllerAsync instance;
		public static ProjectControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ProjectController.class);
			}
			return instance;
		}
	}
	
	public InstancePipelineModel makeNewProject(ProjectModel projectModel,
			String email);

	public List<ProjectModel> getUserProjectList(String userID);

	public boolean existUserProjectCheck(String userID, String projectName);

	public void sharingProjects(Map<String, String> config, String userID, String email, List<ShareInstancePipelineModel> sharedProjectList,
			List<ShareUserModel> shareUserList); 
}
