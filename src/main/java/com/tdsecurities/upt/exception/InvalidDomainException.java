package com.tdsecurities.upt.exception;

public class InvalidDomainException extends Exception {

	/**
	 * 
	 * @param string
	 */
	public InvalidDomainException(String string) {
		 super("Invalid Domain " + string);
	}
	
	/**
	 * 
	 */
	public InvalidDomainException() {
		 super("Invalid Domain");
	}
}
