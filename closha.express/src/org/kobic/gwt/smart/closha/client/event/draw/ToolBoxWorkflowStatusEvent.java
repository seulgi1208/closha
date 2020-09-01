package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ToolBoxWorkflowStatusEvent extends GwtEvent<ToolBoxWorkflowStatusEventHandler>{

	public static Type<ToolBoxWorkflowStatusEventHandler> TYPE = new Type<ToolBoxWorkflowStatusEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ToolBoxWorkflowStatusEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<ToolBoxWorkflowStatusEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ToolBoxWorkflowStatusEventHandler handler) {
		// TODO Auto-generated method stub
		handler.toolBoxWorkflowStatusEvent(this);
	}
}
