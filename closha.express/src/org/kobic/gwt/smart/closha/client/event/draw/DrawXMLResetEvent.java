package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class DrawXMLResetEvent extends GwtEvent<DrawXMLResetEventHandler>{

	public static Type<DrawXMLResetEventHandler> TYPE = new Type<DrawXMLResetEventHandler>();
	
	private String projectName;	
	
	public String getProjectName(){
		return projectName;
	}
	
	public DrawXMLResetEvent(String projectName){
		this.projectName = projectName;
	}
	
	@Override
	public Type<DrawXMLResetEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DrawXMLResetEventHandler handler) {
		// TODO Auto-generated method stub
		handler.drawXMLResetEvent(this);
	}

}
