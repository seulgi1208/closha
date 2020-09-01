package org.kobic.gwt.smart.closha.shared.file.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMRecord;
import net.sf.samtools.SAMFileReader.ValidationStringency;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.server.controller.hadoop.file.HadoopFileSystemControllerImpl;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.FileSystemService;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

public class HadoopFileSystem implements FileSystemService{

	private FileSystem fileSystem = null;	
	
	private Map<String, String> config = null;
	
	protected static HadoopFileSystem instance = null;

	private SAMFileReader reader = null;
	
	private Configuration getConf(){
		
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", config.get(Constants.HDFS_URL_KEY));
		conf.set("hadoop.job.ugi", config.get(Constants.HDFS_USER_KEY));
		conf.setBoolean("fs.hdfs.impl.disable.cache", true);
		
		return conf;
	}
	
	public FileSystem getFileSystemInstance(){
		
		if(fileSystem == null){
			try {
				
				fileSystem = FileSystem.get(getConf());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fileSystem;
		}else{
			return fileSystem;
		}
	}
	
	public static HadoopFileSystem getInstance(Map<String, String> config){
		
		if(instance == null){
			instance = new HadoopFileSystem(config);
		}else {
			return instance;
		}
		
		return instance;
	}
	
	public HadoopFileSystem(Map<String, String> config){
		this.config = config;
	}
	
	
	@Override
	public String getRootDirectoryPath(String path){
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			path = dfs.getFileStatus(new Path(path)).getPath().toString();
			/*dfs.close();*/
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
	}
	
	@Override
	public String getParentDirectoryPath(String path){
		return new Path(path).getParent().toString();
	}
	
	
	@Override
	public List<FileModel> getFileList(String path, String pId, String userId) {
		
		List<FileModel> list = new ArrayList<FileModel>();
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			
			FileStatus[] lists = dfs.listStatus(new Path(path));
			
			for (int i = 0; i < lists.length; i++) {
				
				FileModel fm = new FileModel();
				
				fm.setId(CommonUtils.getUUID());
				fm.setpId(pId);
				fm.setName(lists[i].getPath().getName());
				fm.setSize(String.valueOf(lists[i].getLen()));
				fm.setLastModifyDate(CommonUtils.convertMillisecondsToDate(lists[i].getModificationTime()));
				fm.setLastAccessDate(CommonUtils.convertMillisecondsToDate(lists[i].getAccessTime()));
				fm.setPath(lists[i].getPath().toString().replace(config.get(Constants.HDFS_URL_KEY), ""));
				fm.setpPath(lists[i].getPath().getParent().toString());
				fm.setUser(lists[i].getOwner());
				fm.setGroup(lists[i].getGroup());
				
				if(lists[i].isDirectory()){
					
					fm.setType(Constants.DIRECTORY);
					fm.setDic(true);
					
					if(checkSubDirectory(fm.getPath())){
						fm.setIcon("closha/icon/folder_add.png");
					}else{
						fm.setIcon("closha/icon/folder.png");
					}
						
				}else{
					
					fm.setType(Constants.FILE);
					fm.setDic(false);
					
					if(CommonUtilsGwt.chekExtension(CommonUtilsGwt.getExtension(fm.getPath()))){
						fm.setIcon("closha/icon/builder.gif");
					}else{
						fm.setIcon("closha/icon/archive.png");
					}
				}
				
				list.add(fm);
			}
			
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public boolean checkSubDirectory(String path) {
		
		FileStatus[] lists = null;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			lists = dfs.listStatus(new Path(path));
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return lists.length != 0 ? true : false;
	}
	
	@Override
	public boolean makeDirectory(String path) {
		
		boolean result = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			result = dfs.mkdirs(new Path(path));
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return result;
	}
	
	@Override
	public boolean makeDirectory(List<String> path) {
		// TODO Auto-generated method stub
		
		for(String dir : path){
			if(!makeDirectory(dir)){
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean createFile(String path) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			result = dfs.createNewFile(new Path(path));
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public boolean deleteFile(String path) {
		
		boolean result = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			result = dfs.delete(new Path(path), true);
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	@Override
	public boolean editFile(String souruce, String target) {
		
		boolean result = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			result = dfs.rename(new Path(souruce), new Path(target));
			/*dfs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void writeFile(String path, String line, boolean append) {
		// TODO Auto-generated method stub
		
		FileSystem dfs = getFileSystemInstance();

		Path target = new Path(path);
		
		try{
			if (dfs.exists(target)) {
				
				if(append){
					FSDataInputStream in = dfs.open(target);
					line = in.readUTF() + line;
					in.close();
				}
				
				dfs.delete(target, true);
			}
			
			FSDataOutputStream out = dfs.create(target);
			out.writeUTF(line);
			out.close();
			
			/*dfs.close();*/
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String previewFile(String filePath) throws IOException {
		if (FilenameUtils.getExtension(filePath).toLowerCase().equals("bam")
				|| FilenameUtils.getExtension(filePath).toLowerCase().equals("sam")) {
			return readBinaryFile(filePath);
		} else {
			return readLineFile(filePath);
		}
	}

	@Override
	public String readBinaryFile(String path){
		
		StringBuffer content = new StringBuffer();
		
		try{
			HadoopFileSystemController controller = new HadoopFileSystemControllerImpl();
			String temp = controller.getTempContentFile(config, path);
			
			File file = new File(temp);
			
			try{
				reader = new SAMFileReader(file);
				reader.setValidationStringency(ValidationStringency.SILENT);
			}catch(Exception e){
				System.out.println("An error has occurred while reading the SAM || BAM file.");
			}
			
			content.append("<font face=\"courier new\" size=\"2\">" + reader.getFileHeader().getTextHeader() + "</font><br>");
			
			int i = 0;

			for(SAMRecord record : reader){
				content.append("<font face=\"courier new\" size=\"2\">" + record.getSAMString() + "</font><br>");
				if(i <= 20){
					i++;
				}else{
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return content.toString();
	}
	
	@Override
	public String readFile(String path) {
		// TODO Auto-generated method stub
		
		String temp;
		
		StringBuffer data = new StringBuffer();
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dfs.open(new Path(path))));
			
			while ((temp = in.readLine()) != null) {
				data.append(temp);
			}
			
			/*dfs.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}

	@Override
	public String readLineFile(String path) {
		
		long recnum = 1;
		
		String temp;
		
		StringBuffer data = new StringBuffer();
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dfs.open(new Path(path))));
			
			while ((temp = in.readLine()) != null) {
				data.append("<font face=\"courier new\" size=\"2\">" + temp + "</font><br>");
				if (((++recnum) % 1000) == 0) {
					break;
				}
			}
			
			/*dfs.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}

	@Override
	public String getFileName(String path) {
		// TODO Auto-generated method stub
		return new Path(path).getName();
	}
	
	public void copyingFileFromLocalToHDFS(String localFilePath, String hdfsFilePath) {
		// TODO Auto-generated method stub
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			dfs.copyFromLocalFile(new Path(localFilePath), new Path(hdfsFilePath));
			/*dfs.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void copyingFileFromHDFSToLocal(String hdfsFilePath, String localFilePath) {
		// TODO Auto-generated method stub
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			dfs.copyToLocalFile(new Path(hdfsFilePath), new Path(localFilePath));
			/*dfs.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void copy(String source, String target){
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			FileUtil.copy(dfs, new Path(source), getFileSystemInstance(), new Path(target), false, getConf());
			/*dfs.close();*/
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getLogsData(String path){
		
		StringBuffer buffer = new StringBuffer();
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			
			FileStatus[] lists = dfs.listStatus(new Path(path));
			
			for (int i = 0; i < lists.length; i++) {
				buffer.append("<font face=\"courier new\" size=\"2\">[" + lists[i].getPath().getName() + "]</font><br>");
				buffer.append(readLineFile(lists[i].getPath().toString()));
			}
			
			/*dfs.close();*/
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public boolean exist(String path){
		
		boolean exsit = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			exsit = dfs.exists(new Path(path));
			/*dfs.close();*/
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exsit;
	}

	@Override
	public boolean isExists(String path) {
		// TODO Auto-generated method stub
		
		boolean exists = false;
		
		FileSystem dfs = getFileSystemInstance();
		
		try {
			exists = dfs.exists(new Path(path));
			/*dfs.close();*/
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exists;
	}

	@Override
	public long getLength(String path) {
		// TODO Auto-generated method stub
		
		Path p = new Path(path);
		
        FileSystem dfs = getFileSystemInstance();
        
        ContentSummary cSummary = null;
        
		try {
			cSummary = dfs.getContentSummary(p);
			/*dfs.close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		long length = cSummary.getLength();
        
		return length;
	}

	@Override
	public boolean copy(List<String> source, String target) {
		// TODO Auto-generated method stub
		
		FileSystem dfs = getFileSystemInstance();
		
		Path destFile = new Path(target);
		
		boolean res = false;
		
		List<Boolean> results = new ArrayList<Boolean>();
		
		for(String srcFile: source){
			try {
				res = FileUtil.copy(dfs, new Path(srcFile), dfs, destFile, false, dfs.getConf());
				results.add(res);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return results.contains(false) ? false : true;
	}
}