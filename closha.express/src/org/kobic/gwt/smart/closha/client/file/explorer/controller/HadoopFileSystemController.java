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
package org.kobic.gwt.smart.closha.client.file.explorer.controller;

import java.util.List;
import java.util.Map;

import org.kobic.gwt.smart.closha.shared.model.file.explorer.CodeFileModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.CompressModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("HadoopFileSystemController")
public interface HadoopFileSystemController extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static HadoopFileSystemControllerAsync instance;
		public static HadoopFileSystemControllerAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(HadoopFileSystemController.class);
			}
			return instance;
		}
	}
	
	public String getRootPath(Map<String, String> config, String userId);
	
	public String getParentDirectoryPath(Map<String, String> config, String path);
	
	public List<FileModel> getRootDirectoryList(Map<String, String> config, String userId);
	
	public List<FileModel> getSubDirectoryList(Map<String, String> config, String userId, String path);
	
	public List<FileModel> getSubDirectoryList(Map<String, String> config, String userId, String path, String pId);
	
	public List<FileModel> getFileBrowserSubDirectory(Map<String, String> config, String userId, String path);
	
	public void makeDirectory(Map<String, String> config, String path, String name);
	
	public void makeDirectory(Map<String, String> config, String path);
	
	public void makeHomeDirectory(Map<String, String> config, String userId);
	
	public void makeProjectDirectory(Map<String, String> config, String userId, String projectName);
	
	public String makeScriptDirectory(Map<String, String> config, String userId, String moduleName);
	
	public void deleteDirectory(Map<String, String> config, List<String> path);
	
	public void deleteProjectDirectory(Map<String, String> config, String projectName, String userId, String instanceId);
	
	public void editFile(Map<String, String> config, String path, String name);
	
	public void writeFile(Map<String, String> config, String path, String line, boolean append);
	
	public String getPreviewData(Map<String, String> config, String path);
	
	public String getLogData(Map<String, String> config, String userId, String projectName);
	
	public CodeFileModel getCodeData(Map<String, String> config, String scriptPath);
	
	public String getExtension(Map<String, String> config, String path);
	
	public CompressModel fileCompress(Map<String, String> config, String path, String name);
	
	public void copyingFileFromLocalToHDFS(Map<String, String> config, String localFilePath, String hdfsFilePath);
	
	public void copyingFileFromHDFSToLocal(Map<String, String> config, String hdfsFilePath, String localFilePath);
	
	public void copy(Map<String, String> config, UserDto userDto, List<FileModel> files, String target);
	
	public String getTempContentFile(Map<String, String> config, String hdfsFilePath);
	
	public boolean isSize(Map<String, String> config, String hdfsFilePath);
	
	public void convertHDFSPathToRapidantPath(Map<String, String> config, String source, String userName, String email);
	
}
