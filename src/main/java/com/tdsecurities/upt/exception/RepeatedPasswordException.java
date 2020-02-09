package com.tdsecurities.upt.exception;

public class RepeatedPasswordException extends Exception {

	public RepeatedPasswordException(String string) {
		 super(string);
	}
	
	public RepeatedPasswordException() {
		 super();
	}
}