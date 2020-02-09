package com.tdsecurities.upt.exception;

public class GlobalUpdateUserException extends Exception {

		/**
		 * 
		 * @param string
		 */
		public GlobalUpdateUserException(String string) {
			 super(string);
		}
		
		
		public GlobalUpdateUserException() {
			 super("");
		}
	
}
