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
package org.kobic.gwt.smart.closha.server.controller.hadoop.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.server.service.InstancePipelineService;
import org.kobic.gwt.smart.closha.server.service.ProjectService;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.Messages;
import org.kobic.gwt.smart.closha.shared.file.service.impl.HadoopFileSystem;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.CodeFileModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.CompressModel;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;
import org.kobic.gwt.smart.closha.shared.utils.SpringContextHelper;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.common.ZipUtils;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

import turbo.cache.lite.thrift.service.client.CacheLiteClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class HadoopFileSystemControllerImpl extends RemoteServiceServlet implements HadoopFileSystemController {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getRootPath(Map<String, String> config, String userId){
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
				+ userId;
		
		return HadoopFileSystem.getInstance(config).getRootDirectoryPath(path);
	}
	
	@Override
	public String getParentDirectoryPath(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		return HadoopFileSystem.getInstance(config).getParentDirectoryPath(path);
	}
	
	@Override
	public List<FileModel> getRootDirectoryList(Map<String, String> config, String userId) {
		// TODO Auto-generated method stub
		List<FileModel> list = HadoopFileSystem.getInstance(config).getFileList(getRootPath(config, userId), Constants.ROOT_ID, userId);
		return list;
	}
	
	@Override
	public List<FileModel> getFileBrowserSubDirectory(Map<String, String> config, String userId, String path){
		// TODO Auto-generated method stub
		List<FileModel> list = HadoopFileSystem.getInstance(config).getFileList(path, Constants.ROOT_ID, userId);
		return list;
	}
	
	@Override
	public List<FileModel> getSubDirectoryList(Map<String, String> config,
			String userId, String path) {
		// TODO Auto-generated method stub
		List<FileModel> list = HadoopFileSystem.getInstance(config).getFileList(getRootPath(config, userId), Constants.ROOT_ID, userId);
		return list;
	}
	
	@Override
	public List<FileModel> getSubDirectoryList(Map<String, String> config, String userId, String path, String pId) {
		// TODO Auto-generated method stub
		List<FileModel> list = HadoopFileSystem.getInstance(config).getFileList(path, pId, userId);
		return list;
	}
	
	@Override
	public void makeDirectory(Map<String, String> config, String name, String path) {
		// TODO Auto-generated method stub
		HadoopFileSystem.getInstance(config).makeDirectory(path + File.separator + name);
		
	}

	@Override
	public void makeDirectory(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		HadoopFileSystem.getInstance(config).makeDirectory(path);
	}

	@Override
	public void makeHomeDirectory(Map<String, String> config, String userId) {
		// TODO Auto-generated method stub
		List<String> path = new ArrayList<String>();
		path.add(getRootPath(config, userId) + File.separator + config.get(Constants.SERVICE_NAME_KEY) + File.separator + Constants.PROJECT);
		
		HadoopFileSystem.getInstance(config).makeDirectory(path);
	}

	@Override
	public void makeProjectDirectory(Map<String, String> config, String userId, String projectName) {
		// TODO Auto-generated method stub
		
		System.out.println(userId + "'s [" + projectName + "] directory will be created.");
		
		String projectPath = getRootPath(config, userId) + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + projectName;
		
		HadoopFileSystem.getInstance(config).makeDirectory(projectPath);
		
		String logFilePath = projectPath + File.separator + Constants.LOG;
		HadoopFileSystem.getInstance(config).makeDirectory(logFilePath);
	}
	
	@Override
	public String makeScriptDirectory(Map<String, String> config, String userId, String moduleName) {
		// TODO Auto-generated method stub
		
		String path = getRootPath(config, userId) + File.separator + Constants.SCRIPT + File.separator + moduleName + "_script";
		
		HadoopFileSystem.getInstance(config).makeDirectory(path);
		
		return path;
	}
	
	@Override
	public void deleteDirectory(Map<String, String> config, List<String> path) {
		// TODO Auto-generated method stub
		for(String file : path){
			HadoopFileSystem.getInstance(config).deleteFile(file);
		}

		//캐시 디스크에 있는 데이터도 함께 삭제하여 동기화한다.
		CacheLiteClient client = new CacheLiteClient();
		client.delete_cache(path);
	}
	
	@Override
	public void deleteProjectDirectory(Map<String, String> config, String projectName, String userId, String instanceID) {
		// TODO Auto-generated method stub

		String path = getRootPath(config, userId) + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + projectName;
		
		System.out.println("[" + path + "] Project directory will be deleted.");
		
		//HDFS 존재하는 프로젝트 폴더 삭제.
		boolean res = HadoopFileSystem.getInstance(config).deleteFile(path);
		
		System.out.println("Progress results of a project requested to be deleted : [" + res + "]");
		
		if(!res){
			System.out.println("The result of checking the presence of the requested project : [" + HadoopFileSystem.getInstance(config).exist(path) + "]");
		}
		
		//데이터베이스에서 프로젝트 정보를 삭제.
		ProjectService pService = (ProjectService) SpringContextHelper.getBean(getServletContext(), "projectService");
		pService.deleteUserProject(instanceID);
		
		//데이터베이스에서 생성된 인스턴스 파이프라인 정보를 삭제
		InstancePipelineService iService = (InstancePipelineService) SpringContextHelper.getBean(getServletContext(), "instanceService");
		iService.deleteInstancePipeline(instanceID);
		iService.deleteInstancePipelineJob(instanceID);
	}
	
	@Override
	public void editFile(Map<String, String> config, String path, String name) {
		// TODO Auto-generated method stub
		String source = HadoopFileSystem.getInstance(config).getParentDirectoryPath(path) + File.separator + name;
		HadoopFileSystem.getInstance(config).editFile(path, source);
	}
	
	@Override
	public void writeFile(Map<String, String> config, String path, String line, boolean append) {
		// TODO Auto-generated method stub
		HadoopFileSystem.getInstance(config).writeFile(path, line, append);
	}
	
	@Override
	public String getPreviewData(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		String data = null;
		
		try {
			data = HadoopFileSystem.getInstance(config).previewFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public String getLogData(Map<String, String> config,String userId, String projectName) {
		// TODO Auto-generated method stub
		
		String path = getRootPath(config, userId) + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator
				+ Constants.PROJECT + File.separator + projectName
				+ File.separator + Constants.LOG;

		return HadoopFileSystem.getInstance(config).getLogsData(path);
	}

	@Override
	public CodeFileModel getCodeData(Map<String, String> config, String scriptPath) {
		// TODO Auto-generated method stub
		
		CodeFileModel sm = new CodeFileModel();
		sm.setCode(HadoopFileSystem.getInstance(config).readFile(scriptPath));
		sm.setFileName(HadoopFileSystem.getInstance(config).getFileName(scriptPath));
		
		return sm;
	}

	@Override
	public String getExtension(Map<String, String> config, String path) {
		// TODO Auto-generated method stub
		return FilenameUtils.getExtension(path).toLowerCase();
	}

	@Override
	public CompressModel fileCompress(Map<String, String> config, String path, String name) {
		// TODO Auto-generated method stub
		
		String output = "";
		
		File file = new File(path);
		
		if(CommonUtilsGwt.chekExtension(CommonUtilsGwt.getExtension(path))){
			
			output = file.getParentFile().toString() + File.separator + name + ".zip";
			
			File outputFile = new File(output);
			
			if(outputFile.exists()){
				System.out.println("The result file has successfully been compressed.");
			}else{
				if(file.isDirectory()){
					try {
						ZipUtils.zip(path, output);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					ZipUtils.fileZip(path, output);
				}
			}
			
			CompressModel fdm = new CompressModel();
			fdm.setParentPath(outputFile.getParent());
			fdm.setPath(outputFile.getAbsolutePath());
			fdm.setName(outputFile.getName());
			fdm.setSize(String.valueOf(outputFile.length()));
			fdm.setModify(String.valueOf(outputFile.lastModified()));
			
			return fdm;
		}else{
			output = file.getParentFile().toString() + File.separator + name;
			
			File outputFile = new File(output);
			CompressModel fdm = new CompressModel();
			fdm.setParentPath(outputFile.getParent());
			fdm.setPath(outputFile.getAbsolutePath());
			fdm.setName(outputFile.getName());
			fdm.setSize(String.valueOf(outputFile.length()));
			fdm.setModify(String.valueOf(outputFile.lastModified()));
			
			return fdm;
		}
	}

	@Override
	public void copyingFileFromLocalToHDFS(Map<String, String> config,
			String localFilePath, String hdfsFilePath) {
		// TODO Auto-generated method stub
		HadoopFileSystem.getInstance(config).copyingFileFromLocalToHDFS(localFilePath, hdfsFilePath);
	}

	@Override
	public void copyingFileFromHDFSToLocal(Map<String, String> config,
			String hdfsFilePath, String localFilePath) {
		// TODO Auto-generated method stub
		HadoopFileSystem.getInstance(config).copyingFileFromHDFSToLocal(hdfsFilePath, localFilePath);
	}

	@Override
	public String getTempContentFile(Map<String, String> config,
			String hdfsFilePath) {
		// TODO Auto-generated method stub
		
		HadoopFileSystem hdfs = HadoopFileSystem.getInstance(config);
		
		String tmp = config.get(Constants.TMP_DIR_KEY) + "/" + Constants.TEMP;
		
		File file = new File(tmp);
		
		if(!file.exists()) file.mkdirs();
		
		String localFilePath = tmp + "/" + hdfs.getFileName(hdfsFilePath);
		
		hdfs.copyingFileFromHDFSToLocal(hdfsFilePath, localFilePath);
		
		System.out.println("The data from path [" + hdfsFilePath + "] to has successfully been copied to the temporary data folder [" + localFilePath + "].");
		
		return localFilePath;
	}

	@Override
	public boolean isSize(Map<String, String> config, String hdfsFilePath) {
		// TODO Auto-generated method stub
		
		HadoopFileSystem hdfs = HadoopFileSystem.getInstance(config);
		
		long limit = 100 * 1024 * 1024;
		long length = hdfs.getLength(hdfsFilePath);
		
		return limit > length ? true : false;
	}

	@Override
	public void convertHDFSPathToRapidantPath(Map<String, String> config, String source, String userName, String email) {
		// TODO Auto-generated method stub

		HadoopFileSystem hdfs = HadoopFileSystem.getInstance(config);
		
		String target = source;
		String name = hdfs.getFileName(source);
		
		target = target.replaceAll(config.get(Constants.HDFS_URL_KEY), "");
		target = target.replaceAll(config.get(Constants.HDFS_DIR_KEY), config.get(Constants.RAPIDANT_DIR_KEY));
		
		CacheLiteClient client = new CacheLiteClient();
		boolean res = client.copyingFileFromHDFSToLocal(source, target);
		
		if(res){
			System.out.println("Data has been copied from [" + source + "] to [" + target + "](rapidant data directory).");

			CommonUtils.sendEmail(config, String.format(
					Messages.RAPIDANT_SHARE_TITLE, userName, 
					name), String.format(
					Messages.RAPIDANT_SHARE_MESSAGE,
					name, name), email);
		 }else{
			 CommonUtils.sendEmail(config, String.format(
						Messages.RAPIDANT_SHARE_ERR_TITLE, userName, 
						name), String.format(
						Messages.RAPIDANT_SHARE_ERR_MESSAGE,
						name, name), email);
		 }
	}

	@Override
	public void copy(Map<String, String> config, UserDto userDto, List<FileModel> files,
			String target) {
		// TODO Auto-generated method stub
		List<String> source = new ArrayList<String>();
		
		for(FileModel model : files){
			source.add(model.getPath());
		}
		
		HadoopFileSystem hdfs = HadoopFileSystem.getInstance(config);
		
		boolean res = hdfs.copy(source, target);
		
		System.out.println(res);
	}
}