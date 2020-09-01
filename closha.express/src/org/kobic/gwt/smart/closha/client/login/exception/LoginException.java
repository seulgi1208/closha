package org.kobic.gwt.smart.closha.client.login.exception;

import java.io.PrintStream;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginException extends Exception implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	private Throwable nestedThrowable;
	
	public LoginException(){
		nestedThrowable = null;
	}
	
	public LoginException(String msg){
		super(msg);
		nestedThrowable = null;
	}
	
	public LoginException(Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
	}
	
	public LoginException(String msg, Throwable nestedThrowable){
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
