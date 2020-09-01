package org.kobic.gwt.smart.closha.client.projects.explorer.event;

import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.GwtEvent;

public class OpenProjectEvent extends GwtEvent<OpenProjectEventHandler>{
	
	private String userID;
	private InstancePipelineModel instancePipelineModel;
	
	public static Type<OpenProjectEventHandler> TYPE = new Type<OpenProjectEventHandler>();
	
	public String getUserID(){
		return userID;
	}
	
	public InstancePipelineModel getInstancePipelineModel(){
		return instancePipelineModel;
	}
	
	public OpenProjectEvent(String userID, InstancePipelineModel instancePipelineModel){
		this.userID = userID;
		this.instancePipelineModel = instancePipelineModel;
	}

	@Override
	public Type<OpenProjectEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(OpenProjectEventHandler handler) {
		// TODO Auto-generated method stub
		handler.getOpenUserProject(this);
	}

}
