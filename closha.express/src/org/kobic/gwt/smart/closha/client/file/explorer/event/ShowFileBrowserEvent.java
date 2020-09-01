package org.kobic.gwt.smart.closha.client.file.explorer.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowFileBrowserEvent extends GwtEvent<ShowFileBrowserEventHandler>{

	public static Type<ShowFileBrowserEventHandler> TYPE = new Type<ShowFileBrowserEventHandler>();
	
	private boolean isLoad;
	private String path;
	
	public boolean isLoad() {
		return isLoad;
	}

	public String getPath() {
		return path;
	}

	public ShowFileBrowserEvent(boolean isLoad, String path){
		this.isLoad = isLoad;
		this.path = path;
	}
	
	@Override
	public Type<ShowFileBrowserEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ShowFileBrowserEventHandler handler) {
		// TODO Auto-generated method stub
		handler.focusOnFileBrowser(this);
	}
}
