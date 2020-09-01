package org.kobic.gwt.smart.closha.shared.model.file.explorer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CodeFileModel implements IsSerializable{
	
	private String code;
	private String fileName;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
