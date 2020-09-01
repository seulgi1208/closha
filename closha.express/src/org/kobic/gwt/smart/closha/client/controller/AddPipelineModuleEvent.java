package org.kobic.gwt.smart.closha.client.controller;

import org.kobic.gwt.smart.closha.shared.model.design.XmlModuleModel;

import com.google.gwt.event.shared.GwtEvent;

public class AddPipelineModuleEvent extends GwtEvent<AddPipelineModuleEventHandler>{

	public static Type<AddPipelineModuleEventHandler> TYPE = new Type<AddPipelineModuleEventHandler>(); 
	
	private XmlModuleModel xmlModuleModel;
	private String projectName;
	
	public XmlModuleModel getModuleModel(){
		return xmlModuleModel;
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public AddPipelineModuleEvent(XmlModuleModel xmlModuleModel, String projectName){
		this.xmlModuleModel = xmlModuleModel;
		this.projectName = projectName;
	}

	@Override
	public Type<AddPipelineModuleEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(AddPipelineModuleEventHandler handler) {
		// TODO Auto-generated method stub
		handler.addPipelineModule(this);
	}

}
