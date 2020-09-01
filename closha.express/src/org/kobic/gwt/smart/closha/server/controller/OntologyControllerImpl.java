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

import org.kobic.gwt.smart.closha.client.controller.OntologyController;
import org.kobic.gwt.smart.closha.server.service.OntologyService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.ontology.OntologyModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OntologyControllerImpl extends RemoteServiceServlet implements OntologyController {

	private static final long serialVersionUID = 1L;

	@Override
	public List<OntologyModel> getOntologyList(int type) {
		// TODO Auto-generated method stub
		
		OntologyService service = (OntologyService) SpringContextHelper.getBean(getServletContext(), "ontologyService");
		
		boolean isAdmin = (boolean) getThreadLocalRequest().getSession().getAttribute(Constants.IS_ADMIN_SESSION_KEY);
		
		return service.getOntology(type, isAdmin);
	}

	@Override
	public void deleteOntology(List<String> list) {
		// TODO Auto-generated method stub
		OntologyService service = (OntologyService) SpringContextHelper.getBean(getServletContext(), "ontologyService");
		service.deleteOntology(list);
	}

	@Override
	public void addOntology(String ontology, int type) {
		// TODO Auto-generated method stub
		OntologyModel om = new OntologyModel();
		om.setOntologyID(UUID.uuid());
		om.setOntologyName(ontology);
		om.setType(String.valueOf(type));
		
		OntologyService service = (OntologyService) SpringContextHelper.getBean(getServletContext(), "ontologyService");
		service.addOntology(om);
	}
}
