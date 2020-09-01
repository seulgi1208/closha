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
package org.kobic.gwt.smart.closha.server.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.kobic.gwt.smart.closha.client.common.controller.CommonController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CommonControllerImpl extends RemoteServiceServlet implements CommonController {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean userBrowserChecker(String agent) {
		// TODO Auto-generated method stub
		String[] str = agent.split(";");
		if(str.length > 0){
			for(String line : str){
				if(line.trim().startsWith("msie")){
					return true;
				}
			}			
		}
		return false;
	}

	@Override
	public String getHTMLContents(String htmlType) {
		// TODO Auto-generated method stub
		
		File f = new File(getServletContext().getRealPath("/") + File.separator 
				+ htmlType);
		
		if (f.exists()) {
			try {
				return FileUtils.readFileToString(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Constants.NULL_MESSAGE;
	}

	@Override
	public Map<String, String> getConfiguration() {
		// TODO Auto-generated method stub
		return CommonUtils.getConfiguration();
	}
}
