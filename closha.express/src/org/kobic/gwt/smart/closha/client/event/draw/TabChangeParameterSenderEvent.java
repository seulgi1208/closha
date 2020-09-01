package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class TabChangeParameterSenderEvent extends GwtEvent<TabChangeParameterSenderEventHandler>{

	public static Type<TabChangeParameterSenderEventHandler> TYPE = new Type<TabChangeParameterSenderEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public TabChangeParameterSenderEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<TabChangeParameterSenderEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(TabChangeParameterSenderEventHandler handler) {
		// TODO Auto-generated method stub
		handler.tabChangeParameterSender(this);
	}

}
