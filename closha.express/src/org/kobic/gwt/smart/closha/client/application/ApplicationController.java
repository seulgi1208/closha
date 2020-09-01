package org.kobic.gwt.smart.closha.client.application;

import java.util.Map;

import org.kobic.gwt.smart.closha.client.frame.body.presenter.BodyPresenter;
import org.kobic.gwt.smart.closha.client.frame.body.viewer.BodyViewer;
import org.kobic.gwt.smart.closha.client.frame.header.presenter.HeaderPresenter;
import org.kobic.gwt.smart.closha.client.frame.header.viewer.HeaderViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.smartgwt.client.widgets.layout.Layout;

public class ApplicationController implements Presenter, ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	
	private Map<String, String> config;

	@SuppressWarnings("unused")
	private Layout container;

	private BodyPresenter bodyPresenter;
	private HeaderPresenter headerPresenter;

	public ApplicationController(HandlerManager eventBus, Map<String, String> config) {
		this.eventBus = eventBus;
		this.config = config;
		init();
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		String token = event.getValue();
		if(token.isEmpty()) token = null;
		bodyPresenter.setFrame(token);
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		this.container = container;
		
		if (!"".equals(History.getToken())) History.newItem("", false);
		
		headerPresenter.go(container);
		bodyPresenter.go(container); 
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		headerPresenter = new HeaderPresenter(eventBus, new HeaderViewer());
		bodyPresenter = new BodyPresenter(eventBus, config, new BodyViewer());
		bind();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		History.addValueChangeHandler(this);
	}
}
