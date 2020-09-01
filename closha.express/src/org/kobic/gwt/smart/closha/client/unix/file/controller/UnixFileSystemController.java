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
package org.kobic.gwt.smart.closha.client.unix.file.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.file.explorer.CodeFileModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UnixFileSystemController")
public interface UnixFileSystemController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static UnixFileSystemControllerAsync instance;
		public static UnixFileSystemControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(UnixFileSystemController.class);
			}
			return instance;
		}
	}
	
	public String getRootDirectoryPath(Map<String, String> config, String userID);
	
	public String getParentDirectoryPath(Map<String, String> config, String path);
	
	public List<FileModel> getRootDirectoryList(Map<String, String> config, String userId);
	
	public List<FileModel> getSubDirectoryList(Map<String, String> config, String userID, String path);

	public List<FileModel> getSubDirectoryList(Map<String, String> config, String userID, String path, String parentID);
	
	public void makeDirectory(Map<String, String> config, String name, String path);
	
	public void makeDirectory(Map<String, String> config, String path);
	
	public void makeUserHomeFolder(Map<String, String> config, String userID);
	
	public void makeProjectFolder(Map<String, String> config, String userID, String projectName);
	
	public String makeUploadDataFolder(Map<String, String> config, String userID, String projectName);
	
	public String makeUploadScriptFolder(Map<String, String> config, String userID, String moduleName);
	
	public void deleteDirectory(Map<String, String> config, List<String> paths);
	
	public void deleteDirectory(String path);
	
	public void deleteUserProject(Map<String, String> config, String projectName, String userID, String instanceID);

	public void writeFile(String path, String line, boolean append);
	
	public void editFileName(String path, String name);
	
	public String getPreviewData(Map<String, String> config, String path);
	
	public String getFileExtensionType(String path);
	
	public LinkedHashMap<String, String> getLogFile(Map<String, String> config, String userID, String projectName);
	
	public String getLogFileData(Map<String, String> config, String userID, String projectName);
	
	public CodeFileModel readSourceCodeFile(String scriptPath);
	
	public String readFile(String path);
}
