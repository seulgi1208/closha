package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class GetModuleInProjectEvent extends GwtEvent<GetModuleInProjectEventHandler>{

	public static Type<GetModuleInProjectEventHandler> TYPE = new Type<GetModuleInProjectEventHandler>();
	
	@Override
	public Type<GetModuleInProjectEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(GetModuleInProjectEventHandler handler) {
		// TODO Auto-generated method stub
		handler.getModuleInProject(this);
	}
}
