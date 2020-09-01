package org.kobic.gwt.smart.closha.client.event.draw;

import java.util.List;

import org.kobic.gwt.smart.closha.shared.model.design.XmlParameterModel;

import com.google.gwt.event.shared.GwtEvent;

public class ParameterSettingEvent extends GwtEvent<ParameterSettingEventHandler>{

	public static Type<ParameterSettingEventHandler> TYPE = new Type<ParameterSettingEventHandler>();
	
	private String projectName;	
	private List<XmlParameterModel> parameters;
	
	public String getProjectName(){
		return projectName;
	}
	
	public List<XmlParameterModel> getParameters(){
		return parameters;
	}
	
	public ParameterSettingEvent(String projectName, List<XmlParameterModel> parameters){
		this.projectName = projectName;
		this.parameters = parameters;
	}
	
	@Override
	public Type<ParameterSettingEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ParameterSettingEventHandler handler) {
		// TODO Auto-generated method stub
		handler.parameterSettingEvent(this);
	}

}
