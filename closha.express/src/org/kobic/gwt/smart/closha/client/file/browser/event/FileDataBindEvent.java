package org.kobic.gwt.smart.closha.client.file.browser.event;

import com.google.gwt.event.shared.GwtEvent;

public class FileDataBindEvent extends GwtEvent<FileDataBindEventHandler>{

	public static Type<FileDataBindEventHandler> TYPE = new Type<FileDataBindEventHandler>();
	
	@Override
	public Type<FileDataBindEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileDataBindEventHandler handler) {
		// TODO Auto-generated method stub
		handler.fileBrowserDataReLoad(this);
	}
}
