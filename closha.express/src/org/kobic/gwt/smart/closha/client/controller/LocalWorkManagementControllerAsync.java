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

import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LocalWorkManagementControllerAsync {
	public void pipelineExecute(Map<String, String> config, String instanceOwner, String projectName,
			String instanceID, String pipelineID, String ownerEmail,
			XmlDispatchModel xmlDispatchBean, AsyncCallback<Void> callback);
	
	public void pipelineForceStop(String instanceID, AsyncCallback<Boolean> callback);
}
