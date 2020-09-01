package org.kobic.gwt.smart.closha.client.section.job.history.event;

import com.google.gwt.event.shared.GwtEvent;

public class JobHistoryStackDataRefreshEvent extends GwtEvent<JobHistoryStackDataReflashEventHandler>{

	public static Type<JobHistoryStackDataReflashEventHandler> TYPE = new Type<JobHistoryStackDataReflashEventHandler>();
	
	@Override
	public Type<JobHistoryStackDataReflashEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(JobHistoryStackDataReflashEventHandler handler) {
		// TODO Auto-generated method stub
		handler.historyStackDataRefreshEvent(this);
	}

}
