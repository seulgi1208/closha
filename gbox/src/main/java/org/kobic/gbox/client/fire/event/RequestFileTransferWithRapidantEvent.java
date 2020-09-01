package org.kobic.gbox.client.fire.event;

import java.util.List;

import org.kobic.gbox.client.model.TableFileModel;

import com.pennychecker.eventbus.Event;

public class RequestFileTransferWithRapidantEvent extends Event<RequestFileTransferWithRapidantEventHandler> {

	public final static Type<RequestFileTransferWithRapidantEventHandler> TYPE = new Type<RequestFileTransferWithRapidantEventHandler>();

	private List<TableFileModel> list;
	
	public List<TableFileModel> getList() {
		return list;
	}

	public RequestFileTransferWithRapidantEvent(List<TableFileModel> list) {
		super();
		this.list = list;
	}

	@Override
	protected void dispatch(RequestFileTransferWithRapidantEventHandler handler) {
		// TODO Auto-generated method stub
		handler.requestEvent(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<RequestFileTransferWithRapidantEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
