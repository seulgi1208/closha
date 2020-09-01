package org.kobic.gbox.client.fire.event;

import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.Event;

public class PathPropertiesFireEvent extends Event<PathPropertiesFireEventHandler> {

	public final static Type<PathPropertiesFireEventHandler> TYPE = new Type<PathPropertiesFireEventHandler>();

	private String path;	
	private Member member;
	
	public Member getUserDto(){
		return member;
	}
	
	public String getPath(){
		return path;
	}
	
	public PathPropertiesFireEvent(String path, Member member) {
		super();
		this.path = path;
		this.member = member;
	}

	@Override
	protected void dispatch(PathPropertiesFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.getSelectPathProperties(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<PathPropertiesFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
