package org.kobic.gwt.smart.closha.client.frame.right.event;

import com.google.gwt.event.shared.GwtEvent;

public class PipelineOntologyDataEvent extends GwtEvent<PipelineOntologyDataEventHandler>{

	public static Type<PipelineOntologyDataEventHandler> TYPE = new Type<PipelineOntologyDataEventHandler>();
	
	@Override
	public Type<PipelineOntologyDataEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PipelineOntologyDataEventHandler handler) {
		// TODO Auto-generated method stub
		handler.pipelineOntologyDataReload(this);
	}
}
