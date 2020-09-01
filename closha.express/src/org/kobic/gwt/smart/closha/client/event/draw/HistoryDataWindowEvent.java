package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class HistoryDataWindowEvent extends GwtEvent<HistoryDataWindowEventHandler>{

	public static Type<HistoryDataWindowEventHandler> TYPE = new Type<HistoryDataWindowEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public HistoryDataWindowEvent(String projectName){
		this.projectName = projectName;	
	}
	
	@Override
	public Type<HistoryDataWindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(HistoryDataWindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.historyDataWindowEvent(this);
	}

}
