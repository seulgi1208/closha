package org.kobic.gwt.smart.closha.shared.file.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMRecord;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.file.service.FileSystemService;
import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;
import org.kobic.gwt.smart.closha.shared.utils.common.CommonUtils;
import org.kobic.gwt.smart.closha.shared.utils.common.ReverseLineInputStream;
import org.kobic.gwt.smart.closha.shared.utils.gwt.CommonUtilsGwt;

public class UnixFileSystem implements FileSystemService{
	
	public static UnixFileSystem instance;
	
	public static UnixFileSystem getInstance(){
		
		if(instance == null){
			instance = new UnixFileSystem();
		}else {
			return instance;
		}
		
		return instance;
	}
	
	@Override
	public String getRootDirectoryPath(String filePath) {
		// TODO Auto-generated method stub
		return Paths.get(filePath).toAbsolutePath().toString();
	}
	
	@Override
	public String getParentDirectoryPath(String path) {
		// TODO Auto-generated method stub
		return Paths.get(path).getParent().toString();
	}
	
	@Override
	public List<FileModel> getFileList(String path, String pId, String userId) {
		// TODO Auto-generated method stub
		
		Path p = Paths.get(path);
		List<FileModel> list = new ArrayList<FileModel>();
		
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(p)){
			
			for(Path file : ds){
				
				BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
				
				FileModel fm = new FileModel();
				
				fm.setId(CommonUtils.getUUID());
				fm.setpId(pId);
				fm.setName(file.getFileName().toString());
				fm.setSize(String.valueOf(attr.size()));
				fm.setLastModifyDate(String.valueOf(attr.lastModifiedTime().toMillis()));
				fm.setLastAccessDate(String.valueOf(attr.lastAccessTime().toMillis()));
				fm.setPath(file.toAbsolutePath().toString());
				fm.setpPath(file.getParent().toAbsolutePath().toString());
				fm.setUser(userId);
				fm.setGroup(userId);

				if(file.toFile().isDirectory()){
					
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
		}catch (IOException e){
			System.err.println(e);
		}
		return list;
	}
	
	@Override
	public boolean checkSubDirectory(String path) {
		// TODO Auto-generated method stub
		File[] lists = new File(path).listFiles();
		return lists.length != 0 ? true : false;
	}
	
	@Override
	public boolean makeDirectory(String path) {
		// TODO Auto-generated method stub

		Path newDir = FileSystems.getDefault().getPath(path);
		
		if(Files.notExists(newDir, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})){
	        try {
	        	newDir = Files.createDirectory(newDir);
	        } catch (IOException e) {
	            System.err.println(e);
	        }
		}
		
		return Files.exists(newDir, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
	}
	
	@Override
	public boolean makeDirectory(List<String> filePath) {
		// TODO Auto-generated method stub
		
		for(String path : filePath){
			if(!makeDirectory(path)){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean createFile(String filePath) {
		// TODO Auto-generated method stub
		Path path = Paths.get(filePath);
		
		boolean isNew = false;
		
		if(!path.toFile().exists()){
			try {
				isNew = path.toFile().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isNew;
	}
	
	@Override
	public boolean deleteFile(String path) {
		// TODO Auto-generated method stub
		
		boolean result = false;
        
		Path target = FileSystems.getDefault().getPath(path);

		try{
			if(target.toFile().exists()){
				if(target.toFile().isFile()){
					result = Files.deleteIfExists(target);
				}else{
					FileUtils.deleteDirectory(target.toFile());
				}
			}else{
				System.out.println("There are no data on the [" + path + "].");
			}
		} catch (IOException | SecurityException e) {
            System.err.println(e);
        }
		return result;
	}
	
	@Override
	public boolean editFile(String souruce, String target) {
		// TODO Auto-generated method stub
		Path path = FileSystems.getDefault().getPath(souruce);
		try{
			Files.move(path, path.resolveSibling(target), StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e){
			System.err.println(e);
		}
		return false;
	}

	@Override
	public void writeFile(String path, String line, boolean append) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(line);
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String previewFile(String filePath) throws IOException {
		// TODO Auto-generated method stub
		if(FilenameUtils.getExtension(filePath).toLowerCase().equals("bam")
				|| FilenameUtils.getExtension(filePath).toLowerCase().equals("sam")) {
			return readBinaryFile(filePath);
		}else{
			return readLineFile(filePath);
		}
	}
	
	@Override
	public String readBinaryFile(String filePath) {
		// TODO Auto-generated method stub

		StringBuffer data = new StringBuffer();
		
		try{
			Path path = Paths.get(filePath);
			SAMFileReader reader = new SAMFileReader(path.toFile());
			data.append("<font face=\"courier new\" size=\"2\">" + reader.getFileHeader().getTextHeader() + "</font><br>");

			int i = 0;

			for(SAMRecord record : reader){
				data.append("<font face=\"courier new\" size=\"2\">" + record.getSAMString() + "</font><br>");
				if(i <= 100){
					i++;
				}else{
					break;
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data.toString();
	}
	
	@Override
	public String readFile(String path) {
		// TODO Auto-generated method stub
		
		String str;
		
		StringBuffer log = new StringBuffer();
		
		if(Paths.get(path).toFile().exists()){
			try {
				BufferedReader in = new BufferedReader(new FileReader(Paths.get(path).toFile()));
				
				while ((str = in.readLine()) != null) {
					log.append("<font face=\"courier new\" size=\"2\">" + str + "</font><br>");
				}
				
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return log.toString();
		}else{
			return Constants.NULL_MESSAGE;
		}
	}
	
	@Override
	public String readLineFile(String path) {
		// TODO Auto-generated method stub
		
		long recnum = 1;
		
		String str;
		
		StringBuffer data = new StringBuffer();

		try {
			BufferedReader in = new BufferedReader(new FileReader(Paths.get(path).toFile()));
			
			while ((str = in.readLine()) != null) {
				data.append("<font face=\"courier new\" size=\"2\">" + str + "</font><br>");
				if (((++recnum) % 100) == 0) {
					break;
				}
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}
	
	@Override
	public String getFileName(String path) {
		// TODO Auto-generated method stub
		return Paths.get(path).getFileName().toString();
	}
	
	public String reverseReadFile(String filePath) {
		// TODO Auto-generated method stub
		
		Path path = Paths.get(filePath);
		
		if(path.toFile().exists()){
			StringBuffer log = new StringBuffer();
			
			try {
				BufferedReader in = new BufferedReader (new InputStreamReader (new ReverseLineInputStream(path.toFile())));
				
				for(int i = 0; i <= 300; i++) {
				    String line = in.readLine();
				    if (line == null) {
				        break;
				    }
				    log.append("<font face=\"courier new\" size=\"2\">" + line + "</font><br>");
				}
				
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return log.toString();
		}else{
			return Constants.NULL_MESSAGE;
		}
	}
	
	public boolean exist(String path){
		return new File(path).exists();
	}
	
	public boolean makeLogDirectory(String path){
		return new File(path).mkdirs();
	}
	
	public void copyLogFiles(Map<String, String> config, String userId, String projectName, String working_path){
		
		String path = config.get(Constants.HDFS_DIR_KEY) + File.separator
				+ userId + File.separator
				+ config.get(Constants.SERVICE_NAME_KEY) + File.separator 
				+ Constants.PROJECT + File.separator + projectName + File.separator 
				+ Constants.LOG;
	
		File[] logs = new File(working_path).listFiles();
		
		for(File file : logs){
			HadoopFileSystem.getInstance(config).copyingFileFromLocalToHDFS(file.getAbsolutePath(), path);
		}	
	}
	
	public String readCodeFile(String scriptPath) {
		// TODO Auto-generated method stub		
		Path path = Paths.get(scriptPath);
		
		String line;
		StringBuffer source = new StringBuffer();
		
		if(path.toFile().canRead()){
			try {
				BufferedReader in = new BufferedReader(new FileReader(path.toFile()));
				
				while ((line = in.readLine()) != null) {
					source.append(line + "\n");
				}
				
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return source.toString();
		}else{
			return Constants.NULL_MESSAGE;
		}
	}

	@Override
	public boolean isExists(String path) {
		// TODO Auto-generated method stub
		return new File(path).exists();
	}

	@Override
	public long getLength(String path) {
		// TODO Auto-generated method stub
		return new File(path).length();
	}

	@Override
	public boolean copy(List<String> source, String target) {
		// TODO Auto-generated method stub
		
		boolean res = true;
		
		File destFile = new File(target);
		
		for(String s : source){
			File srcFile = new File(s);
			
			if(srcFile.isDirectory()){
				try {
					FileUtils.copyDirectory(srcFile, destFile);
				} catch (IOException e) {
					res = false;
				}
			}else{
				try {
					FileUtils.copyFile(srcFile, destFile);
				} catch (IOException e) {
					res = false;
				}
			}
		}
		
		return res;
	}
}