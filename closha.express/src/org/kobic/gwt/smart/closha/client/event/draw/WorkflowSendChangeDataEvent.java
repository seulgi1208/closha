package org.kobic.gwt.smart.closha.client.event.draw;

import org.kobic.gwt.smart.closha.shared.model.design.XmlDispatchModel;

import com.google.gwt.event.shared.GwtEvent;

public class WorkflowSendChangeDataEvent extends GwtEvent<WorkflowSendChangeDataEventHandler>{

	public static Type<WorkflowSendChangeDataEventHandler> TYPE = new Type<WorkflowSendChangeDataEventHandler>();
	
	private String projectName;	
	private XmlDispatchModel xmlDispatchModel;
	
	public String getProjectName(){
		return projectName;
	}
	
	public XmlDispatchModel getXmlDispatchModel() {
		return xmlDispatchModel;
	}

	public WorkflowSendChangeDataEvent(String projectName, XmlDispatchModel xmlDispatchModel){
		this.projectName = projectName;
		this.xmlDispatchModel = xmlDispatchModel;
	}
	
	@Override
	public Type<WorkflowSendChangeDataEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(WorkflowSendChangeDataEventHandler handler) {
		// TODO Auto-generated method stub
		handler.workflowSendChangeData(this);
	}

}
