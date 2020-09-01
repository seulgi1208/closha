package org.kobic.gwt.smart.closha.client.frame.right.event;

import com.google.gwt.event.shared.GwtEvent;

public class JobHistoryFilteringEvent extends GwtEvent<JobHistoryFilteringEventHandler>{

	public static Type<JobHistoryFilteringEventHandler> TYPE = new Type<JobHistoryFilteringEventHandler>();
	
	private int state;	
	
	public int getState(){
		return state;
	}
	
	public JobHistoryFilteringEvent(int state){
		this.state = state;
	}
	
	@Override
	public Type<JobHistoryFilteringEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(JobHistoryFilteringEventHandler handler) {
		// TODO Auto-generated method stub
		handler.stateChangedEvent(this);
	}

}
