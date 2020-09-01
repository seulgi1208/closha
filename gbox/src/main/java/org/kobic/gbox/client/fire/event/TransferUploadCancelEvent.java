package org.kobic.gbox.client.fire.event;

import java.util.List;

import org.kobic.gbox.client.model.TransferModel;

import com.pennychecker.eventbus.Event;

public class TransferUploadCancelEvent extends Event<TransferUploadCancelEventHandler> {

	public final static Type<TransferUploadCancelEventHandler> TYPE = new Type<TransferUploadCancelEventHandler>();

	private List<TransferModel> transferModel;

	public TransferUploadCancelEvent(List<TransferModel> transferModel) {
		this.transferModel = transferModel;
	}
	
	public TransferUploadCancelEvent() {
		super();
	}

	public List<TransferModel> getTransferModel() {
		return transferModel;
	}

	@Override
	protected void dispatch(TransferUploadCancelEventHandler handler) {
		// TODO Auto-generated method stub
		handler.transferCancel(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<TransferUploadCancelEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
