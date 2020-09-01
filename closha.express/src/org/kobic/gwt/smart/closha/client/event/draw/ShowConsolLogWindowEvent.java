package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ShowConsolLogWindowEvent extends GwtEvent<ShowConsolLogWindowEventHandler>{

	public static Type<ShowConsolLogWindowEventHandler> TYPE = new Type<ShowConsolLogWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ShowConsolLogWindowEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<ShowConsolLogWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowConsolLogWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showConsolLogViewer(this);
	}

}
