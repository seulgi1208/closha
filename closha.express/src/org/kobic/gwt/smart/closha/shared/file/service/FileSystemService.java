package org.kobic.gwt.smart.closha.shared.file.service;

import java.io.IOException;
import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.file.explorer.FileModel;

public interface FileSystemService {
	
	String getRootDirectoryPath(String path);
	
	String getParentDirectoryPath(String path);
	
	List<FileModel> getFileList(String path, String pId, String userId);
	
	boolean checkSubDirectory(String path);
	
	boolean makeDirectory(String path);
	
	boolean makeDirectory(List<String> path);
	
	boolean createFile(String path);
	
	boolean deleteFile(String path);
	
	boolean editFile(String souruce, String target);
	
	void writeFile(String path, String line, boolean append);
	
	String previewFile(String path) throws IOException;
	
	String readBinaryFile(String path);
	
	String readFile(String path);
	
	String readLineFile(String path);
	
	String getFileName(String path);
	
	boolean isExists(String path);
	
	long getLength(String path);
	
	boolean copy(List<String> source, String target);
	
}
