package org.kobic.gwt.smart.closha.client.event.registration;

import com.google.gwt.event.shared.GwtEvent;

public class SelectScriptFileEvent extends GwtEvent<SelectScriptFileEventHandler>{

	public static Type<SelectScriptFileEventHandler> TYPE = new Type<SelectScriptFileEventHandler>();
	
	private String formName;
	private String filePath;
	
	public String getFormName() {
		return formName;
	}

	public String getFilePath() {
		return filePath;
	}

	public SelectScriptFileEvent(String formName, String filePath){
		this.formName = formName;
		this.filePath = filePath;
	}
	
	
	@Override
	public Type<SelectScriptFileEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SelectScriptFileEventHandler handler) {
		// TODO Auto-generated method stub
		handler.selectScriptFileEvent(this);
	}
}
