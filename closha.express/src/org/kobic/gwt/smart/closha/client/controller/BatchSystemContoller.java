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

import org.kobic.gwt.smart.closha.shared.model.job.scheduler.HostsModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobStatusModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobInformationModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("BatchSystemContoller")
public interface BatchSystemContoller extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BatchSystemContollerAsync instance;
		public static BatchSystemContollerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(BatchSystemContoller.class);
			}
			return instance;
		}
	}
	
	public List<JobInformationModel> getJobStates();
	public List<JobStatusModel> getJobDetailInfo(String jobID);
	public List<HostsModel> getClusterHostInfo();
	public void jobDeletes(List<String> jobIDs);
}
