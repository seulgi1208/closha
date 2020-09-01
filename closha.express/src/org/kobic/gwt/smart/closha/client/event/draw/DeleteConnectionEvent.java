package org.kobic.gwt.smart.closha.client.event.draw;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteConnectionEvent extends GwtEvent<DeleteConnectionEventHandler>{

	public static Type<DeleteConnectionEventHandler> TYPE = new Type<DeleteConnectionEventHandler>();
	
	private List<String> key;
	
	public List<String> getKey(){
		return key;
	}
	
	public DeleteConnectionEvent(List<String> key){
		this.key = key;
	}
	
	@Override
	public Type<DeleteConnectionEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteConnectionEventHandler handler) {
		// TODO Auto-generated method stub
		handler.deleteConnectionEvent(this);
	}

}
