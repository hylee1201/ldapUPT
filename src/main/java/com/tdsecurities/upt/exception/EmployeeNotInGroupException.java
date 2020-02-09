package com.tdsecurities.upt.exception;

public class EmployeeNotInGroupException extends Exception{

			/**
		 * 
		 * @param string
		 */
		public EmployeeNotInGroupException(String string) {
			 super(string);
		}
		
		/**
		 * 
		 */
		public EmployeeNotInGroupException() {
			 super("Employee is not set up in the AD Group");
		}
	}

