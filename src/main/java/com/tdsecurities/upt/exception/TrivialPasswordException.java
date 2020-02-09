package com.tdsecurities.upt.exception;

public class TrivialPasswordException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TrivialPasswordException(){
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public TrivialPasswordException(String message){
		super(message);
	}
	
}
