package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class GWTPipelineXMLDataSaveEvent extends GwtEvent<GWTPipelineXMLDataSaveEventHandler>{


	public static Type<GWTPipelineXMLDataSaveEventHandler> TYPE = new Type<GWTPipelineXMLDataSaveEventHandler>();
	
	@Override
	public Type<GWTPipelineXMLDataSaveEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(GWTPipelineXMLDataSaveEventHandler handler) {
		// TODO Auto-generated method stub
		handler.savePipelineXMLData(this);
	}	
}
