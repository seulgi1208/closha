package org.kobic.gwt.smart.closha.client.section.job.history.viewer;

import org.kobic.gwt.smart.closha.client.common.component.VLayoutWidget;
import org.kobic.gwt.smart.closha.client.section.job.history.presenter.JobHistoryPresenter;

import com.smartgwt.client.widgets.layout.VLayout;

public class JobHistoryViewer extends VLayoutWidget implements
		JobHistoryPresenter.Display {

	public JobHistoryViewer() {
		setMargin(3);
	}

	@Override
	public VLayout asLayout() {
		// TODO Auto-generated method stub
		return this;
	}
}
