package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ShowHistoryWindowEvent extends GwtEvent<ShowHistoryWindowEventHandler>{

	public static Type<ShowHistoryWindowEventHandler> TYPE = new Type<ShowHistoryWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public ShowHistoryWindowEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<ShowHistoryWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowHistoryWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.showHistoryWindowEvent(this);
	}

}
