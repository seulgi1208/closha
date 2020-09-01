package org.kobic.gbox.client.fire.event;

import org.kobic.gbox.client.model.TableTransferModel;

import com.pennychecker.eventbus.Event;

public class FileTransferInitFireEvent extends Event<FileTransferInitFireEventHandler> {

	public final static Type<FileTransferInitFireEventHandler> TYPE = new Type<FileTransferInitFireEventHandler>();
	
	private TableTransferModel transferFile;
	
	public TableTransferModel getTransferData() {
		return transferFile;
	}

	public void setTransferData(TableTransferModel transferFile) {
		this.transferFile = transferFile;
	}

	public FileTransferInitFireEvent(TableTransferModel transferFile) {
		super();
		this.transferFile = transferFile;
	}

	@Override
	protected void dispatch(FileTransferInitFireEventHandler handler) {
		// TODO Auto-generated method stub
		handler.transferTableBindEvent(this);
	}

	@Override
	public com.pennychecker.eventbus.Event.Type<FileTransferInitFireEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
}
