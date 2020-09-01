package org.kobic.gwt.smart.closha.client.monitoring.resource.presenter;


import org.kobic.gwt.smart.closha.client.presenter.Presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class ResourceMonitorPresenter implements Presenter{

	@SuppressWarnings("unused")
	private final HandlerManager eventBus;
	private final Display display;
	
	public interface Display{
		HLayout asWidget();
	}
	
	public ResourceMonitorPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}

	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asWidget());
		bind();
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}
}
