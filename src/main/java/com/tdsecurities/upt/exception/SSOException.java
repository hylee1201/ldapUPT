package com.tdsecurities.upt.exception;

public class SSOException extends Exception
{
	private static final long serialVersionUID = -657230077924173457L;
	
	/**
	 * 
	 */
	public SSOException() {
		 super();
	}

	/**
	 * 
	 * @param string
	 */
	public SSOException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public SSOException(Throwable cause){
		super(cause);
	}
}
