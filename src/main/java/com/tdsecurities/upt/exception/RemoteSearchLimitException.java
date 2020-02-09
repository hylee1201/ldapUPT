package com.tdsecurities.upt.exception;

public class RemoteSearchLimitException extends Exception {

	/**
	 * 
	 */
	public RemoteSearchLimitException() {
		 super("Search Limit Reached");
	}

	/**
	 * 
	 * @param string
	 */
	public RemoteSearchLimitException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public RemoteSearchLimitException(Throwable cause){
		super(cause);
	}
}
