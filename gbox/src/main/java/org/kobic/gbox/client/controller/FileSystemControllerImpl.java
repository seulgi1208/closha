package org.kobic.gbox.client.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.kobic.gbox.client.common.CommonsUtil;
import org.kobic.gbox.client.model.TableFileModel;
import org.kobic.gbox.client.model.TreeFileModel;

public class FileSystemControllerImpl implements FileSystemController{
	
	@Override
	public File[] getRootDrive() {
		// TODO Auto-generated method stub
		return File.listRoots();
	}

	@Override
	public List<TreeFileModel> getSubDirectory(String path, String pid) {
		// TODO Auto-generated method stub
		
		File file = new File(path);
		
		if(file.exists()){
		
			List<TreeFileModel> list = new ArrayList<TreeFileModel>();
			
			int size = file.listFiles().length;
			
			if(size != 0){
				for(File f : file.listFiles()){
					if(!f.isHidden()){
						TreeFileModel fm = new TreeFileModel();
						fm.setId(CommonsUtil.getInstance().getUID());
						fm.setPid(pid);
						fm.setName(f.getName());
						fm.setPath(f.getAbsolutePath());
						fm.setModified(f.lastModified());
						fm.setSize(f.length());
						fm.setDiretory(f.isDirectory());
						list.add(fm);
					}
				}
				return list;
			}
		}
		
		return null;
	}

	@Override
	public List<TableFileModel> getSubDirectory(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		
		List<TableFileModel> list = new ArrayList<TableFileModel>();
		
		if(file.exists() && file.isDirectory()){
		
			File[] subFile = file.listFiles();
			int size = subFile.length;
			
			File pFile = getParentPath(path);
			
			if(pFile != null && pFile.exists()){
				list.add(0, new TableFileModel("..", pFile.getAbsolutePath(),
						pFile.length(), pFile.lastModified(), true));
			}
			
			if(size != 0){
				for(File f : file.listFiles()){
					if(!f.isHidden()){
						TableFileModel fm = new TableFileModel();
						fm.setName(f.getName());
						fm.setPath(f.getAbsolutePath());
						fm.setModified(f.lastModified());
						fm.setSize(f.length());
						fm.setDiretory(f.isDirectory());
						list.add(fm);
					}
				}
				return list;
			}
		}
		return list;
	}

	@Override
	public File getParentPath(String path) {
		// TODO Auto-generated method stub
		
		File file = new File(path);
		File pFile = file.getParentFile();
		
		if(pFile == null){
			return null;
		}else{
			return pFile;
		}
	}

	@Override
	public void makeDirectory(String path, String name) {
		// TODO Auto-generated method stub
		String mPath = path + File.separator + name;
		
		try {
			FileUtils.forceMkdir(new File(mPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean delete(List<String> path) {
		// TODO Auto-generated method stub
		
		List<Boolean> results = new ArrayList<Boolean>();
		
		for(String p : path){
			File f = new File(p);
			try {
				FileUtils.forceDelete(f);
				
				results.add(!f.exists());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return !results.contains(false);
	}
}
