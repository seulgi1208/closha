package org.kobic.gbox.client.common.exception;

public class KoDSExecutorServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public KoDSExecutorServiceException(){
		super("ExecutorService Shutdown!!");
	}
	
	public KoDSExecutorServiceException(String msg){
		super(msg);
	}

	public String toString(){
		return "ExecutorService Shutdown!!";
	}
}
