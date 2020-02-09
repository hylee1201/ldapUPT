package com.tdsecurities.upt.exception;

public class EmployeeEmptyFieldException extends Exception {

	/**
	 * 
	 * @param string
	 */
	public EmployeeEmptyFieldException(String string) {
		 super(string);
	}
	
	public EmployeeEmptyFieldException(String id, String domain, String fieldName) {
		 super(fieldName+" is missing for user "+id+" in "+domain+".  Please contact your help desk.");
	}
	/**
	 * 
	 */
	public EmployeeEmptyFieldException() {
		 super();
	}
}
