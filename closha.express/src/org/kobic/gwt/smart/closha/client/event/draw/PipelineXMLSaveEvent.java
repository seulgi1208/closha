package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class PipelineXMLSaveEvent extends GwtEvent<PipelineXMLSaveEventHandler>{

	public static Type<PipelineXMLSaveEventHandler> TYPE = new Type<PipelineXMLSaveEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public PipelineXMLSaveEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<PipelineXMLSaveEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(PipelineXMLSaveEventHandler handler) {
		// TODO Auto-generated method stub
		handler.saveXMLData(this);
	}

}
