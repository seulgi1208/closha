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

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.kobic.gwt.smart.closha.client.controller.CacheLiteClientController;
import org.kobic.gwt.smart.closha.shared.model.parameter.ParameterModel;

import turbo.cache.lite.thrift.service.TurboCacheThriftService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CacheLiteClientControllerImpl extends RemoteServiceServlet
		implements CacheLiteClientController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String master_ip = "192.168.154.52";
	public static final int master_port = 7912;
	
	@Override
	public Map<String, ParameterModel> isExistCache(Map<String, ParameterModel> paramMap) {
		// TODO Auto-generated method stub
		
		Map<String, ParameterModel> map = new HashMap<String, ParameterModel>();
		
		String key;
		ParameterModel value;
		
		boolean res = false;
		
		try {
			TTransport transport;
			transport = new TFramedTransport(new TSocket(master_ip, master_port));
			TProtocol protocol = new TBinaryProtocol(transport);
			TurboCacheThriftService.Client client = new TurboCacheThriftService.Client(protocol);

			transport.open();
			
			for (Map.Entry<String, ParameterModel> elem : paramMap.entrySet()) {
				
				key = elem.getKey();
				value = elem.getValue();
				
				res = client.isExistCache(value.getSetupValue());
				value.setExistCache(res);
				
				map.put(key, value);
			}
			
			transport.close();
		} catch (TException e) {
			e.printStackTrace();
		}
		
		return map;
	}
}