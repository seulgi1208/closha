package org.kobic.gwt.smart.closha.client.projects.explorer.event;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;

import com.google.gwt.event.shared.GwtEvent;

public class TransmitParameterDataEvent extends GwtEvent<TransmitParameterDataEventHandler>{

	public static Type<TransmitParameterDataEventHandler> TYPE = new Type<TransmitParameterDataEventHandler>();
	
	private String projectName;	
	private List<XmlParameterModel> parameters;
	
	public String getProjectName(){
		return projectName;
	}
	
	public List<XmlParameterModel> getParameters(){
		return parameters;
	}
	
	
	public TransmitParameterDataEvent(String projectName, List<XmlParameterModel> parameters){
		this.projectName = projectName;
		this.parameters = parameters;
	}
	
	@Override
	public Type<TransmitParameterDataEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(TransmitParameterDataEventHandler handler) {
		// TODO Auto-generated method stub
		handler.parameterDataSendEvent(this);
	}

}
