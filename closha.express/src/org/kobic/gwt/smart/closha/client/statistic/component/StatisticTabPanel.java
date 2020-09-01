package org.kobic.gwt.smart.closha.client.statistic.component;

import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.client.statistic.presenter.StatisticPresenter;
import org.kobic.gwt.smart.closha.client.statistic.viewer.StatisticViewer;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class StatisticTabPanel extends Tab{
	
	private Presenter systemMonitorPresenter;
	
	public StatisticTabPanel(HandlerManager evnetBus){
		
		setID(Constants.MONITOR_WINDOW_ID);
		setTitle(Constants.MONITOR_WINDOW_TITLE);
		setIcon("closha/icon/chart_bar.gif");
		setWidth(200);
		
		HLayout systemLayout = new HLayout();
		systemLayout.setBorder("solid 1px #DCDCDC");
		systemLayout.setHeight100();
		systemLayout.setWidth100();
		systemLayout.setAlign(Alignment.CENTER);
		
		systemMonitorPresenter = new StatisticPresenter(evnetBus, new StatisticViewer());
		systemMonitorPresenter.go(systemLayout);
		
		setPane(systemLayout);
	}

}
	