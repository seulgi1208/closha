package org.kobic.gwt.smart.closha.client.user.exception;

import java.io.PrintStream;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserException extends Exception implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	private Throwable nestedThrowable;
	
	public UserException(){
		nestedThrowable = null;
	}
	
	public UserException(String msg){
		super(msg);
		nestedThrowable = null;
	}
	
	public UserException(Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
	}
	
	public UserException(String msg, Throwable nestedThrowable){
		super(msg);
		this.nestedThrowable = nestedThrowable;
	}
	
	public void printStackTrace() {
		super.printStackTrace();
		if (nestedThrowable != null) {
			nestedThrowable.printStackTrace();
		}
	}

	public void pritnStackStrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (nestedThrowable != null) {
			nestedThrowable.printStackTrace(ps);
		}
	}
}
