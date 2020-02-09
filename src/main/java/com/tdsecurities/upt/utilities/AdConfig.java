package com.tdsecurities.upt.utilities;

public class AdConfig {
	private String url;
	private String baseDN;
	private String userDN;
	private String password;
	private String timeout;
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the baseDN
	 */
	public String getBaseDN() {
		return baseDN;
	}
	/**
	 * @param baseDN the baseDN to set
	 */
	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}
	/**
	 * @return the userDN
	 */
	public String getUserDN() {
		return userDN;
	}
	/**
	 * @param userDN the userDN to set
	 */
	public void setUserDN(String userDN) {
		this.userDN = userDN;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("url:"+url);
		buffer.append("\n");
		buffer.append("baseDN:"+baseDN);
		buffer.append("\n");
		buffer.append("userDN:"+userDN);
		buffer.append("\n");
		//buffer.append("password:"+password);
		//buffer.append("\n");
		buffer.append("timeout:"+timeout);
		
		return buffer.toString();
	}
}
