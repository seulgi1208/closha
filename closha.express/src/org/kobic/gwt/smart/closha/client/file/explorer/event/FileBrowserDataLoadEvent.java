package org.kobic.gwt.smart.closha.client.file.explorer.event;

import com.google.gwt.event.shared.GwtEvent;

public class FileBrowserDataLoadEvent extends GwtEvent<FileBrowserDataLoadEventHandler>{

	public static Type<FileBrowserDataLoadEventHandler> TYPE = new Type<FileBrowserDataLoadEventHandler>(); 
	
	private String filePath;
	
	public String getFilePath(){
		return filePath;
	}
	
	public FileBrowserDataLoadEvent(String filePath){
		this.filePath = filePath;
	}

	@Override
	public Type<FileBrowserDataLoadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileBrowserDataLoadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.selectFileInputPathSetting(this);
	}
}
