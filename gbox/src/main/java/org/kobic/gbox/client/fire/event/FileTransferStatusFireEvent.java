package org.kobic.gbox.client.fire.event;

import org.kobic.gbox.client.model.TableTransferModel;

import com.pennychecker.eventbus.Event;

public class FileTransferStatusFireEvent extends Event<FileTransferStatusFireEventHandler> {

	public final static Type<FileTransferStatusFireEventHandler> TYPE = new Type<FileTransferStatusFireEventHandler>();
	
	private TableTransferModel transferFile;
	
	public TableTransferModel getTransferData() {
		return transferFile;
	}

	public void setTransferData(TableTransferModel transferFile) {
		this.transferFile = transferFile;
	}
	
	public FileTransferStatusFireEvent(TableTransferModel transferFile) {
		super();
		this.transferFile = transferFile;
	}

	@Override
	protected void dispatch(FileTransferStatusFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.transferDataBindEvent(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<FileTransferStatusFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
