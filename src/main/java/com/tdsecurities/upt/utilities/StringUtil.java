/**
 * Copyright (c) 2009 TD Bank Financial Group. All Rights Reserved.
 **/
package com.tdsecurities.upt.utilities;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.tdsecurities.upt.constants.Constants;

/**
 * The Class StringUtil.
 */
public class StringUtil {
	
	/** The Constant NULL_STRING. */
	private static final String NULL_STRING = "null";
	

	/** The Constant HTML_PERC. */
	private static final String HTML_PERC = "%";
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(StringUtil.class);

	/**
	 * Checks if is empty.
	 *
	 * @param p_text the text
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String p_text) {
		boolean l_isEmpty = false;
		if (p_text == null) {
			l_isEmpty = true;
		} else if (p_text.trim().length() == 0) {
			l_isEmpty = true;
		} else {
			l_isEmpty = false;
		}
		return l_isEmpty;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param p_text the text
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String[] p_text) {
		boolean l_isEmpty = false;
		if (p_text == null) {
			l_isEmpty = true;
		} else if (p_text.length == 0) {
			l_isEmpty = true;
		} else {
			l_isEmpty = false;
		}
		return l_isEmpty;
	}

	/**
	 * Checks if is double.
	 *
	 * @param p_text the p_text
	 * @return true, if is double
	 */
	public static boolean isDouble(String p_text) {
		boolean l_isDouble = false;
		try {
			new Double(p_text);
			l_isDouble = true;
		} catch (Exception ex) {
			l_isDouble = false;
		}
		return l_isDouble;
	}

	/**
	 * Checks if is positive double.
	 *
	 * @param p_text the p_text
	 * @return true, if is positive double
	 */
	public static boolean isPositiveDouble(String p_text) {
		boolean l_isDouble = false;
		try {
			Double l_value = new Double(p_text);
			if (l_value.doubleValue() >= 0)
				l_isDouble = true;
			else
				l_isDouble = false;
		} catch (Exception ex) {
			l_isDouble = false;
		}
		return l_isDouble;
	}

	/**
	 * Checks if is positive integer.
	 *
	 * @param p_text the p_text
	 * @return true, if is positive integer
	 */
	public static boolean isPositiveInteger(String p_text) {
		boolean l_isInteger = false;
		try {
			Integer l_value = new Integer(p_text);
			if (l_value.intValue() >= 0)
				l_isInteger = true;
			else
				l_isInteger = false;
		} catch (Exception l_ex) {
			LOG.debug("Exception...");
			l_isInteger = false;
		}
		return l_isInteger;
	}

	/**
	 * Adds the single quotes.
	 *
	 * @param p_str the p_str
	 * @return the string
	 */
	public static String addSingleQuotes(String p_str) {
		return "'" + (p_str == null ? "" : p_str.trim()) + "'";
	}

	/**
	 * Number only.
	 *
	 * @param p_value the p_value
	 * @return the string
	 */
	public static String numberOnly(String p_value) {
		StringTokenizer l_st = new StringTokenizer(p_value, Constants.COMMA_STRING);
		String l_withoutCommas = "";
		while (l_st.hasMoreTokens()) {
			l_withoutCommas = l_withoutCommas.concat(l_st.nextToken());
		}
		return l_withoutCommas;
	}

	/**
	 * To double only.
	 *
	 * @param p_value the p_value
	 * @return the double
	 */
	public static Double toDoubleOnly(String p_value) {
		StringTokenizer l_st = new StringTokenizer(p_value, Constants.COMMA_STRING);
		String l_withoutCommas = "";
		while (l_st.hasMoreTokens()) {
			l_withoutCommas = l_withoutCommas.concat(l_st.nextToken());
		}
		int l_lastIndex = l_withoutCommas.length() - 1;
		if (l_withoutCommas.charAt(0) == '('
				&& l_withoutCommas.charAt(l_withoutCommas.length() - 1) == ')') {
			l_withoutCommas = "-" + l_withoutCommas.substring(1, l_lastIndex);
		}
		return new Double(l_withoutCommas);
	}

	/**
	 * To integer only.
	 *
	 * @param p_value the p_value
	 * @return the integer
	 */
	public static Integer toIntegerOnly(String p_value) {
		StringTokenizer l_st = new StringTokenizer(p_value, Constants.COMMA_STRING);
		String l_result = "";
		String l_finalResult = "";
		while (l_st.hasMoreTokens()) {
			l_result = l_result.concat(l_st.nextToken());
		}
		l_st = new StringTokenizer(l_result, ".");
		while (l_st.hasMoreTokens()) {
			l_finalResult = l_finalResult.concat(l_st.nextToken());
		}

		int l_lastIndex = l_finalResult.length() - 2;
		if (l_finalResult.charAt(0) == '('
				&& l_finalResult.charAt(l_finalResult.length() - 1) == ')') {
			l_finalResult = "-" + l_finalResult.substring(1, l_lastIndex);
		}

		return new Integer(l_finalResult);
	}

	/**
	 * Checks if is date.
	 *
	 * @param p_dateString the p_date string
	 * @param p_pattern the p_pattern
	 * @return true, if is date
	 */
	public static boolean isDate(String p_dateString, String p_pattern) {
		SimpleDateFormat l_formatter = new SimpleDateFormat(p_pattern);
		boolean l_valid = false;
		try {
			l_formatter.parse(p_dateString);
			l_valid = true;
		} catch (Exception l_e) {
			l_valid = false;
		}

		return l_valid;
	}

	/**
	 * *************************************************************************
	 * Replace all occurrences of <code>o</code> in <code>str</code> with
	 * <code>n</code>, or only the first occurrence if <code>all</code> is
	 * <code>false</code>. <br>
	 * <code>replace("aaaa", "aa", "bbb", false)</code> returns
	 * <code>"bbbaa"</code> <br>
	 * <code>replace("aaaa", "aa", "bbb", true)</code> returns
	 * <code>"bbbbbb"</code>
	 * ************************************************************************
	 *
	 * @param p_str the p_str
	 * @param p_o the p_o
	 * @param p_n the p_n
	 * @param p_all the p_all
	 * @return the string
	 */
	public static String replace(String p_str, String p_o, String p_n, boolean p_all) {
		if (p_str == null || p_o == null || p_o.length() == 0 || p_n == null)
			throw new IllegalArgumentException("null or empty String");
		StringBuffer l_result = null;
		int l_oldpos = 0;
		do {
			int l_pos = p_str.indexOf(p_o, l_oldpos);
			if (l_pos < 0)
				break;
			if (l_result == null)
				l_result = new StringBuffer();
			l_result.append(p_str.substring(l_oldpos, l_pos));
			l_result.append(p_n);
			l_pos += p_o.length();
			l_oldpos = l_pos;
		} while (p_all);
		if (l_oldpos == 0) {
			return p_str;
		} else {
			l_result.append(p_str.substring(l_oldpos));
			return new String(l_result);
		}
	}

	/**
	 * Trim.
	 *
	 * @param p_str the p_str
	 * @return the string
	 */
	public static String trim(String p_str) {
		String l_retVal = Constants.EMPTY_STRING;
		if(p_str != null){
			l_retVal = p_str.trim();
		}
		return l_retVal;
	} 

	/**
	 * Strip chars.
	 *
	 * @param p_phoneNumber the phone number
	 * @return the string
	 */
	public static String stripChars(String p_phoneNumber)
	{   
	    String l_returnString = "";
	    char l_c;
	    // Search through string's characters one by one.
	    // If character is not in bag, append to returnString.
	    for (int l_i = 0; l_i < p_phoneNumber.length(); l_i++)
	    {   
	        // Check that current character isn't whitespace.
	        l_c = p_phoneNumber.charAt(l_i);
	        if ("1234567890".indexOf(l_c) > -1) {
	        	l_returnString += l_c;
	        	if (LOG.isDebugEnabled())
	        		LOG.debug("String: " + l_returnString);
	        }
	    }
	    return l_returnString;
	}

	/**
	 * Checks if is valid phone number.
	 *
	 * @param p_phoneNumber the phone number
	 * @return true, if is valid phone number
	 */
	public static boolean isValidPhoneNumber(String p_phoneNumber) {
	    char l_c;
	    for (int l_i = 0; l_i < p_phoneNumber.length(); l_i++)
	    {   
	        // Check that current character isn't whitespace.
	    	l_c = p_phoneNumber.charAt(l_i);
	        if (l_c != ' ' && "1234567890()+- ".indexOf(l_c) == -1) {
	        	return false;
	        }
	    }
		return true;
	}
	/**
	 * Checks if is null or empty string.
	 * 
	 * @param p_s
	 *            the s
	 * 
	 * @return true, if is null or empty string
	 */
	public static boolean isNullOrEmptyString(String p_s) {
		p_s = trimString(p_s);
		if (p_s == null || "".equals(p_s) || NULL_STRING.equals(p_s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method will trim the string.
	 * 
	 * @param p_str
	 *            the str
	 * 
	 * @return String value
	 */
	public static String trimString(String p_str) {
		if (p_str != null) {
			p_str = p_str.trim();
		}
		return p_str;
	}
	
	/**
	 * Gets the query ready string.
	 * 
	 * @param p_searchString
	 *            the search string
	 * 
	 * @return the query ready string
	 */
	public static String getQueryReadyString(String p_searchString) {
		String l_srchString = HTML_PERC;

		if (p_searchString != null) {
			String l_trimedString = p_searchString.trim();

			if (l_trimedString != null) {
				if (isNullOrEmptyString(p_searchString.trim())) {
					l_srchString = HTML_PERC;
				} else {
					l_srchString = p_searchString;
				}
			}
		}
		return l_srchString;
	}
	
	/**
	 * Gets the string with max length.
	 *
	 * @param p_str the p_str
	 * @param p_maxLength the p_max length
	 * @return the string with max length
	 */
	public static String getStringWithMaxLength(String p_str, int p_maxLength){
		int l_endIndex = -1;
		if(p_str!=null && (p_str.length()>=p_maxLength)){
			l_endIndex = p_maxLength;
		} else {
			l_endIndex = p_str.length();
		}
		if(p_str != null){
			return p_str.substring(0, l_endIndex);
		} else{
			return p_str;
		}
	}

	/**
	 * Replace escape char.
	 * 
	 * @param p_string
	 *            the string
	 * @return the string
	 */
	public static String replaceEscapeChar(String p_string) {
		String l_ret = null;

		if (StringUtil.isEmpty(p_string)) {
			return null;
		} else {
			if (p_string.indexOf("_") != -1) {
				l_ret = p_string.replace("_", "\\_");
			}
			if (p_string.indexOf("%") != -1) {
				l_ret = p_string.replace("%", "\\%");
			}
			if (p_string.indexOf("\\") != -1) {
				l_ret = p_string.replace("\\", "\\\\");
			}

			if (null == l_ret) {
				l_ret = p_string;
			}
		}
		return l_ret.trim();
	}

}
