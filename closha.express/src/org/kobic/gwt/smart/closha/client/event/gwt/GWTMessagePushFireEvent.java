package org.kobic.gwt.smart.closha.client.event.gwt;

import org.kobic.gwt.smart.closha.shared.model.event.service.GWTMessagePushServiceModel;

import com.google.gwt.event.shared.GwtEvent;

public class GWTMessagePushFireEvent extends GwtEvent<GWTMessagePushFireEventHandler>{

	public static Type<GWTMessagePushFireEventHandler> TYPE = new Type<GWTMessagePushFireEventHandler>();
	
	private GWTMessagePushServiceModel gwtMessagePushServiceModel;
	
	public GWTMessagePushServiceModel getGwtMessagePushServiceModel() {
		return gwtMessagePushServiceModel;
	}
	
	public GWTMessagePushFireEvent(GWTMessagePushServiceModel gwtMessagePushServiceModel){
		this.gwtMessagePushServiceModel = gwtMessagePushServiceModel;
	}

	@Override
	public Type<GWTMessagePushFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(GWTMessagePushFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.gwtMessageFireEvent(this);
	}
}
