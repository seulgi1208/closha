package org.kobic.gwt.smart.closha.shared.model.design;

import com.google.gwt.user.client.rpc.IsSerializable;

public class XmlModuleModel implements IsSerializable {

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
	private String upadteDate;
	private String parameter;
	private String parameterNumber;
	private String linkedKey;
	private String version;
	private String imgSrc;
	private int imgWidth;
	private int imgHeight;
	private int x;
	private int y;
	private String key;
	private String status;
	private boolean isTerminate;
	private String url;
	private boolean isOpen;
	private boolean isMulti;
	private boolean isAdmin;
	private boolean isCheck;
	private boolean isParallel;
	private boolean isAlignment;

	public XmlModuleModel() {

	}

	public XmlModuleModel(String type, String moduleID, String ontologyID,
			String appFormat, String language, String scriptPath, String cmd,
			String moduleName, String moduleDesc, String moduleAuthor,
			String registerDate, String updateDate, String parameter,
			String parameterNumber, String linkedKey, String version,
			String imgSrc, int imgWidth, int imgHeight, int x, int y,
			String key, String status, boolean isTerminate, String url,
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
		this.upadteDate = updateDate;
		this.parameter = parameter;
		this.parameterNumber = parameterNumber;
		this.linkedKey = linkedKey;
		this.version = version;
		this.imgSrc = imgSrc;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.x = x;
		this.y = y;
		this.key = key;
		this.status = status;
		this.isTerminate = isTerminate;
		this.url = url;
		this.isOpen = isOpen;
		this.isMulti = isMulti;
		this.isAdmin = isAdmin;
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

	public String getUpadteDate() {
		return upadteDate;
	}

	public void setUpadteDate(String upadteDate) {
		this.upadteDate = upadteDate;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getParameterNumber() {
		return parameterNumber;
	}

	public void setParameterNumber(String parameterNumber) {
		this.parameterNumber = parameterNumber;
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

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isTerminate() {
		return isTerminate;
	}

	public void setTerminate(boolean isTerminate) {
		this.isTerminate = isTerminate;
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

}
