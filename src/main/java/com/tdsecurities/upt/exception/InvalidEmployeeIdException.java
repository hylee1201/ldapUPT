package com.tdsecurities.upt.exception;

public class InvalidEmployeeIdException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param string
	 */
	public InvalidEmployeeIdException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 */
	public InvalidEmployeeIdException() {
		 super();
	}
}
