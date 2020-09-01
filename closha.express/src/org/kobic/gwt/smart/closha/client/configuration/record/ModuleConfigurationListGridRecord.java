package org.kobic.gwt.smart.closha.client.configuration.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModuleConfigurationListGridRecord extends ListGridRecord implements IsSerializable {
	
	public ModuleConfigurationListGridRecord(){
		
	}
	
	public ModuleConfigurationListGridRecord(String moduleID, String moduleName,
			String moduleDesc, String registerDate, String updateDate,
			String version, String scriptPath, String author, String icon, boolean open, boolean multi, boolean admin, boolean check) {
		
		setAttribute("module_icon", icon.replace("images/", ""));
		
		setModuleID(moduleID);
		setModuleName(moduleName);
		setModuleDesc(moduleDesc);
		setRegisterDate(registerDate);
		setUpdateDate(updateDate);
		setVersion(version);
		setScriptPath(scriptPath);
		setAuthor(author);
		
		setOpen(open);
		setMulti(multi);
		setAdmin(admin);
		setCheck(check);
		
	}
	
	public String getModuleID() {
		// TODO Auto-generated method stub
		return getAttributeAsString("moduleID");
	}
	
	public void setModuleID(String moduleID) {
		// TODO Auto-generated method stub
		setAttribute("moduleID", moduleID);  
	}
	
	public String getModuleName() {
		// TODO Auto-generated method stub
		return getAttributeAsString("moduleName");
	}
	
	public void setModuleName(String moduleName) {
		// TODO Auto-generated method stub
		setAttribute("moduleName", moduleName);  
	}
	
	public String getModuleDesc() {
		// TODO Auto-generated method stub
		return getAttributeAsString("moduleDesc");
	}
	
	public void setModuleDesc(String moduleDesc) {
		// TODO Auto-generated method stub
		setAttribute("moduleDesc", moduleDesc);  
	}
	
	public String getRegisterDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("registerDate");
	}
	
	public void setRegisterDate(String registerDate) {
		// TODO Auto-generated method stub
		setAttribute("registerDate", registerDate);  
	}
	
	public String getUpdateDate() {
		// TODO Auto-generated method stub
		return getAttributeAsString("updateDate");
	}
	
	public void setUpdateDate(String updateDate) {
		// TODO Auto-generated method stub
		setAttribute("updateDate", updateDate);  
	}
	
	public String getVersion() {
		// TODO Auto-generated method stub
		return getAttributeAsString("version");
	}
	
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		setAttribute("version", version);  
	}
	
	public String getScriptPath() {
		// TODO Auto-generated method stub
		return getAttributeAsString("scriptPath");
	}
	
	public void setScriptPath(String scriptPath) {
		// TODO Auto-generated method stub
		setAttribute("scriptPath", scriptPath);  
	}
	
	public String getAuthor() {
		// TODO Auto-generated method stub
		return getAttributeAsString("author");
	}
	
	public void setAuthor(String author) {
		// TODO Auto-generated method stub
		setAttribute("author", author);  
	}
	
	
	public boolean getOpen() {
		// TODO Auto-generated method stub
		return getAttributeAsBoolean("isOpen");
	}

	public void setOpen(boolean open) {
		// TODO Auto-generated method stub
		setAttribute("isOpen", open);
	}
	
	public boolean getMulti() {
		// TODO Auto-generated method stub
		return getAttributeAsBoolean("isMulti");
	}

	public void setMulti(boolean multi) {
		// TODO Auto-generated method stub
		setAttribute("isMulti", multi);
	}
	
	public boolean getAdmin() {
		// TODO Auto-generated method stub
		return getAttributeAsBoolean("isAdmin");
	}

	public void setAdmin(boolean admin) {
		// TODO Auto-generated method stub
		setAttribute("isAdmin", admin);
	}
	
	public boolean getCheck() {
		// TODO Auto-generated method stub
		return getAttributeAsBoolean("isCheck");
	}

	public void setCheck(boolean check) {
		// TODO Auto-generated method stub
		setAttribute("isCheck", check);
	}
	
}
