package org.kobic.gwt.smart.closha.shared.model.module.register;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterModuleModel implements IsSerializable {

	private String type;
	private String moduleID;
	private String ontologyID;
	private String appFormat;
	private String language;
	private String scriptPath;
	private String cmd;
	private String moduleName;
	private String moduleDesc;
	private String moduleAuthor;
	private String registerDate;
	private String updateDate;
	private String parameter;
	private String linkedKey;
	private String version;
	private String icon;
	private String url;
	private boolean isOpen;
	private boolean isMulti;
	private boolean isAdmin;
	private boolean isCheck;
	private boolean isParallel;
	private boolean isAlignment;

	public RegisterModuleModel() {

	}

	public RegisterModuleModel(String type, String moduleID, String ontologyID,
			String appFormat, String language, String scriptPath, String cmd,
			String moduleName, String moduleDesc, String moduleAuthor,
			String registerDate, String updateDate, String parameter,
			String linkedKey, String version, String icon, String url,
			boolean isOpen, boolean isMulti, boolean isAdmin, boolean isCheck,
			boolean isParallel, boolean isAlignment) {

		this.type = type;
		this.moduleID = moduleID;
		this.ontologyID = ontologyID;
		this.appFormat = appFormat;
		this.language = language;
		this.scriptPath = scriptPath;
		this.cmd = cmd;
		this.moduleName = moduleName;
		this.moduleDesc = moduleDesc;
		this.moduleAuthor = moduleAuthor;
		this.registerDate = registerDate;
		this.updateDate = updateDate;
		this.parameter = parameter;
		this.linkedKey = linkedKey;
		this.version = version;
		this.icon = icon;
		this.url = url;
		this.isOpen = isOpen;
		this.isMulti = isMulti;
		this.isAdmin = isAdmin;
		this.isCheck = isCheck;
		this.isParallel = isParallel;
		this.isAlignment = isAlignment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getOntologyID() {
		return ontologyID;
	}

	public void setOntologyID(String ontologyID) {
		this.ontologyID = ontologyID;
	}

	public String getAppFormat() {
		return appFormat;
	}

	public void setAppFormat(String appFormat) {
		this.appFormat = appFormat;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleAuthor() {
		return moduleAuthor;
	}

	public void setModuleAuthor(String moduleAuthor) {
		this.moduleAuthor = moduleAuthor;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getLinkedKey() {
		return linkedKey;
	}

	public void setLinkedKey(String linkedKey) {
		this.linkedKey = linkedKey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isMulti() {
		return isMulti;
	}

	public void setMulti(boolean isMulti) {
		this.isMulti = isMulti;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public boolean isAlignment() {
		return isAlignment;
	}

	public void setAlignment(boolean isAlignment) {
		this.isAlignment = isAlignment;
	}

	@Override
	public String toString() {
		return "RegisterModuleModel [type=" + type + ", moduleID=" + moduleID
				+ ", ontologyID=" + ontologyID + ", appFormat=" + appFormat
				+ ", language=" + language + ", scriptPath=" + scriptPath
				+ ", cmd=" + cmd + ", moduleName=" + moduleName
				+ ", moduleDesc=" + moduleDesc + ", moduleAuthor="
				+ moduleAuthor + ", registerDate=" + registerDate
				+ ", updateDate=" + updateDate + ", parameter=" + parameter
				+ ", linkedKey=" + linkedKey + ", version=" + version
				+ ", icon=" + icon + ", url=" + url + ", isOpen=" + isOpen
				+ ", isMulti=" + isMulti + ", isAdmin=" + isAdmin
				+ ", isCheck=" + isCheck + ", isParallel=" + isParallel
				+ ", isAlignment=" + isAlignment + "]";
	}

}