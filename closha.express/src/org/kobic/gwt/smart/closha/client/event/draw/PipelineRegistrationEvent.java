package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class PipelineRegistrationEvent extends GwtEvent<PipelineRegistrationEventHandler>{

	public static Type<PipelineRegistrationEventHandler> TYPE = new Type<PipelineRegistrationEventHandler>();
	
	private String projectName;
	
	public String getProjectName(){
		return projectName;
	}
	
	public PipelineRegistrationEvent(String projectName){
		this.projectName = projectName;
	}

	@Override
	public Type<PipelineRegistrationEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PipelineRegistrationEventHandler handler) {
		// TODO Auto-generated method stub
		handler.pipelineRegistration(this);
	}

}
