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

import org.kobic.gwt.smart.closha.shared.model.parameter.ParameterModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CacheLiteClientController")
public interface CacheLiteClientController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static CacheLiteClientControllerAsync instance;
		public static CacheLiteClientControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(CacheLiteClientController.class);
			}
			return instance;
		}
	}
	
	public Map<String, ParameterModel> isExistCache(Map<String, ParameterModel> paramMap);
}
