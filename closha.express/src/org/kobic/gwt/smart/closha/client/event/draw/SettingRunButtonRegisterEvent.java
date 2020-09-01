package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class SettingRunButtonRegisterEvent extends GwtEvent<SettingRunButtonRegisterHandler>{

	public static Type<SettingRunButtonRegisterHandler> TYPE = new Type<SettingRunButtonRegisterHandler>();
	
	private String projectName;	
	private int status;
	
	public int getStatus() {
		return status;
	}

	public String getProjectName(){
		return projectName;
	}
	
	public SettingRunButtonRegisterEvent(String projectName, int status){
		this.projectName = projectName;
		this.status = status;
	}
	
	@Override
	public Type<SettingRunButtonRegisterHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SettingRunButtonRegisterHandler handler) {
		// TODO Auto-generated method stub
		handler.settingRegisterRunEventHandler(this);
	}

}
