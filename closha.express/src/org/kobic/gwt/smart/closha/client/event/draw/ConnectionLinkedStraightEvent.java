package org.kobic.gwt.smart.closha.client.event.draw;

import com.google.gwt.event.shared.GwtEvent;

public class ConnectionLinkedStraightEvent extends GwtEvent<ConnectionLinkedStraightEventHandler>{

	public static Type<ConnectionLinkedStraightEventHandler> TYPE = new Type<ConnectionLinkedStraightEventHandler>();
	
	private String projectName;	
	private String startKey;
	private String endKey;
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getStartKey(){
		return startKey;
	}
	
	public String getEndKey(){
		return endKey;
	}
	
	public ConnectionLinkedStraightEvent(String projectName, String startKey, String endKey){
		this.projectName = projectName;
		this.startKey = startKey;
		this.endKey = endKey;
	}
	
	@Override
	public Type<ConnectionLinkedStraightEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ConnectionLinkedStraightEventHandler handler) {
		// TODO Auto-generated method stub
		handler.linkedConnectStraightEvent(this);
	}

}
