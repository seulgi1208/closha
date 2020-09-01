package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ToolBoxParameterWindowEvent extends GwtEvent<ToolBoxParameterWindowEventHandler>{

	public static Type<ToolBoxParameterWindowEventHandler> TYPE = new Type<ToolBoxParameterWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ToolBoxParameterWindowEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<ToolBoxParameterWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ToolBoxParameterWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.toolBoxParameterWindowEvent(this);
	}
}
