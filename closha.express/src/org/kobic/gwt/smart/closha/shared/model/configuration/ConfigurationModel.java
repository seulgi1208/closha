package org.kobic.gwt.smart.closha.shared.model.configuration;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigurationModel implements IsSerializable{
	
	private String rootPath;
	private String pythonPath;
	private String shellPath;
	private String javaPath;
	private String perlPath;
	private String rPath;
	private String mailUse;
	private String hostServer;
	private String authSelect;
	private String starttlsSelect;
	private String tlsSelect;
	private String mailId;
	private String mailPasswd;
	private String fromMailAdress;
	private String fromName;
	private String fromAddTo;
	
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getPythonPath() {
		return pythonPath;
	}
	public void setPythonPath(String pythonPath) {
		this.pythonPath = pythonPath;
	}
	public String getShellPath() {
		return shellPath;
	}
	public void setShellPath(String shellPath) {
		this.shellPath = shellPath;
	}
	public String getJavaPath() {
		return javaPath;
	}
	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}
	public String getPerlPath() {
		return perlPath;
	}
	public void setPerlPath(String perlPath) {
		this.perlPath = perlPath;
	}
	public String getrPath() {
		return rPath;
	}
	public void setrPath(String rPath) {
		this.rPath = rPath;
	}
	public String getMailUse() {
		return mailUse;
	}
	public void setMailUse(String mailUse) {
		this.mailUse = mailUse;
	}
	public String getHostServer() {
		return hostServer;
	}
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}
	public String getAuthSelect() {
		return authSelect;
	}
	public void setAuthSelect(String authSelect) {
		this.authSelect = authSelect;
	}
	public String getStarttlsSelect() {
		return starttlsSelect;
	}
	public void setStarttlsSelect(String starttlsSelect) {
		this.starttlsSelect = starttlsSelect;
	}
	public String getTlsSelect() {
		return tlsSelect;
	}
	public void setTlsSelect(String tlsSelect) {
		this.tlsSelect = tlsSelect;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getMailPasswd() {
		return mailPasswd;
	}
	public void setMailPasswd(String mailPasswd) {
		this.mailPasswd = mailPasswd;
	}
	public String getFromMailAdress() {
		return fromMailAdress;
	}
	public void setFromMailAdress(String fromMailAdress) {
		this.fromMailAdress = fromMailAdress;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromAddTo() {
		return fromAddTo;
	}
	public void setFromAddTo(String fromAddTo) {
		this.fromAddTo = fromAddTo;
	}
}