package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ExecuteProjectEvent extends GwtEvent<ExecuteProjectEventHandler>{

	public static Type<ExecuteProjectEventHandler> TYPE = new Type<ExecuteProjectEventHandler>();
	
	private String projectName;
	private int status;

	public String getProjectName(){
		return projectName;
	}
	
	public ExecuteProjectEvent(String projectName, int status){
		this.projectName = projectName;
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	@Override
	public Type<ExecuteProjectEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ExecuteProjectEventHandler handler) {
		// TODO Auto-generated method stub
		handler.executeProjectEvent(this);
	}

}
