package org.kobic.gwt.smart.closha.client.frame.right.event;

import com.google.gwt.event.shared.GwtEvent;

public class JobHistoryDataLoadEvent extends GwtEvent<JobHistoryDataLoadEventHandler>{

	public static Type<JobHistoryDataLoadEventHandler> TYPE = new Type<JobHistoryDataLoadEventHandler>();
	
	@Override
	public Type<JobHistoryDataLoadEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(JobHistoryDataLoadEventHandler handler) {
		// TODO Auto-generated method stub
		handler.resetHistoryLabel(this);
	}

}
