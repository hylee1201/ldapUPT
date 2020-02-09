package com.tdsecurities.upt.exception;

public class DuplicateSelfRegRequestException extends Exception  {

	/**
	 * 
	 * @param string
	 */
	public DuplicateSelfRegRequestException(String string) {
		 super(string);
	}
	
	
	public DuplicateSelfRegRequestException() {
		 super("Request is already in the pending list.");
	}
}
