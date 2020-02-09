package com.tdsecurities.upt.exception;

public class EmployeeNotFoundException extends Exception {

	/**
	 * 
	 * @param string
	 */
	public EmployeeNotFoundException(String string) {
		 super(string);
	}
	
	public EmployeeNotFoundException(String id, String domain) {
		 super("User "+id+" is not found in the specified "+domain+" domain");
	}
	/**
	 * 
	 */
	public EmployeeNotFoundException() {
		 super();
	}
}
