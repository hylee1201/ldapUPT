package com.tdsecurities.upt.model.user;

import java.io.Serializable;

import com.tdsecurities.upt.constants.MemberAppEnum;

public class UserEnrollmentMappingVO implements Serializable {
	private static final long serialVersionUID = 2403941900211033136L;

	private MemberAppEnum memberApp;
	private String alias;
	private boolean active;
	
	/**
	 * @return the memberApp
	 */
	public MemberAppEnum getMemberApp() {
		return memberApp;
	}
	/**
	 * @param memberApp the memberApp to set
	 */
	public void setMemberApp(MemberAppEnum memberApp) {
		this.memberApp = memberApp;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
