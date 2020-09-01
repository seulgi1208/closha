package org.kobic.gwt.smart.closha.client.file.explorer.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.tree.TreeNode;

public class FileExplorerRecord extends TreeNode implements IsSerializable {
	
	public FileExplorerRecord(){
		
	}
	
	public FileExplorerRecord(String fileId, String parentId, String fileName,
			String fileSize, String lastModifyDate, String lastAccessDate,
			String fileType, String filePath, String user, String group,
			boolean isDic, String icon) {
		
		setFileId(fileId);
		setParentId(parentId);
		setFileName(fileName);
		setFileSize(fileSize);
		setLastModifyDate(lastModifyDate);
		setLastAccessDate(lastAccessDate);
		setFileType(fileType);
		setFilePath(filePath);
		setUser(user);
		setGroup(group);
		setIsDic(isDic);
		setAttribute("icon", icon);
		setAttribute("event", false);
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