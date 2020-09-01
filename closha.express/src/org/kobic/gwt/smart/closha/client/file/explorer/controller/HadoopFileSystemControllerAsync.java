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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HadoopFileSystemControllerAsync {
	public void getRootPath(Map<String, String> config, String userId, AsyncCallback<String> callback);
	
	public void getParentDirectoryPath(Map<String, String> config, String path, AsyncCallback<String> callback);
	
	public void getRootDirectoryList(Map<String, String> config, String userId, AsyncCallback<List<FileModel>> callback);
	
	public void getSubDirectoryList(Map<String, String> config, String userId, String path, AsyncCallback<List<FileModel>> callback);
	
	public void getSubDirectoryList(Map<String, String> config, String userId, String path, String pId, AsyncCallback<List<FileModel>> callback);
	
	public void getFileBrowserSubDirectory(Map<String, String> config, String userId, String path, AsyncCallback<List<FileModel>> callback);
	
	public void makeDirectory(Map<String, String> config, String path, String name, AsyncCallback<Void> callback);
	
	public void makeDirectory(Map<String, String> config, String path, AsyncCallback<Void> callback);
	
	public void makeHomeDirectory(Map<String, String> config, String userId, AsyncCallback<Void> callback);
	
	public void makeProjectDirectory(Map<String, String> config, String userId, String projectName, AsyncCallback<Void> callback);
	
	public void makeScriptDirectory(Map<String, String> config, String userId, String moduleName, AsyncCallback<String> callback);
	
	public void deleteDirectory(Map<String, String> config, List<String> path, AsyncCallback<Void> callback);
	
	public void deleteProjectDirectory(Map<String, String> config, String projectName, String userId, String instanceId, AsyncCallback<Void> callback);
	
	public void editFile(Map<String, String> config, String path, String name, AsyncCallback<Void> callback);
	
	public void writeFile(Map<String, String> config, String path, String line, boolean append, AsyncCallback<Void> callback);
	
	public void getPreviewData(Map<String, String> config, String path, AsyncCallback<String> callback);
	
	public void getLogData(Map<String, String> config, String userId, String projectName, AsyncCallback<String> callback);
	
	public void getCodeData(Map<String, String> config, String scriptPath, AsyncCallback<CodeFileModel> callback);
	
	public void getExtension(Map<String, String> config, String path, AsyncCallback<String> callback);
	
	public void fileCompress(Map<String, String> config, String path, String name, AsyncCallback<CompressModel> callback);
	
	public void copyingFileFromLocalToHDFS(Map<String, String> config, String localFilePath, String hdfsFilePath, AsyncCallback<Void> callback);
	
	public void copyingFileFromHDFSToLocal(Map<String, String> config, String hdfsFilePath, String localFilePath, AsyncCallback<Void> callback);
	
	public void copy(Map<String, String> config, UserDto userDto, List<FileModel> files, String target, AsyncCallback<Void> callback);
	
	public void getTempContentFile(Map<String, String> config, String hdfsFilePath, AsyncCallback<String> callback);
	
	public void isSize(Map<String, String> config, String hdfsFilePath, AsyncCallback<Boolean> callback);
	
	public void convertHDFSPathToRapidantPath(Map<String, String> config, String source, String userName, String email, AsyncCallback<Void> callback);
	
}
