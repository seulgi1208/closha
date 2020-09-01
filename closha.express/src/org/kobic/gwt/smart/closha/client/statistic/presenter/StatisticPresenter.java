package org.kobic.gwt.smart.closha.client.statistic.presenter;

import org.kobic.gwt.smart.closha.client.monitoring.resource.presenter.ResourceMonitorPresenter;
import org.kobic.gwt.smart.closha.client.monitoring.resource.viewer.ResourceMonitorViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StatisticPresenter implements Presenter{

	private final HandlerManager eventBus;
	private final Display display;
	
	private Presenter resourceMoniteringPresenter;
	
	public interface Display{
		VLayout asLayout();
	}
	
	public StatisticPresenter(HandlerManager eventBus, Display view){
		this.eventBus = eventBus;
		this.display = view;
	}
	
	@Override
	public void bind(){
		
	}
	
	@Override
	public void go(Layout container) {
		// TODO Auto-generated method stub
		container.addMember(display.asLayout());
		
		resourceMoniteringPresenter =  new ResourceMonitorPresenter(eventBus, new ResourceMonitorViewer());
		resourceMoniteringPresenter.go(display.asLayout());
		
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		bind();
	}
}
