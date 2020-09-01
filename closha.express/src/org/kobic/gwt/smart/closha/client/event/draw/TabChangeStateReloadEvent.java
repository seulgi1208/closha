package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class TabChangeStateReloadEvent extends GwtEvent<TabChangeStateReloadEventHandler>{

	public static Type<TabChangeStateReloadEventHandler> TYPE = new Type<TabChangeStateReloadEventHandler>();
	
	private String projectName;
	private String instanceID;
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getInstanceID(){
		return instanceID;
	}

	public TabChangeStateReloadEvent(String projectName, String instanceID){
		this.projectName = projectName;
		this.instanceID = instanceID;
	}
	
	@Override
	public Type<TabChangeStateReloadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(TabChangeStateReloadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.tabChangeStateReloadEvent(this);
	}

}
