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
package org.kobic.gwt.smart.closha.server.controller.unix.file;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.kobic.gwt.smart.closha.client.unix.file.controller.UnixFileSystemController;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.server.service.ProjectService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.impl.UnixFileSystem;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.CodeFileModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnixFileSystemControllerImpl extends RemoteServiceServlet implements UnixFileSystemController {

	private static final long serialVersionUID = 1L;

	@Override
	public String getRootDirectoryPath(Map<String, String> config, String userID) {
		// TODO Auto-generated method stub
		
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator + Constants.SCRIPT + File.separator + userID;
		
		File file = new File(path);
		
		if(!file.exists()){
			file.mkdirs();
		}
		
		return path;
	}

	@Override
	public String getParentDirectoryPath(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		return UnixFileSystem.getInstance().getParentDirectoryPath(path);
	}

	@Override
	public void makeDirectory(Map<String, String> config, String name,
			String path) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().makeDirectory(path + File.separator + name);
	}

	@Override
	public void makeDirectory(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().makeDirectory(path);
	}

	@Override
	public void makeUserHomeFolder(Map<String, String> config, String userID) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().makeDirectory(config.get(Constants.UNIX_DIR_KEY) + File.separator + userID);
	}

	@Override
	public void makeProjectFolder(Map<String, String> config, String userID,
			String projectName) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().makeDirectory(config.get(Constants.UNIX_DIR_KEY) + File.separator + userID + File.separator + projectName);
	}

	@Override
	public String makeUploadDataFolder(Map<String, String> config,
			String userID, String projectName) {
		// TODO Auto-generated method stub
		
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator + userID + File.separator + projectName + "_data";
		
		UnixFileSystem.getInstance().makeDirectory(path);
		
		return path;
	}

	@Override
	public String makeUploadScriptFolder(Map<String, String> config,
			String userID, String moduleName) {
		// TODO Auto-generated method stub
		
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator + Constants.SCRIPT + File.separator + userID + File.separator + moduleName + "_script";
		
		UnixFileSystem.getInstance().makeDirectory(path);
		
		return path;
	}

	@Override
	public void deleteUserProject(Map<String, String> config, String projectName, String userID,
			String instanceID) {
		// TODO Auto-generated method stub
		
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator
				+ userID + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + projectName;
		
		UnixFileSystem.getInstance().deleteFile(path);
		
		//user project + instance pipeline + pipeline job information delete from database
		ProjectService pService = (ProjectService) SpringContextHelper.getBean(getServletContext(), "projectService");
		pService.deleteUserProject(instanceID);
		
		InstancePipelineService iService = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		iService.deleteInstancePipeline(instanceID);
		iService.deleteInstancePipelineJob(instanceID);
	}
	

	@Override
	public void deleteDirectory(Map<String, String> config, List<String> paths) {
		// TODO Auto-generated method stub
		for(String path : paths){
			UnixFileSystem.getInstance().deleteFile(path);
		}
	}

	@Override
	public void writeFile(String path, String line, boolean append) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().writeFile(path, line, append);
	}

	@Override
	public void editFileName(String path, String name) {
		// TODO Auto-generated method stub
		
		String edit = UnixFileSystem.getInstance().getParentDirectoryPath(path) + File.separator + name;
		UnixFileSystem.getInstance().editFile(path, edit);
	}

	@Override
	public String getPreviewData(Map<String, String> config, String path) {
		// TODO Auto-generated method stub

		return UnixFileSystem.getInstance().readFile(path);
	}

	@Override
	public String getFileExtensionType(String path) {
		// TODO Auto-generated method stub
		return FilenameUtils.getExtension(path).toLowerCase();
	}
	
	@Override
	public LinkedHashMap<String, String> getLogFile(Map<String, String> config, String userID,
			String projectName) {
		// TODO Auto-generated method stub
		
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator + Constants.LOG + File.separator + userID + File.separator 
				+ projectName + Constants.LOG_DIR_EXTENSION;
		
		System.out.println("log dir: " + path);
		
		LinkedHashMap<String, String> logsMap = new LinkedHashMap<String, String>();
		
		File file = new File(path);
				
		if(file.exists()){
			for(File f: file.listFiles()){
				logsMap.put(f.getAbsolutePath(), f.getName());
			}
		}
		
		return logsMap;
	}

	@Override
	public String getLogFileData(Map<String, String> config, String userID, String projectName) {
		// TODO Auto-generated method stub
		String path = config.get(Constants.UNIX_DIR_KEY) + File.separator + Constants.LOG + File.separator + userID + File.separator 
				+ projectName + Constants.LOG_DIR_EXTENSION;
		
		System.out.println("log dir: " + path);
		
		File file = new File(path);
		
		File lists[] = null;
		
		StringBuffer logs = new StringBuffer();
		
		if(file.isDirectory()){
			lists = file.listFiles();
			
			for(File f : lists){
				
				System.out.println("log files: " + f.getAbsolutePath());
				
				logs.append(UnixFileSystem.getInstance().reverseReadFile(f.getAbsolutePath()));
			}
		}

		return logs.toString();
	}

	@Override
	public List<FileModel> getRootDirectoryList(Map<String, String> config, String userId) {
		// TODO Auto-generated method stub
		return UnixFileSystem.getInstance().getFileList(getRootDirectoryPath(config, userId), Constants.ROOT_ID, userId);
	}

	@Override
	public List<FileModel> getSubDirectoryList(Map<String, String> config,
			String userID, String path) {
		// TODO Auto-generated method stub
		return UnixFileSystem.getInstance().getFileList(path, Constants.ROOT_ID, userID);
	}

	@Override
	public List<FileModel> getSubDirectoryList(Map<String, String> config,
			String userID, String path, String parentID) {
		// TODO Auto-generated method stub
		
		return UnixFileSystem.getInstance().getFileList(path, parentID, userID);
	}


	@Override
	public CodeFileModel readSourceCodeFile(String scriptPath) {
		// TODO Auto-generated method stub
		
		CodeFileModel sm = new CodeFileModel();
		sm.setCode(UnixFileSystem.getInstance().readCodeFile(scriptPath));
		sm.setFileName(UnixFileSystem.getInstance().getFileName(scriptPath));
		
		return sm;
	}

	@Override
	public void deleteDirectory(String path) {
		// TODO Auto-generated method stub
		UnixFileSystem.getInstance().deleteFile(path);
	}

	@Override
	public String readFile(String path) {
		// TODO Auto-generated method stub
		return UnixFileSystem.getInstance().readFile(path);
	}
}