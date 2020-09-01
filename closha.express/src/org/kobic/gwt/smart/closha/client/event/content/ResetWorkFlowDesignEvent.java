package org.kobic.gwt.smart.closha.client.event.content;

import com.google.gwt.event.shared.GwtEvent;

public class ResetWorkFlowDesignEvent extends GwtEvent<ResetWorkFlowDesignEventHandler>{
	
	public static Type<ResetWorkFlowDesignEventHandler> TYPE = new Type<ResetWorkFlowDesignEventHandler>();

	@Override
	public Type<ResetWorkFlowDesignEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ResetWorkFlowDesignEventHandler handler) {
		// TODO Auto-generated method stub
		handler.resetWorkFlowFrom(this);
	}

}
