package org.kobic.gwt.smart.closha.client.event.draw;

import org.kobic.gwt.smart.closha.shared.model.design.IntegrationModuleConnectModel;

import com.google.gwt.event.shared.GwtEvent;

public class ShowConnectionWindowEvent extends GwtEvent<ShowConnectionWindowEventHandler>{

	public static Type<ShowConnectionWindowEventHandler> TYPE = new Type<ShowConnectionWindowEventHandler>();
	
	private IntegrationModuleConnectModel integrationLinkedModel;
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public IntegrationModuleConnectModel getIntegrationLinkedModel(){
		return integrationLinkedModel;
	}
	
	public ShowConnectionWindowEvent(String projectName, IntegrationModuleConnectModel integrationLinkedModel){
		this.projectName = projectName;
		this.integrationLinkedModel = integrationLinkedModel;
	}
	
	@Override
	public Type<ShowConnectionWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowConnectionWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showConnectionWindow(this);
	}

}
