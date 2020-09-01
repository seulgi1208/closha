package org.kobic.gwt.smart.closha.client.register.agreement.presenter;

import org.kobic.gwt.smart.closha.client.common.controller.CommonController;
import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEvent;
import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEventHandler;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class AgreementPresenter implements Presenter {
	
	public interface Display{
		HLayout asWidget();
		Window getAgreementWindow();
		TextAreaItem getServiceTerms();
		TextAreaItem getPolicy();
		CheckboxItem getAcceptCheckBox();
		ButtonItem getConfirmButton();
	}
	
	private final HandlerManager eventBus;
	private final Display display;
	
	
	public AgreementPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}
	
	@Override
	public void init(){
		bind();
	}
	
	@Override
	public void bind(){
		setAcceptCheckBoxEvent();
		setWindowCloseEvent();
		setConfirmButtonEvent();
		addFireEventListener();
		setPolicyContent();
		setServiceTermsContent();
	}
	
	private void setPolicyContent(){
		CommonController.Util.getInstance().getHTMLContents("agreement/policy.txt", new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				display.getPolicy().setValue(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
			}
		});
	}
	
	private void setServiceTermsContent(){
		CommonController.Util.getInstance().getHTMLContents("agreement/service_terms.txt", new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				display.getServiceTerms().setValue(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getCause() + ":" + caught.getMessage());
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
	
	private void setConfirmButtonEvent(){
		display.getConfirmButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();	
				History.newItem(Constants.REGISTER_WINDOW_ID);
			}
		});
	}
	
	private void setWindowCloseEvent(){
		display.getAgreementWindow().addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();
				History.newItem(Constants.LOGIN_WINDOW_ID);
			}
		});
	}
	
	private void setAcceptCheckBoxEvent(){
		display.getAcceptCheckBox().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				display.getConfirmButton().setDisabled(!((Boolean) event.getValue()));  
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
