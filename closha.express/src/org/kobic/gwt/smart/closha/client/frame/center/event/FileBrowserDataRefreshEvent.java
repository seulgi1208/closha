package org.kobic.gwt.smart.closha.client.frame.center.event;

import com.google.gwt.event.shared.GwtEvent;

public class FileBrowserDataRefreshEvent extends GwtEvent<FileBrowserDataRefreshEventHandler>{

	public static Type<FileBrowserDataRefreshEventHandler> TYPE = new Type<FileBrowserDataRefreshEventHandler>();
	
	private String projectName;
	
	public String getProjectName(){
		return projectName;
	}
	
	public FileBrowserDataRefreshEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<FileBrowserDataRefreshEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileBrowserDataRefreshEventHandler handler) {
		// TODO Auto-generated method stub
		handler.dataRefresh(this);
	}

}
