package org.kobic.gwt.smart.closha.client.frame.header.presenter;

import org.kobic.gwt.smart.closha.client.frame.center.event.ShowIntroTabEvent;
import org.kobic.gwt.smart.closha.client.login.event.LoginViewerEvent;
import org.kobic.gwt.smart.closha.client.login.event.LoginViewerEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class HeaderPresenter implements Presenter{
	
	private final HandlerManager eventBus;
	private final Display display;
	public static UserDto userDto;
	
	private boolean is_login = false;
	
	public interface Display{
		HLayout getHeader();
		Img getImg();
	}
	
	public HeaderPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}
	
	@Override
	public void init(){
		bind();
	}

	@Override
	public void bind(){
		loginFireEvent();
		logoFireEvent();
	}
	
	private void logoFireEvent(){
		display.getImg().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				System.out.println(is_login);
				
				if(is_login){
					eventBus.fireEvent(new ShowIntroTabEvent());
				}
			}
		});
	}
	
	private void loginFireEvent(){
		eventBus.addHandler(LoginViewerEvent.TYPE, new LoginViewerEventHandler() {
			@Override
			public void loginViewerEvent(LoginViewerEvent event) {
				// TODO Auto-generated method stub
				userDto = event.getUserDto();
				is_login = true;
			} 
		});
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		init();
		container.addMember(display.getHeader());
	}
}