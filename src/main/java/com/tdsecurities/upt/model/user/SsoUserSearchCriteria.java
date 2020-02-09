package com.tdsecurities.upt.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdsecurities.upt.constants.InetUserStatusEnum;
import com.tdsecurities.upt.constants.MemberAppEnum;

public class SsoUserSearchCriteria implements Serializable {
	private static final long serialVersionUID = 2403941900211033136L;

	private String uid;
	private String firstName;
	private String lastName;
	private String email;
	private InetUserStatusEnum userStatus;
	private boolean onlyActiveAppMembers = true;
	private Collection<MemberAppEnum> memberAppCollection = new ArrayList<MemberAppEnum>();
	private Map<MemberAppEnum, String> memberAppUsernames = new HashMap<MemberAppEnum, String>();
	private List<MemberAppEnum> memberAppEnrolled = new ArrayList<MemberAppEnum>();
	private List<MemberAppEnum> memberAppDeenrolled = new ArrayList<MemberAppEnum>();
	private List<String> aliasList = new ArrayList<String>();
	private MemberAppEnum aliasType;
	private List<String> groupList = new ArrayList<String>();
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the userStatus
	 */
	public InetUserStatusEnum getUserStatus() {
		return userStatus;
	}
	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(InetUserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}
	/**
	 * @return the memberAppCollection
	 */
	public Collection<MemberAppEnum> getMemberAppCollection() {
		return memberAppCollection;
	}
	/**
	 * @param memberAppCollection the memberAppCollection to set
	 */
	public void setMemberAppCollection(Collection<MemberAppEnum> memberAppCollection) {
		this.memberAppCollection = memberAppCollection;
	}
	/**
	 * @return the onlyActiveAppMembers
	 */
	public boolean isOnlyActiveAppMembers() {
		return onlyActiveAppMembers;
	}
	/**
	 * @param onlyActiveAppMembers the onlyActiveAppMembers to set
	 */
	public void setOnlyActiveAppMembers(boolean onlyActiveAppMembers) {
		this.onlyActiveAppMembers = onlyActiveAppMembers;
	}

	public Map<MemberAppEnum, String> getMemberAppUsernames()
	{
		return memberAppUsernames;
	}
	public void setMemberAppUsernames(Map<MemberAppEnum, String> memberAppUsernames)
	{
		this.memberAppUsernames = memberAppUsernames;
	}
	public List<MemberAppEnum> getMemberAppEnrolled()
	{
		return memberAppEnrolled;
	}
	public void setMemberAppEnrolled(List<MemberAppEnum> memberAppEnrolled)
	{
		this.memberAppEnrolled = memberAppEnrolled;
	}
	public List<MemberAppEnum> getMemberAppDeenrolled()
	{
		return memberAppDeenrolled;
	}
	public void setMemberAppDeenrolled(List<MemberAppEnum> memberAppDeenrolled)
	{
		this.memberAppDeenrolled = memberAppDeenrolled;
	}
	public List<String> getAliasList()
	{
		return aliasList;
	}
	public void setAliasList(List<String> aliasList)
	{
		this.aliasList = aliasList;
	}
	public MemberAppEnum getAliasType()
	{
		return aliasType;
	}
	public void setAliasType(MemberAppEnum aliasType)
	{
		this.aliasType = aliasType;
	}
	
	public List<String> getGroupList() {
		return groupList;
	}
	
	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}	
	
}
