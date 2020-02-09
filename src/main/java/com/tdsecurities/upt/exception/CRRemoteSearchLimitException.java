package com.tdsecurities.upt.exception;

public class CRRemoteSearchLimitException extends Exception
{
	private static final long serialVersionUID = -5472006521510681072L;

	/**
	 * 
	 */
	public CRRemoteSearchLimitException() {
		 super();
	}

	/**
	 * 
	 * @param string
	 */
	public CRRemoteSearchLimitException(String string) {
		 super(string);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public CRRemoteSearchLimitException(Throwable cause){
		super(cause);
	}
}
