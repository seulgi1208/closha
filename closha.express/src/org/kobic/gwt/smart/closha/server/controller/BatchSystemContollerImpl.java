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

import java.util.List;

import org.kobic.gwt.smart.closha.client.controller.BatchSystemContoller;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueManagerService;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueManagerServiceImpl;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateService;
import org.kobic.gwt.smart.closha.shared.batch.sge.QueueStateServiceImpl;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.HostsModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobStatusModel;
import org.kobic.gwt.smart.closha.shared.model.job.scheduler.JobInformationModel;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class BatchSystemContollerImpl extends RemoteServiceServlet implements BatchSystemContoller {

	private static final long serialVersionUID = 1L;

	@Override
	public List<JobInformationModel> getJobStates() {
		// TODO Auto-generated method stub
		QueueStateService service = new QueueStateServiceImpl();
		return service.getQueueState();
	}

	@Override
	public List<JobStatusModel> getJobDetailInfo(String jobID) {
		// TODO Auto-generated method stub
		QueueStateService service = new QueueStateServiceImpl();
		return service.getJobDetailInfo(jobID);
	}

	@Override
	public List<HostsModel> getClusterHostInfo() {
		// TODO Auto-generated method stub
		QueueStateService service = new QueueStateServiceImpl();
		return service.getClusterHostInfo();
	}

	@Override
	public void jobDeletes(List<String> jobIDs) {
		// TODO Auto-generated method stub
		QueueManagerService service = new QueueManagerServiceImpl();
		service.queueDeletes(jobIDs);
	}
}
