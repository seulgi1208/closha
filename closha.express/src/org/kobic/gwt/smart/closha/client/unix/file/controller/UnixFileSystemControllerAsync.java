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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UnixFileSystemControllerAsync {
	public void getRootDirectoryPath(Map<String, String> config, String userID, AsyncCallback<String> callback);
	
	public void getParentDirectoryPath(Map<String, String> config, String path, AsyncCallback<String> callback);
	
	public void getRootDirectoryList(Map<String, String> config, String userId, AsyncCallback<List<FileModel>> callback);
	
	public void getSubDirectoryList(Map<String, String> config, String userID, String path, AsyncCallback<List<FileModel>> callback);

	public void getSubDirectoryList(Map<String, String> config, String userID, String path, String parentID, AsyncCallback<List<FileModel>> callback);
	
	public void makeDirectory(Map<String, String> config, String name, String path, AsyncCallback<Void> callback);
	
	public void makeDirectory(Map<String, String> config, String path, AsyncCallback<Void> callback);
	
	public void makeUserHomeFolder(Map<String, String> config, String userID, AsyncCallback<Void> callback);
	
	public void makeProjectFolder(Map<String, String> config, String userID, String projectName, AsyncCallback<Void> callback);
	
	public void makeUploadDataFolder(Map<String, String> config, String userID, String projectName, AsyncCallback<String> callback);
	
	public void makeUploadScriptFolder(Map<String, String> config, String userID, String moduleName, AsyncCallback<String> callback);
	
	public void deleteDirectory(Map<String, String> config, List<String> paths, AsyncCallback<Void> callback);
	
	public void deleteDirectory(String path, AsyncCallback<Void> callback);
	
	public void deleteUserProject(Map<String, String> config, String projectName, String userID, String instanceID, AsyncCallback<Void> callback);

	public void writeFile(String path, String line, boolean append, AsyncCallback<Void> callback);
	
	public void editFileName(String path, String name, AsyncCallback<Void> callback);
	
	public void getPreviewData(Map<String, String> config, String path, AsyncCallback<String> callback);
	
	public void getFileExtensionType(String path, AsyncCallback<String> callback);
	
	public void getLogFile(Map<String, String> config, String userID, String projectName, AsyncCallback<LinkedHashMap<String, String>> callback);
	
	public void getLogFileData(Map<String, String> config, String userID, String projectName, AsyncCallback<String> callback);
	
	public void readSourceCodeFile(String scriptPath, AsyncCallback<CodeFileModel> callback);
	
	public void readFile(String path, AsyncCallback<String> callback);
}
