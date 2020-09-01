package org.kobic.gwt.smart.closha.client.login.presenter;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEvent;
import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEventHandler;
import org.kobic.gwt.smart.closha.client.login.component.FindUserWindow;
import org.kobic.gwt.smart.closha.client.login.controller.LoginController;
import org.kobic.gwt.smart.closha.client.login.event.LoginViewerEvent;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class LoginPresenter implements Presenter{
	
	private HandlerManager eventBus;
	private Display display;
	private Map<String, String> config;
	
	public interface Display{
		HLayout asWidget();
		DynamicForm getLoginForm();
		ImgButton getLoginButton();
		ImgButton getForgetButton();
		ImgButton getRegisterButton();
	}
	
	public LoginPresenter(HandlerManager eventBus, Map<String, String> config, Display view){
		this.eventBus = eventBus;
		this.config = config;
		this.display = view;
	}

	@Override
	public void bind(){
		forgetPasswordLinkItemEvent();
		userRegisterLinkItemEvent();
		userLoginButtonEvent();
		addFireEventListener();
		userLoginKeyPressEvent();
	}
	
	private void addFireEventListener(){
		eventBus.addHandler(CloseWindowEvent.TYPE, new CloseWindowEventHandler() {
			@Override
			public void closeLoginWinodw(CloseWindowEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();
			}
		});
	}
	
	private void userLoginKeyPressEvent(){
		display.getLoginForm().addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				// TODO Auto-generated method stub
				
				 if (EventHandler.getKey().equalsIgnoreCase("Enter")){
					 
					 final String id = display.getLoginForm().getValueAsString("id");
						final String passwd = display.getLoginForm().getValueAsString("password");

						if(display.getLoginForm().validate()){
							LoginController.Util.getInstance().login(config, id, passwd, new AsyncCallback<UserDto>() {
								@Override
								public void onSuccess(UserDto userDto) {
									// TODO Auto-generated method stub
									if(userDto != null){
										eventBus.fireEvent(new LoginViewerEvent(userDto));
										display.asWidget().destroy();
										History.newItem(Constants.CLOSHA_WINDOW_ID);
									}
								}
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									SC.warn(caught.getMessage());
								}
							});
						}
					 
				 }
			}
		});
	}
	
	private void userLoginButtonEvent(){
		display.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				final String id = display.getLoginForm().getValueAsString("id");
				final String passwd = display.getLoginForm().getValueAsString("password");

				if(display.getLoginForm().validate()){
					LoginController.Util.getInstance().login(config, id, passwd, new AsyncCallback<UserDto>() {
						@Override
						public void onSuccess(UserDto userDto) {
							// TODO Auto-generated method stub
							if(userDto != null){
								eventBus.fireEvent(new LoginViewerEvent(userDto));
								display.asWidget().destroy();
								History.newItem(Constants.CLOSHA_WINDOW_ID);
							}
						}
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.warn(caught.getMessage());
						}
					});
				}
			}
		});
	}
	
	private void userRegisterLinkItemEvent(){
		display.getRegisterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();	
				History.newItem(Constants.REGISTER_WINDOW_ID);
			}
		});
	}
	
	private void forgetPasswordLinkItemEvent(){
		display.getForgetButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Window window = new FindUserWindow(config);
				window.show();
			}
		});
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bind();
	}
}