package org.kobic.gwt.smart.closha.client.monitoring.job.component;

import org.kobic.gwt.smart.closha.client.monitoring.job.presenter.JobMonitorPresenter;
import org.kobic.gwt.smart.closha.client.monitoring.job.viewer.JobMonitorViewer;
import org.kobic.gwt.smart.closha.client.presenter.Presenter;
import org.kobic.gwt.smart.closha.shared.Constants;

import com.google.gwt.event.shared.HandlerManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class JobMonitorTabPanel extends Tab{
	
	private Presenter SGEMonitorPresenter;
	
	public JobMonitorTabPanel(HandlerManager evnetBus){
		
		setID(Constants.JOB_WINDOW_ID);
		setTitle(Constants.JOB_WINDOW_TITLE);
		setIcon("closha/icon/monitor.png");
		setWidth(200);
		
		HLayout vmLayout = new HLayout();
		vmLayout.setBorder("solid 1px #DCDCDC");
		vmLayout.setHeight100();
		vmLayout.setWidth100();
		vmLayout.setAlign(Alignment.CENTER);
		
		SGEMonitorPresenter = new JobMonitorPresenter(evnetBus, new JobMonitorViewer());
		SGEMonitorPresenter.go(vmLayout);
		
		setPane(vmLayout);
	}
}
