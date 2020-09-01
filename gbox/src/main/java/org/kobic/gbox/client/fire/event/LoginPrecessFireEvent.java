package org.kobic.gbox.client.fire.event;

import org.kobic.sso.client.model.Member;

import com.pennychecker.eventbus.Event;

public class LoginPrecessFireEvent extends Event<LoginPrecessFireEventHandler> {

	public final static Type<LoginPrecessFireEventHandler> TYPE = new Type<LoginPrecessFireEventHandler>();

	private String frameId;	
	private Member member;
	
	public Member getMember(){
		return member;
	}
	
	public String getFrameId(){
		return frameId;
	}
	
	public LoginPrecessFireEvent(String id, Member member) {
		super();
		this.frameId = id;
		this.member = member;
	}

	@Override
	protected void dispatch(LoginPrecessFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.loginFireEvent(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<LoginPrecessFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
