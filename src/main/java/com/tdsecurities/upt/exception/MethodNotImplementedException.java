package com.tdsecurities.upt.exception;

public class MethodNotImplementedException extends Exception {

	

	/**
	 * 
	 */
	public MethodNotImplementedException() {
		 super();
	}

	/**
	 * 
	 * @param string
	 */
	public MethodNotImplementedException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public MethodNotImplementedException(Throwable cause){
		super(cause);
	}
}
