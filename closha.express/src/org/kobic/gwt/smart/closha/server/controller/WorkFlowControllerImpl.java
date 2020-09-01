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

import org.kobic.gwt.smart.closha.client.controller.WorkFlowController;
import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;
import org.kobic.gwt.smart.closha.shared.utils.design.PipelineXmlDataUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class WorkFlowControllerImpl extends RemoteServiceServlet implements WorkFlowController {

	private static final long serialVersionUID = 1L;
	private PipelineXmlDataUtil xmlUtils = new PipelineXmlDataUtil();
	
	@Override
	public void createWorkFlowXML(String name, String userID, String version, String description) {
		// TODO Auto-generated method stub
		xmlUtils.createWorkFlowXMLFromData(name, userID, version, description, null, null);
	}

	@Override
	public XmlDispatchModel drawParserXML(String instancePipelineXML) {
		// TODO Auto-generated method stub
		XmlDispatchModel xmlDispatchBean = new XmlDispatchModel();
		xmlDispatchBean = xmlUtils.drawParserXML(instancePipelineXML);
		
		return xmlDispatchBean;
	}
}