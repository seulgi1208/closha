package org.kobic.gbox.client.controller;

import java.io.File;
import java.util.List;

import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;

public interface FileSystemController {
	
	public static class Util {
		
		private static FileSystemController instance;
		
		public static FileSystemController getInstance(){
			if (instance == null) {
				instance = new FileSystemControllerImpl();
			}
			return instance;
		}
	}
	
	public File[] getRootDrive();
	public File getParentPath(String path);
	public List<TreeFileModel> getSubDirectory(String path, String pid);
	public List<TableFileModel> getSubDirectory(String path);
	public void makeDirectory(String path, String name);
	public boolean delete(List<String> path);
	
}
