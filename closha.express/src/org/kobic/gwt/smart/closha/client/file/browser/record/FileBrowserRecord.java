package org.kobic.gwt.smart.closha.client.file.browser.record;

import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class FileBrowserRecord extends ListGridRecord implements IsSerializable {
	
	public FileBrowserRecord(){
		
	}
	
	public FileBrowserRecord(String path, String parentPath){
		
		setAttribute("fileId", "");
		setAttribute("parentId", "");
		setAttribute("fileName", "..");
		setAttribute("fileSize", "");
		setAttribute("lastModifyDate", "");
		setAttribute("lastAccessDate", "");
		setAttribute("fileType", Constants.DIRECTORY);
		setAttribute("filePath", path);
		setAttribute("parentPath", parentPath);
		setAttribute("user", "");
		setAttribute("group", "");
		setAttribute("isDic", true);
		setAttribute("icon", "closha/icon/folderopen.png");
	}
	
	public FileBrowserRecord(String fileId, String parentId, String fileName, String fileSize, String lastModifyDate, 
			String lastAccessDate, String fileType, String filePath, String parentPath, String user, String group, boolean isDic, String icon){
		setFileId(fileId);
		setParentId(parentId);
		setFileName(fileName);
		setFileSize(fileSize);
		setLastModifyDate(lastModifyDate);
		setLastAccessDate(lastAccessDate);
		setFileType(fileType);
		setFilePath(filePath);
		setParentPath(parentPath);
		setUser(user);
		setGroup(group);
		setIsDic(isDic);
		setAttribute("icon", icon);
	}
	
	public String getFileId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("fileId");
	}
	
	public void setFileId(String fileId) {
		// TODO Auto-generated method stub
		setAttribute("fileId", fileId);  
	}
	
	public String getParentId() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parentId");
	}
	
	public void setParentId(String parentId) {
		// TODO Auto-generated method stub
		setAttribute("parentId", parentId);  
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("fileName");
	}
	
	public void setFileName(String fileName) {
		// TODO Auto-generated method stub
		setAttribute("fileName", fileName);  
	}
	
	public String getFileSize() {
		// TODO Auto-generated method stub
		return getAttributeAsString("fileSize");
	}
	
	public void setFileSize(String fileSize) {
		// TODO Auto-generated method stub
		setAttribute("fileSize", fileSize);  
	}
	
	public String getLastModifyDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("lastModifyDate");
	}
	
	public void setLastModifyDate(String lastModifyDate) {
		// TODO Auto-generated method stub
		setAttribute("lastModifyDate", lastModifyDate);  
	}
	
	public String getLastAccessDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("lastAccessDate");
	}
	
	public void setLastAccessDate(String lastAccessDate) {
		// TODO Auto-generated method stub
		setAttribute("lastAccessDate", lastAccessDate);  
	}
	
	public String getFileType() {
		// TODO Auto-generated method stub
		return getAttributeAsString("fileType");
	}
	
	public void setFileType(String fileType) {
		// TODO Auto-generated method stub
		setAttribute("fileType", fileType);  
	}
	
	public String getFilePath() {
		// TODO Auto-generated method stub
		return getAttributeAsString("filePath");
	}
	
	public void setFilePath(String filePath) {
		// TODO Auto-generated method stub
		setAttribute("filePath", filePath);  
	}
	
	public String getParentPath() {
		// TODO Auto-generated method stub
		return getAttributeAsString("parentPath");
	}
	
	public void setParentPath(String parentPath) {
		// TODO Auto-generated method stub
		setAttribute("parentPath", parentPath);  
	}
	
	public String getUser() {
		// TODO Auto-generated method stub
		return getAttributeAsString("user");
	}
	
	public void setUser(String user) {
		// TODO Auto-generated method stub
		setAttribute("user", user);  
	}
	
	public String getGroup() {
		// TODO Auto-generated method stub
		return getAttributeAsString("group");
	}
	
	public void setGroup(String group) {
		// TODO Auto-generated method stub
		setAttribute("group", group);  
	}
	
	public String getIsDic() {
		// TODO Auto-generated method stub
		return getAttributeAsString("isDic");
	}
	
	public void setIsDic(boolean isDic) {
		// TODO Auto-generated method stub
		setAttribute("isDic", isDic);  
	}
}
