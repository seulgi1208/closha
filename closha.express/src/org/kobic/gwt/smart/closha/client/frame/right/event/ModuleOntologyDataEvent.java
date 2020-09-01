package org.kobic.gwt.smart.closha.client.frame.right.event;

import com.google.gwt.event.shared.GwtEvent;

public class ModuleOntologyDataEvent extends GwtEvent<ModuleOntologyDataEventHandler>{

	public static Type<ModuleOntologyDataEventHandler> TYPE = new Type<ModuleOntologyDataEventHandler>();
	
	@Override
	public Type<ModuleOntologyDataEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ModuleOntologyDataEventHandler handler) {
		// TODO Auto-generated method stub
		handler.moduleOntologyDataReload(this);
	}
}
