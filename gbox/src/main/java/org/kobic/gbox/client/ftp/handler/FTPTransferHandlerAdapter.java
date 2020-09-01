package org.kobic.gbox.client.ftp.handler;

public interface FTPTransferHandlerAdapter {

	public void started();

	public void progressed();

	public void configured();

	public void finished();

}
