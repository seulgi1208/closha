package org.kobic.gwt.smart.closha.client.frame.main.presenter;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEvent;
import org.kobic.gwt.smart.closha.client.common.event.CloseWindowEventHandler;
import org.kobic.gwt.smart.closha.client.frame.center.presenter.CenterPresenter;
import org.kobic.gwt.smart.closha.client.frame.center.viewer.CenterViewer;
import org.kobic.gwt.smart.closha.client.frame.left.presenter.LeftPresenter;
import org.kobic.gwt.smart.closha.client.frame.left.viewer.LeftViewer;
import org.kobic.gwt.smart.closha.client.frame.right.presenter.RightPresenter;
import org.kobic.gwt.smart.closha.client.frame.right.viewer.RightViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.model.login.UserDto;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;

public class MainPresenter implements Presenter{
	
	private final HandlerManager eventBus;
	private final Display display;
	private Presenter leftPresenter;
	private Presenter centerPresenter;
	private Presenter rightPresenter;
	private UserDto userDto;
	private Map<String, String> config;

	public interface Display{
		HLayout asWidget();
		VLayout getLeftPanel();
		VLayout getCenterPanel();
		VLayout getRightPanel();
	}
	
	public MainPresenter(HandlerManager eventBus, Map<String, String> config, UserDto userDto, Display view){
		this.eventBus = eventBus;
		this.display = view;
		this.userDto = userDto;
		this.config = config;
	}
	
	@Override
	public void bind(){
		addFileEventListener();
	}
	
	private void addFileEventListener(){
		eventBus.addHandler(CloseWindowEvent.TYPE, new CloseWindowEventHandler() {
			@Override
			public void closeLoginWinodw(CloseWindowEvent event) {
				// TODO Auto-generated method stub
				display.asWidget().destroy();
			}
		});
	}
	
	@Override
	public void go(final Layout container) {
		// TODO Auto-generated method stub	
		
		leftPresenter = new LeftPresenter(eventBus, config, userDto, new LeftViewer(userDto.getUserName()));
		leftPresenter.go(display.getLeftPanel());
		
		RemoteEventService theRemoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
		
		centerPresenter = new CenterPresenter(eventBus, config, userDto, theRemoteEventService, new CenterViewer());
		centerPresenter.go(display.getCenterPanel());
		
		rightPresenter = new RightPresenter(eventBus, config, userDto, new RightViewer());
		rightPresenter.go(display.getRightPanel());
		
		container.addMember(display.asWidget());
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bind();
	}
}