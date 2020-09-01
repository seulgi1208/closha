package org.kobic.gwt.smart.closha.client.event.draw;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteModuleEvent extends GwtEvent<DeleteModuleEventHandler>{

	public static Type<DeleteModuleEventHandler> TYPE = new Type<DeleteModuleEventHandler>();
	
	private List<String> key;
	
	public List<String> getKey(){
		return key;
	}
	
	public DeleteModuleEvent(List<String> key){
		this.key = key;
	}
	
	@Override
	public Type<DeleteModuleEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteModuleEventHandler handler) {
		// TODO Auto-generated method stub
		handler.deleteModuleEvent(this);
	}

}
