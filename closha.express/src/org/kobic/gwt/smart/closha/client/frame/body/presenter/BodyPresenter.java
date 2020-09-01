package org.kobic.gwt.smart.closha.client.frame.body.presenter;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEvent;
import org.kobic.gwt.smart.closha.client.frame.header.presenter.HeaderPresenter;
import org.kobic.gwt.smart.closha.client.frame.main.presenter.MainPresenter;
import org.kobic.gwt.smart.closha.client.frame.main.viewer.MainViewer;
import org.kobic.gwt.smart.closha.client.login.presenter.LoginPresenter;
import org.kobic.gwt.smart.closha.client.login.viewer.LoginViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.register.agreement.presenter.AgreementPresenter;
import org.kobic.gwt.smart.closha.client.register.agreement.viewer.AgreementViewer;
import org.kobic.gwt.smart.closha.client.register.user.presenter.RegisterPresenter;
import org.kobic.gwt.smart.closha.client.register.user.viewer.RegisterViewer;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class BodyPresenter implements Presenter {
	
	private final HandlerManager eventBus;
	private final Display display;
	private Map<String, String> config;
	
	public interface Display{
		VLayout asWidget();
	}

	public BodyPresenter(HandlerManager eventBus, Map<String, String> config, Display display){
		this.eventBus = eventBus;
		this.config = config;
		this.display = display;
	}
 
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		init();
	}
	
	public void setFrame(final String type){
		
		eventBus.fireEvent(new CloseWindowEvent());
		
		Presenter presenter = null;
		
		if(type != null && type.equals(Constants.REGISTER_WINDOW_ID)){
			
			presenter = new RegisterPresenter(eventBus, config, new RegisterViewer());
			
		}else if(type != null && type.equals(Constants.LOGIN_WINDOW_ID)){
			
			presenter = new LoginPresenter(eventBus, config, new LoginViewer());
			
		}else if(type != null && type.equals(Constants.AGREEMENT_WINDOW_ID)){
			
			presenter = new AgreementPresenter(eventBus, new AgreementViewer());
			
		}else if(type != null && type.equals(Constants.CLOSHA_WINDOW_ID)){
			
			if(HeaderPresenter.userDto != null){
				presenter = new MainPresenter(eventBus, config, HeaderPresenter.userDto, new MainViewer());
			}else{
				presenter = new LoginPresenter(eventBus, config, new LoginViewer());
			}
			
		}else {
			
			presenter = new LoginPresenter(eventBus, config, new LoginViewer());
			
		}
		
		if(presenter != null) presenter.go(display.asWidget());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setFrame(null);
		bind();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}
}