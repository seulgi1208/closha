package org.kobic.gwt.smart.closha.client.projects.explorer.event;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectExplorerDataLoadEvent extends GwtEvent<ProjectExplorerDataLoadEventHandler>{

	public static Type<ProjectExplorerDataLoadEventHandler> TYPE = new Type<ProjectExplorerDataLoadEventHandler>();
	
	@Override
	public Type<ProjectExplorerDataLoadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ProjectExplorerDataLoadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.projectExplorerDataReload(this);
	}

}
