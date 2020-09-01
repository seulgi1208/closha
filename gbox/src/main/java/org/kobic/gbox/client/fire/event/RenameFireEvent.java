package org.kobic.gbox.client.fire.event;

import java.util.Map;

import com.pennychecker.eventbus.Event;

public class RenameFireEvent extends Event<RenameFireEventHandler> {

	public final static Type<RenameFireEventHandler> TYPE = new Type<RenameFireEventHandler>();

	private String target;
	private Map<String, String> path;

	public String getTarget() {
		return target;
	}

	public RenameFireEvent() {
		super();
	}
	
	public Map<String, String> getPath() {
		return path;
	}
	
	public RenameFireEvent(String target, Map<String, String> path) {
		this.target = target;
		this.path = path;
	}

	@Override
	protected void dispatch(RenameFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.rename(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<RenameFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
