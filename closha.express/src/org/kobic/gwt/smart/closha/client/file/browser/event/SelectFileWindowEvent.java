package org.kobic.gwt.smart.closha.client.file.browser.event;

import com.google.gwt.event.shared.GwtEvent;

public class SelectFileWindowEvent extends GwtEvent<SelectFileWindowEventHandler>{

	public static Type<SelectFileWindowEventHandler> TYPE = new Type<SelectFileWindowEventHandler>();
	
	private String formName;
	private String fileName;
	private String filePath;
	private String parameterType;
	private String key;
	private String parameterKey;
	
	public String getFormName(){
		return formName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public String getParameterType() {
		return parameterType;
	}
	
	public String getParameterKey() {
		return parameterKey;
	}
	
	public String getKey() {
		return key;
	}
	
	public SelectFileWindowEvent(String formName, String fileName, String filePath, String parameterType, String parameterKey, String key){
		this.formName = formName;
		this.fileName = fileName;
		this.filePath = filePath;
		this.parameterType = parameterType;
		this.parameterKey = parameterKey;
		this.key = key;
	}
	
	@Override
	public Type<SelectFileWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SelectFileWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.selectFileWindowEvent(this);
	}

}
