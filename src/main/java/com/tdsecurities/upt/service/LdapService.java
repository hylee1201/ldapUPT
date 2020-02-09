package com.tdsecurities.upt.service;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import com.tdsecurities.upt.constants.MemberAppEnum;
import com.tdsecurities.upt.exception.SSOException;
import com.tdsecurities.upt.exception.TrivialPasswordException;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.model.user.UserEnrollmentMappingVO;
import com.tdsecurities.upt.exception.RepeatedPasswordException;

public interface LdapService {

	
	/**
	 * Returns a set with the applications that this user is an active member of 
	 * 
	 * @param ldapUserVO
	 * @return
	 */
	public EnumSet<MemberAppEnum> getMemberAppsForUser(LdapUserVO ldapUserVO);
	
	
	/**
	 * Return a collection containing all the application where the user has been enrolled. 
	 * The user could be active or inactive in these applications
	 *  
	 * @param ldapUserVO
	 * @return
	 */
	public Collection<UserEnrollmentMappingVO> getUserEnrollmentCollection(LdapUserVO ldapUserVO);
	
	
	/**
	 * Set the user alias for the member app
	 * 
	 * @param ldapUserVO
	 * @param alias
	 * @param memberApp
	 * @param isEnrolled
	 */
	public void setLdapUserAliasAndRole(LdapUserVO ldapUserVO, String alias, MemberAppEnum memberApp, boolean isEnrolled);
	
	/**
	 * Generates the LDAP role
	 * 
	 * @param memberApp
	 * @return
	 */
	public String generateLdapRole(MemberAppEnum memberApp);

//	/**
//	 * Creates a new user temporary in the Identity directory. 
//	 * This is usually created on the self registration. InetUserStatus is set to Temporary.
//	 * 
//	 * @param ldapUserVO
//	 */
//	public void createTempUser(LdapUserVO ldapUserVO);
	

	/**
	 * Creates a new user. It creates the user in Authentication and Identity repository.
	 * If an exception is thrown during the create process, the system tries to cleanup the data.
	 * 
	 * @param ldapUserVO
	 * @throws TrivialPasswordException
	 * @throws SSOException 
	 */
	public void createUser(LdapUserVO ldapUserVO) throws TrivialPasswordException, SSOException;

//	/**
//	 * Creates a new SSO user from a temporary user.
//	 * 
//	 * @param ldapUserVO
//	 */
//	public void createUserFromTemp(LdapUserVO ldapUserVO);
	
	/**
	 * Updates a user. It updates the user in Authentication and Identity repository
	 * 
	 * @param ldapUserVO
	 * @throws RepeatedPasswordException
	 * @throws TrivialPasswordException 
	 * @throws SSOException 
	 */
	public void updateUser(LdapUserVO ldapUserVO) throws RepeatedPasswordException, TrivialPasswordException, SSOException;
	
	/**
	 * Updates the password for an user
	 * 
	 * @param uid
	 * @param password
	 * @param isUserChangingItsOwnPassword - true if user is changing its profile
	 * @throws RepeatedPasswordException
	 * @throws TrivialPasswordException 
	 * @throws SSOException 
	 */
	public void updateUserPassword(String uid, String password, String oldPassword, boolean isUserChangingItsOwnPassword) throws RepeatedPasswordException, TrivialPasswordException, SSOException, Exception;
	
	/**
	 * Removes a user from the directory
	 * 
	 * @param uid
	 */
	public void removeUser(String uid);
	
	/**
	 * Finds an user by uid.
	 * If fullDataSet is set to true, it returns the data from Identity 
	 * 
	 * @param uid
	 * @param fullDataSet
	 * @return
	 */
	public LdapUserVO findUserByUid(String uid, boolean fullDataSet);
	
	/**
	 * Finds a user based on the app alias
	 * 
	 * @param alias
	 * @param memberApp
	 * @return
	 */
	public LdapUserVO findUserByAlias(String alias, MemberAppEnum memberApp);
	
	/**
	 * Find the users with the aliases specified
	 * 
	 * @param aliasList
	 * @param memberApp
	 * @return
	 */
	public List<LdapUserVO> findUsersForAliases(List<String> aliasList, MemberAppEnum memberApp);
	
	
	/**
	 * Searches the LDAP server for users
	 * 
	 * @param ssoUserSearchCriteria
	 * @return
	 */
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria ssoUserSearchCriteria);
	
	/**
	 * Finds an employee
	 * 
	 * @param employeeId
	 * @param userDomain
	 * @return
	 */
	public LdapUserVO findEmployee(String employeeId, String userDomain) throws Exception;
	
	/**
	 * cleanup User Before Update
	 * @param uid
	 */
	public void cleanupUserBeforeUpdate(LdapUserVO ldapUserVO);	
	
	/**
	 * checks if the user's password is correct
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean authenticate(String uid, String password);
	
	public boolean authenticate(String uid, String password, String userDN, String baseDN, String url);
	
	public boolean isEmployeeInGroup(String uid, String domain, String[] groupNames) throws Exception;
	
	public List<LdapUserVO> searchEmployee(SsoUserSearchCriteria searchCriteria, String domain) throws Exception;
	
	public List<LdapUserVO> searchEmployee(SsoUserSearchCriteria searchCriteria) throws Exception;
}
