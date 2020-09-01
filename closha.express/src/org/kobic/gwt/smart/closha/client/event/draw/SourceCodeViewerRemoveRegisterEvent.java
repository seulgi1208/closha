package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class SourceCodeViewerRemoveRegisterEvent extends GwtEvent<SourceCodeViewerRemoveRegisterEventHandler>{

	public static Type<SourceCodeViewerRemoveRegisterEventHandler> TYPE = new Type<SourceCodeViewerRemoveRegisterEventHandler>();
	
	@Override
	public Type<SourceCodeViewerRemoveRegisterEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SourceCodeViewerRemoveRegisterEventHandler handler) {
		// TODO Auto-generated method stub
		handler.sourceCodeViewerRemoveRegister(this);
	}

}
