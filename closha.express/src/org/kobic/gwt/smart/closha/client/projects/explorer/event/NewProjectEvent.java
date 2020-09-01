package org.kobic.gwt.smart.closha.client.projects.explorer.event;

import org.kobic.gwt.smart.closha.shared.model.pipeline.instance.InstancePipelineModel;

import com.google.gwt.event.shared.GwtEvent;

public class NewProjectEvent extends GwtEvent<NewProjectEventHandler>{

	public static Type<NewProjectEventHandler> TYPE = new Type<NewProjectEventHandler>();
	
	private InstancePipelineModel instancePipelineModel;
	
	public InstancePipelineModel getInstancePipelineModel(){
		return instancePipelineModel;
	}
	
	public NewProjectEvent(InstancePipelineModel instancePipelineModel){
		this.instancePipelineModel = instancePipelineModel;
	}
	
	@Override
	public Type<NewProjectEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(NewProjectEventHandler handler) {
		// TODO Auto-generated method stub
		handler.makeNewProject(this);
	}

}
