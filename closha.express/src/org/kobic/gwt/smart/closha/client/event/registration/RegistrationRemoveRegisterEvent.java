package org.kobic.gwt.smart.closha.client.event.registration;

import com.google.gwt.event.shared.GwtEvent;

public class RegistrationRemoveRegisterEvent extends GwtEvent<RegistrationRemoveRegisterEventHandler>{

	public static Type<RegistrationRemoveRegisterEventHandler> TYPE = new Type<RegistrationRemoveRegisterEventHandler>();
	
	@Override
	public Type<RegistrationRemoveRegisterEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(RegistrationRemoveRegisterEventHandler handler) {
		// TODO Auto-generated method stub
		handler.registrationRemoveEvent(this);
	}
}
