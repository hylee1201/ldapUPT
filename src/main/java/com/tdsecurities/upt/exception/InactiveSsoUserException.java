package com.tdsecurities.upt.exception;

public class InactiveSsoUserException extends Exception {

	/**
	 * 
	 * @param string
	 */
	public InactiveSsoUserException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 */
	public InactiveSsoUserException() {
		 super("Inactive SSO user.");
	}
}
