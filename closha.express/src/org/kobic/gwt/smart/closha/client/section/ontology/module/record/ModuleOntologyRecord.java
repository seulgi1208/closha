package org.kobic.gwt.smart.closha.client.section.ontology.module.record;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ModuleOntologyRecord extends TreeNode implements IsSerializable {
	
	public ModuleOntologyRecord(){
	}
	
	public ModuleOntologyRecord(String type, String moduleID,
			String ontologyID, String appFormat, String language,
			String scriptPath, String cmd, String moduleName,
			String moduleTreeName, String moduleDesc, String moduleAuthor,
			String registerDate, String updateDate, String parameter,
			String linkedKey, String version, String iconSrc, String icon, String url, boolean open, boolean multi, boolean admin, boolean check, boolean parallel, boolean alignment) {
	
		setType(type);
		setModuleID(moduleID);
		setOntologyID(ontologyID);
		setAppFormat(appFormat);
		setLanguage(language);
		setScriptPath(scriptPath);
		setCmd(cmd);
		setModuleName(moduleName);
		setModuleTreeName(moduleTreeName);
		setModuleDesc(moduleDesc);
		setModuleAuthor(moduleAuthor);
		setRegisterDate(registerDate);
		setUpdateDate(updateDate);
		setParameter(parameter);
		setLinkedKey(linkedKey);
		setVersion(version);
		setIconSrc(iconSrc);
		setIcon(icon);
		setUrl(url);
		setOpen(open);
		setMulti(multi);
		setAdmin(admin);
		setCheck(check);
		setParallel(parallel);
		setAlignment(alignment);
	}

	public String getType() {
		return getAttributeAsString("type");
	}
	public void setType(String type) {
		setAttribute("type", type);  
	}
	public String getModuleID() {
		return getAttributeAsString("moduleID");
	}
	public void setModuleID(String moduleID) {
		setAttribute("moduleID", moduleID);  
	}
	public String getOntologyID() {
		return getAttributeAsString("ontologyID");
	}
	public void setOntologyID(String ontologyID) {
		setAttribute("ontologyID", ontologyID);  
	}
	public String getAppFormat() {
		return getAttributeAsString("appFormat");
	}
	public void setAppFormat(String appFormat) {
		setAttribute("appFormat", appFormat);  
	}
	public String getLanguage() {
		return getAttributeAsString("language");
	}
	public void setLanguage(String language) {
		setAttribute("language", language);  
	}
	public String getScriptPath() {
		return getAttributeAsString("scriptPath");
	}
	public void setScriptPath(String scriptPath) {
		setAttribute("scriptPath", scriptPath);  
	}
	public String getCmd() {
		return getAttributeAsString("cmd");
	}
	public void setCmd(String cmd) {
		setAttribute("cmd", cmd);  
	}
	public String getModuleName() {
		return getAttributeAsString("moduleName");
	}
	public void setModuleName(String moduleName) {
		setAttribute("moduleName", moduleName);  
	}
	public String getModuleTreeName() {
		return getAttributeAsString("moduleTreeName");
	}
	public void setModuleTreeName(String moduleTreeName) {
		setAttribute("moduleTreeName", moduleTreeName);  
	}
	public String getModuleDesc() {
		return getAttributeAsString("moduleDesc");
	}
	public void setModuleDesc(String moduleDesc) {
		setAttribute("moduleDesc", moduleDesc);  
	}
	public String getModuleAuthor() {
		return getAttributeAsString("moduleAuthor");
	}
	public void setModuleAuthor(String moduleAuthor) {
		setAttribute("moduleAuthor", moduleAuthor);  
	}
	public String getRegisterDate() {
		return getAttributeAsString("registerDate");
	}
	public void setRegisterDate(String registerDate) {
		setAttribute("registerDate", registerDate);  
	}
	public String getUpdateDate() {
		return getAttributeAsString("updateDate");
	}
	public void setUpdateDate(String updateDate) {
		setAttribute("updateDate", updateDate);  
	}
	public String getParameter() {
		return getAttributeAsString("parameter");
	}
	public void setParameter(String parameter) {
		setAttribute("parameter", parameter);  
	}
	public String getLinkedKey() {
		return getAttributeAsString("linkedKey");
	}
	public void setLinkedKey(String linkedKey) {
		setAttribute("linkedKey", linkedKey);  
	}
	public String getVersion() {
		return getAttributeAsString("version");
	}
	public void setVersion(String version) {
		setAttribute("version", version);  
	}
	public String getIconSrc() {
		return getAttributeAsString("iconSrc");
	}
	public void setIconSrc(String iconSrc) {
		setAttribute("iconSrc", iconSrc);  
	}
	public String getIcon() {
		return getAttributeAsString("icon");
	}
	public void setIcon(String icon) {
		setAttribute("icon", icon);  
	}
	public String getUrl() {
		return getAttributeAsString("url");
	}
	public void setUrl(String url) {
		setAttribute("url", url);  
	}
	public boolean getOpen() {
		return getAttributeAsBoolean("open");
	}
	public void setOpen(boolean open) {
		setAttribute("open", open);  
	}
	public boolean getMulti() {
		return getAttributeAsBoolean("multi");
	}
	public void setMulti(boolean multi) {
		setAttribute("multi", multi);  
	}
	public boolean getAdmin() {
		return getAttributeAsBoolean("admin");
	}
	public void setAdmin(boolean admin) {
		setAttribute("admin", admin);  
	}
	public boolean getCheck() {
		return getAttributeAsBoolean("check");
	}
	public void setCheck(boolean check) {
		setAttribute("check", check);  
	}
	public boolean getParallel() {
		return getAttributeAsBoolean("parallel");
	}
	public void setParallel(boolean parallel) {
		setAttribute("parallel", parallel);  
	}
	public boolean getAlignment() {
		return getAttributeAsBoolean("alignment");
	}
	public void setAlignment(boolean alignment) {
		setAttribute("alignment", alignment);  
	}
}