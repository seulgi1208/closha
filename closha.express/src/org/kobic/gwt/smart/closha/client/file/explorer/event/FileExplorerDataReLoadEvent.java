package org.kobic.gwt.smart.closha.client.file.explorer.event;

import com.google.gwt.event.shared.GwtEvent;

public class FileExplorerDataReLoadEvent extends
		GwtEvent<FileExplorerDataReLoadEventHandler> {

	public static Type<FileExplorerDataReLoadEventHandler> TYPE = new Type<FileExplorerDataReLoadEventHandler>();

	public FileExplorerDataReLoadEvent() {
	}

	@Override
	public Type<FileExplorerDataReLoadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(FileExplorerDataReLoadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.fileExplorerDataReLoadEvent(this);
	}
}
