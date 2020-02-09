package com.tdsecurities.upt.ldap;

import org.springframework.ldap.core.support.LdapContextSource;

public class UptLdapContextSource extends LdapContextSource {
		
	public void setUserDn(String userDn) {
		super.setUserDn( userDn.trim());
	}
	
	public void setUrl(String url) {
		super.setUrl(url.trim());
	}
	
	public void setBase(String base) {
		super.setBase(base.trim());
	}
	
	public void setPassword(String password) {
		super.setPassword(password.trim());
	}
}
