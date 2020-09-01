package org.kobic.gwt.smart.closha.client.event.function;

import org.kobic.gwt.smart.closha.shared.model.design.PipelineModuleModel;

import com.google.gwt.event.shared.GwtEvent;

public class SelectModuleEvent extends GwtEvent<SelectModuleEventHandler>{

	public static Type<SelectModuleEventHandler> TYPE = new Type<SelectModuleEventHandler>();
	
	public PipelineModuleModel linkedNetworkNodeModel;
	
	public PipelineModuleModel getLinkedNetworkNodeModel() {
		return linkedNetworkNodeModel;
	}

	public SelectModuleEvent(PipelineModuleModel linkedNetworkNodeModel){
		this.linkedNetworkNodeModel = linkedNetworkNodeModel;
	}
	
	@Override
	public Type<SelectModuleEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SelectModuleEventHandler handler) {
		// TODO Auto-generated method stub
		handler.selectModule(this);
	}
}
