package org.kobic.gwt.smart.closha.client.event.draw;

import org.kobic.gwt.smart.closha.shared.model.design.IntegrationModuleConnectModel;

import com.google.gwt.event.shared.GwtEvent;

public class ReloadConnectionWindowEvent extends GwtEvent<ReloadConnectionWindowEventHandler>{

	public static Type<ReloadConnectionWindowEventHandler> TYPE = new Type<ReloadConnectionWindowEventHandler>();
	
	private IntegrationModuleConnectModel integrationLinkedModel;
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public IntegrationModuleConnectModel getIntegrationLinkedModel(){
		return integrationLinkedModel;
	}
	
	public ReloadConnectionWindowEvent(String projectName, IntegrationModuleConnectModel integrationLinkedModel){
		this.projectName = projectName;
		this.integrationLinkedModel = integrationLinkedModel;
	}
	
	@Override
	public Type<ReloadConnectionWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ReloadConnectionWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.reLoadConnectionWindowData(this);
	}

}
