package org.kobic.gwt.smart.closha.client.register.user.presenter;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEvent;
import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEventHandler;
import org.kobic.gwt.smart.closha.client.file.explorer.controller.HadoopFileSystemController;
import org.kobic.gwt.smart.closha.client.login.controller.LoginController;
import org.kobic.gwt.smart.closha.client.login.event.LoginViewerEvent;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.user.controller.UserController;
import org.kobic.gwt.smart.closha.shared.Constants;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class RegisterPresenter implements Presenter {
	
	public interface Display{
		HLayout asWidget();
		ButtonItem getCheckButton();
		IButton getRegisterButton();
		DynamicForm getRegisterForm();
		Window getRegisterWindow();
	}
	
	private final HandlerManager eventBus;
	private final Display display;
	private Map<String, String> config;
	
	public RegisterPresenter(HandlerManager eventBus, Map<String, String> config, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.config = config;
	}
	
	@Override
	public void init(){
		bind();
	}
	
	@Override
	public void bind(){
		idCheckButtonEvent();
		registerButtonEvent();
		addFireEventListener();
		closeRegisterWindowEvent();
	}
	
	private void idCheckButtonEvent(){
		display.getCheckButton().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				// TODO Auto-generated method stub
				
				String id = display.getRegisterForm().getValueAsString("id");
				
				if(id != null){
					UserController.Util.getInstance().isUserId(id, new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getCause() + ":" + caught.getMessage());
						}
						@Override
						public void onSuccess(Boolean exist) {
							// TODO Auto-generated method stub
							if(exist){
								SC.say("ID is available.");
								display.getRegisterForm().getField("id").disable();
								display.getCheckButton().disable();
							}else{
								SC.warn("The ID you have entered already exists.");
								display.getRegisterForm().getField("id").focusInItem();
							}
						}
					});
				}else{
					SC.say("Enter your ID.");
				}
			}
		});
	}
	
	private void makeUserHomeFolder(final UserDto userDto){
		
		HadoopFileSystemController.Util.getInstance().makeHomeDirectory(config, userDto.getUserId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

				SC.confirm("User registration completed", new BooleanCallback() {
					@Override
					public void execute(Boolean allow) {
						// TODO Auto-generated method stub
						display.asWidget().destroy();
						
						if(allow == null){
							History.newItem(Constants.LOGIN_WINDOW_ID);
						}else{
							
							LoginController.Util.getInstance().login(config, userDto.getUserId(), userDto.getPassword(), new AsyncCallback<UserDto>() {
								@Override
								public void onSuccess(UserDto userDto) {
									// TODO Auto-generated method stub
									if(userDto != null){
										eventBus.fireEvent(new LoginViewerEvent(userDto));
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
		});
	}
	
	private void registerButtonEvent(){
		display.getRegisterButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(display.getRegisterForm().validate()){
					
					if(display.getCheckButton().isDisabled()){
						
						final UserDto userDto = new UserDto();
						userDto.setUserName(display.getRegisterForm().getValueAsString("name"));
						userDto.setUserId(display.getRegisterForm().getValueAsString("id"));
						userDto.setPassword(display.getRegisterForm().getValueAsString("password"));
						userDto.setOrganization(display.getRegisterForm().getValueAsString("company"));
						userDto.setPosition(display.getRegisterForm().getValueAsString("position"));
						userDto.setEmailAdress(display.getRegisterForm().getValueAsString("email"));
						userDto.setTel(display.getRegisterForm().getValueAsString("phone"));
						userDto.setHp(display.getRegisterForm().getValueAsString("mobile"));
						userDto.setFax(display.getRegisterForm().getValueAsString("fax"));
						
						if(userDto.getPassword().length() >= 7 && userDto.getPassword().length() <= 13){
							
							UserController.Util.getInstance().registration(userDto, new AsyncCallback<String>() {
								@Override
								public void onSuccess(String userId) {
									// TODO Auto-generated method stub
									makeUserHomeFolder(userDto);
								}
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									System.out.println(caught.getCause() + ":" + caught.getMessage());
								}
							});
						}else{
							SC.warn("Please check your password.");
						}
					}else{
						SC.warn("Please check your ID.");
					}
				}else{
					SC.warn("Please check your information again.");
				}
			}
		});
	}
	
	private void addFireEventListener(){
		eventBus.addHandler(CloseWindowEvent.TYPE, new CloseWindowEventHandler(){
			@Override
			public void closeLoginWinodw(CloseWindowEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();
			}
		});
	}
	
	private void closeRegisterWindowEvent(){
		display.getRegisterWindow().addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();
				History.newItem(Constants.LOGIN_WINDOW_ID);
			}
		});
	}
	
	@Override
	public void go(Layout container){
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
}
