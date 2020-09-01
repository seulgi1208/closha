package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ExpertFunctionStraightEvent extends GwtEvent<ExpertFunctionStraightEventHandler>{

	public static Type<ExpertFunctionStraightEventHandler> TYPE = new Type<ExpertFunctionStraightEventHandler>();
	
	private String projectName;	
	private String key;
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getKey(){
		return key;
	}
	
	public ExpertFunctionStraightEvent(String projectName, String key){
		this.projectName = projectName;
		this.key = key;
	}
	
	@Override
	public Type<ExpertFunctionStraightEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ExpertFunctionStraightEventHandler handler) {
		// TODO Auto-generated method stub
		handler.expertFunctionStraightEvent(this);
	}
}
