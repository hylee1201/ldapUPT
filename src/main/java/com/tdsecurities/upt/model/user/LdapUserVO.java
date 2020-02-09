package com.tdsecurities.upt.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.tdsecurities.upt.constants.InetUserStatusEnum;
import com.tdsecurities.upt.utilities.StringUtil;

public class LdapUserVO {
	private String uid;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private InetUserStatusEnum inetUserStatus;
	private String TDtdsissouserAppID1;
	private String TDtdsissouserAppID2;
	private String TDtdsissouserAppID3;
	private String TDtdsissouserAppID4;
	private String TDtdsissouserAppID5;
	private String TDtdsissouserAppID6;
	private String TDtdsissouserAppID7;
	private String TDtdsissouserAppID8;
	private String TDtdsissouserAppID9;
	private String TDtdsissouserAppID10;
	private String TDtdsissouserAppID11;
	private String TDtdsissouserAppID12;
	private Collection<String> roleCollection = new ArrayList<String>();
	private Collection<String> questionAnswerCollection = new ArrayList<String>();
	private String roleAuthInstance;
	private Date passwordExpirationTime;
	private int passwordRetryCount;
	private Date retryCountResetTime;
	private MigrationFlagEnum migrationFlag;
	private String domain;
	private Collection<String> legacyUserApplicationCollection = new ArrayList<String>();
	
	//for employee only
	private Collection<String> groupMemberCollection = new ArrayList<String>();
	
	private String company;
	private String occupation;
	private String phoneNumber;
	private String distiguishedName;
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("uid:"+(StringUtil.isEmpty(getUid())?"":getUid()));
		buffer.append("\n");
		buffer.append("firstName:"+(StringUtil.isEmpty(getFirstName())?"":getFirstName()));
		buffer.append("\n");
		buffer.append("LastName:"+(StringUtil.isEmpty(getLastName())?"":getLastName()));
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("email:"+(StringUtil.isEmpty(getEmail())?"":getEmail()));
		
		return buffer.toString();
	}
	
	public void addLegacyUserApplication(String application){
		legacyUserApplicationCollection.add(application);
	}
	
	/**
	 * 
	 * @param member
	 */
	public void addGroupMember(String member){
		groupMemberCollection.add(member);
	}
	
	/**
	 * Adds a new role to the role collection
	 * 
	 * @param role
	 */
	public void addRole(String role){
		roleCollection.add(role);
	}
	
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
	 * @return the inetUserStatus
	 */
	public InetUserStatusEnum getInetUserStatus() {
		return inetUserStatus;
	}
	/**
	 * @param inetUserStatus the inetUserStatus to set
	 */
	public void setInetUserStatus(InetUserStatusEnum inetUserStatus) {
		this.inetUserStatus = inetUserStatus;
	}
	/**
	 * @return the tDtdsissouserAppID1
	 */
	public String getTDtdsissouserAppID1() {
		return TDtdsissouserAppID1;
	}
	/**
	 * @param dtdsissouserAppID1 the tDtdsissouserAppID1 to set
	 */
	public void setTDtdsissouserAppID1(String dtdsissouserAppID1) {
		TDtdsissouserAppID1 = dtdsissouserAppID1;
	}
	/**
	 * @return the tDtdsissouserAppID2
	 */
	public String getTDtdsissouserAppID2() {
		return TDtdsissouserAppID2;
	}
	/**
	 * @param dtdsissouserAppID2 the tDtdsissouserAppID2 to set
	 */
	public void setTDtdsissouserAppID2(String dtdsissouserAppID2) {
		TDtdsissouserAppID2 = dtdsissouserAppID2;
	}
	/**
	 * @return the tDtdsissouserAppID3
	 */
	public String getTDtdsissouserAppID3() {
		return TDtdsissouserAppID3;
	}
	/**
	 * @param dtdsissouserAppID3 the tDtdsissouserAppID3 to set
	 */
	public void setTDtdsissouserAppID3(String dtdsissouserAppID3) {
		TDtdsissouserAppID3 = dtdsissouserAppID3;
	}
	/**
	 * @return the tDtdsissouserAppID4
	 */
	public String getTDtdsissouserAppID4() {
		return TDtdsissouserAppID4;
	}
	/**
	 * @param dtdsissouserAppID4 the tDtdsissouserAppID4 to set
	 */
	public void setTDtdsissouserAppID4(String dtdsissouserAppID4) {
		TDtdsissouserAppID4 = dtdsissouserAppID4;
	}
	/**
	 * @return the tDtdsissouserAppID5
	 */
	public String getTDtdsissouserAppID5() {
		return TDtdsissouserAppID5;
	}
	/**
	 * @param dtdsissouserAppID5 the tDtdsissouserAppID5 to set
	 */
	public void setTDtdsissouserAppID5(String dtdsissouserAppID5) {
		TDtdsissouserAppID5 = dtdsissouserAppID5;
	}
	/**
	 * @return the tDtdsissouserAppID6
	 */
	public String getTDtdsissouserAppID6() {
		return TDtdsissouserAppID6;
	}
	/**
	 * @param dtdsissouserAppID6 the tDtdsissouserAppID6 to set
	 */
	public void setTDtdsissouserAppID6(String dtdsissouserAppID6) {
		TDtdsissouserAppID6 = dtdsissouserAppID6;
	}
	/**
	 * @return the tDtdsissouserAppID7
	 */
	public String getTDtdsissouserAppID7() {
		return TDtdsissouserAppID7;
	}
	/**
	 * @param dtdsissouserAppID7 the tDtdsissouserAppID7 to set
	 */
	public void setTDtdsissouserAppID7(String dtdsissouserAppID7) {
		TDtdsissouserAppID7 = dtdsissouserAppID7;
	}
	/**
	 * @return the tDtdsissouserAppID8
	 */
	public String getTDtdsissouserAppID8() {
		return TDtdsissouserAppID8;
	}
	/**
	 * @param dtdsissouserAppID8 the tDtdsissouserAppID8 to set
	 */
	public void setTDtdsissouserAppID8(String dtdsissouserAppID8) {
		TDtdsissouserAppID8 = dtdsissouserAppID8;
	}
	/**
	 * @return the tDtdsissouserAppID9
	 */
	public String getTDtdsissouserAppID9() {
		return TDtdsissouserAppID9;
	}
	/**
	 * @param dtdsissouserAppID9 the tDtdsissouserAppID9 to set
	 */
	public void setTDtdsissouserAppID9(String dtdsissouserAppID9) {
		TDtdsissouserAppID9 = dtdsissouserAppID9;
	}
	/**
	 * @return the tDtdsissouserAppID10
	 */
	public String getTDtdsissouserAppID10() {
		return TDtdsissouserAppID10;
	}
	/**
	 * @param dtdsissouserAppID10 the tDtdsissouserAppID10 to set
	 */
	public void setTDtdsissouserAppID10(String dtdsissouserAppID10) {
		TDtdsissouserAppID10 = dtdsissouserAppID10;
	}
	/**
	 * @return the tDtdsissouserAppID11
	 */
	public String getTDtdsissouserAppID11() {
		return TDtdsissouserAppID11;
	}
	/**
	 * @param dtdsissouserAppID11 the tDtdsissouserAppID11 to set
	 */
	public void setTDtdsissouserAppID11(String dtdsissouserAppID11) {
		TDtdsissouserAppID11 = dtdsissouserAppID11;
	}
	/**
	 * @return the tDtdsissouserAppID12
	 */
	public String getTDtdsissouserAppID12() {
		return TDtdsissouserAppID12;
	}
	/**
	 * @param dtdsissouserAppID12 the tDtdsissouserAppID12 to set
	 */
	public void setTDtdsissouserAppID12(String dtdsissouserAppID12) {
		TDtdsissouserAppID12 = dtdsissouserAppID12;
	}
	/**
	 * @return the roleCollection
	 */
	public Collection<String> getRoleCollection() {
		return roleCollection;
	}
	/**
	 * @param roleCollection the roleCollection to set
	 */
	public void setRoleCollection(Collection<String> roleCollection) {
		this.roleCollection = roleCollection;
	}

	/**
	 * @return the roleAuthInstance
	 */
	public String getRoleAuthInstance() {
		return roleAuthInstance;
	}

	/**
	 * @param roleAuthInstance the roleAuthInstance to set
	 */
	public void setRoleAuthInstance(String roleAuthInstance) {
		this.roleAuthInstance = roleAuthInstance;
	}

	public Collection<String> getQuestionAnswerCollection()
	{
		return questionAnswerCollection;
	}

	public void setQuestionAnswerCollection(
			Collection<String> questionAnswerCollection)
	{
		this.questionAnswerCollection = questionAnswerCollection;
	}

	public Date getPasswordExpirationTime()
	{
		return passwordExpirationTime;
	}

	public void setPasswordExpirationTime(Date passwordExpirationTime)
	{
		this.passwordExpirationTime = passwordExpirationTime;
	}

	/**
	 * @return the migrationFlag
	 */
	public MigrationFlagEnum getMigrationFlag() {
		return migrationFlag;
	}

	/**
	 * @param migrationFlag the migrationFlag to set
	 */
	public void setMigrationFlag(MigrationFlagEnum migrationFlag) {
		this.migrationFlag = migrationFlag;
	}

	/**
	 * @return the passwordRetryCount
	 */
	public int getPasswordRetryCount() {
		return passwordRetryCount;
	}

	/**
	 * @param passwordRetryCount the passwordRetryCount to set
	 */
	public void setPasswordRetryCount(int passwordRetryCount) {
		this.passwordRetryCount = passwordRetryCount;
	}

	/**
	 * @return the retryCountResetTime
	 */
	public Date getRetryCountResetTime() {
		return retryCountResetTime;
	}

	/**
	 * @param retryCountResetTime the retryCountResetTime to set
	 */
	public void setRetryCountResetTime(Date retryCountResetTime) {
		this.retryCountResetTime = retryCountResetTime;
	}

	/**
	 * @return the memberCollection
	 */
	public Collection<String> getGroupMemberCollection() {
		return groupMemberCollection;
	}

	/**
	 * @param memberCollection the memberCollection to set
	 */
	public void setGroupMemberCollection(Collection<String> memberCollection) {
		this.groupMemberCollection = memberCollection;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Collection<String> getLegacyUserApplicationCollection() {
		return legacyUserApplicationCollection;
	}

	public void setLegacyUserApplicationCollection(
			Collection<String> legacyUserApplicationCollection) {
		this.legacyUserApplicationCollection = legacyUserApplicationCollection;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDistiguishedName() {
		return distiguishedName;
	}

	public void setDistiguishedName(String distiguishedName) {
		this.distiguishedName = distiguishedName;
	}	
	
	public boolean isEmployeeInGroup(String[] groupNames){
		if(groupNames == null) return false;
		for(String groupMember : getGroupMemberCollection()){
			for(String groupName: groupNames){
				if(groupMember.equalsIgnoreCase(groupName)){
					return true;	
				}
			}				
		}
		
		return false;
	}
	
}
