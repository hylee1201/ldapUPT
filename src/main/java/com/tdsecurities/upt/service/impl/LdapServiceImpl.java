/**
 * 
 */
package com.tdsecurities.upt.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ldap.InvalidAttributeValueException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.tdsecurities.upt.constants.LdapAttrContants;
import com.tdsecurities.upt.constants.MemberAppEnum;
import com.tdsecurities.upt.constants.UptConstants;
import com.tdsecurities.upt.exception.SSOException;
import com.tdsecurities.upt.exception.TrivialPasswordException;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.MigrationFlagEnum;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.model.user.UserEnrollmentMappingVO;
import com.tdsecurities.upt.persistence.LdapAuthenticationDAO;
import com.tdsecurities.upt.persistence.LdapIdentityDAO;
import com.tdsecurities.upt.persistence.ldap.FilterAttribute;
import com.tdsecurities.upt.service.AdService;
import com.tdsecurities.upt.service.LdapService;
import com.tdsecurities.upt.exception.RepeatedPasswordException;
import com.tdsecurities.wbts.service.PropertyService;

/**
 * @author brinzf2
 *
 */
public class LdapServiceImpl implements LdapService {
	private static final Logger logger = Logger.getLogger(LdapServiceImpl.class);

	private static final String ERROR_PASSWORD_IN_HISTORY 		= "[LDAP: error code 19 - password in history]";
	private static final String ERROR_PASSWORD_TRIVIALITY_CHECK	= "[LDAP: error code 19 - Password failed triviality check. Please choose a different password.]";
	
	private PropertyService propertyService;
	
	private LdapAuthenticationDAO ldapAuthenticationDAO;
	private LdapIdentityDAO ldapIdentityDAO;
	//private AdTdsDAO adTdsDAO;
	private AdService adService;
	
	private Pattern backslashPattern;
	private String persistedDelimiter;
	private String roleAuthInstance;
	private String roleIdentityInstancePrefix;
	private String userDnAuthInstance;
	
	private String authURL;
	private String authBaseDN;

	
	/**
	 * Public constructor
	 */
	public LdapServiceImpl(){
	}
	
	/**
	 * Init method
	 */
	public void init(){
		persistedDelimiter = propertyService.readProperty(UptConstants.SCOPE_LDAP, UptConstants.PROPERTY_UID_DOMAIN_DELIMITER);
		roleAuthInstance = propertyService.readProperty(UptConstants.SCOPE_LDAP, UptConstants.PROPERTY_AUTH_INSTANCE_ROLE);
		userDnAuthInstance = propertyService.readProperty(UptConstants.SCOPE_LDAP, UptConstants.PROPERTY_AUTH_USER_DN);
		roleIdentityInstancePrefix = propertyService.readProperty(UptConstants.SCOPE_LDAP, UptConstants.PROPERTY_IDENTITY_INSTANCE_ROLE_PREFIX);
		backslashPattern = Pattern.compile("\\\\");			// regex pattern for "\" is "\\\\"
	}
	
	/**
	 * Returns a set with the applications that this user is an active member of 
	 * 
	 * @param ldapUserVO
	 * @return
	 */
	public EnumSet<MemberAppEnum> getMemberAppsForUser(LdapUserVO ldapUserVO){
		EnumSet<MemberAppEnum> memberApps = EnumSet.noneOf(MemberAppEnum.class);
		
		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID1())){
			memberApps.add(MemberAppEnum.UPT);
		}
		if(isUserMemberOf(ldapUserVO, MemberAppEnum.CRP)){
			memberApps.add(MemberAppEnum.CRP);
		}
		if(isUserMemberOf(ldapUserVO, MemberAppEnum.TDFX)){
			memberApps.add(MemberAppEnum.TDFX);
		}
		if(isUserMemberOf(ldapUserVO, MemberAppEnum.ERP)){
			memberApps.add(MemberAppEnum.ERP);
		}
		if(isUserMemberOf(ldapUserVO, MemberAppEnum.SDP)){
			memberApps.add(MemberAppEnum.SDP);
		}		
		// TODO check for the other member applications		
		return memberApps;
	}
	
	/**
	 * Checks is the user is member of specified App
	 * 
	 * @param ldapUserVO
	 * @param memberApp
	 * @return
	 */
	private boolean isUserMemberOf(LdapUserVO ldapUserVO, MemberAppEnum memberApp){
		boolean isMember = false;
		
		for(String role : ldapUserVO.getRoleCollection()){
			if(role.toUpperCase().indexOf(memberApp.name())!=-1){
				isMember = true;
				break;
			}
		}
		
		return isMember;
	}
	
	/**
	 * Return a collection containing all the application where the user has been enrolled. 
	 * The user could be active or inactive in these applications
	 *  
	 * @param ldapUserVO
	 * @return
	 */
	public Collection<UserEnrollmentMappingVO> getUserEnrollmentCollection(LdapUserVO ldapUserVO){
		Collection<UserEnrollmentMappingVO> userEnrollmentCollection = new ArrayList<UserEnrollmentMappingVO>();
		
		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID1())){
			UserEnrollmentMappingVO userEnrollmentMapping = new UserEnrollmentMappingVO();
			userEnrollmentMapping.setMemberApp(MemberAppEnum.UPT);
			userEnrollmentMapping.setAlias(ldapUserVO.getTDtdsissouserAppID1());
			userEnrollmentMapping.setActive(true);
			userEnrollmentCollection.add(userEnrollmentMapping);
		}
		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID2())){
			UserEnrollmentMappingVO userEnrollmentMapping = new UserEnrollmentMappingVO();
			userEnrollmentMapping.setMemberApp(MemberAppEnum.CRP);
			userEnrollmentMapping.setAlias(ldapUserVO.getTDtdsissouserAppID2());
			userEnrollmentMapping.setActive(isUserMemberOf(ldapUserVO, MemberAppEnum.CRP));
			userEnrollmentCollection.add(userEnrollmentMapping);
		}
//		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID3())){
//			UserEnrollmentMappingVO userEnrollmentMapping = new UserEnrollmentMappingVO();
//			userEnrollmentMapping.setMemberApp(MemberAppEnum.TDFX);
//			userEnrollmentMapping.setAlias(ldapUserVO.getTDtdsissouserAppID3());
//			userEnrollmentMapping.setActive(isUserMemberOf(ldapUserVO, MemberAppEnum.TDFX));
//			userEnrollmentCollection.add(userEnrollmentMapping);
//		}
		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID3())){
			UserEnrollmentMappingVO userEnrollmentMapping = new UserEnrollmentMappingVO();
			userEnrollmentMapping.setMemberApp(MemberAppEnum.ERP);
			userEnrollmentMapping.setAlias(ldapUserVO.getTDtdsissouserAppID3());
			userEnrollmentMapping.setActive(isUserMemberOf(ldapUserVO, MemberAppEnum.ERP));
			userEnrollmentCollection.add(userEnrollmentMapping);
		}
		if(StringUtils.isNotEmpty(ldapUserVO.getTDtdsissouserAppID4())){
			UserEnrollmentMappingVO userEnrollmentMapping = new UserEnrollmentMappingVO();
			userEnrollmentMapping.setMemberApp(MemberAppEnum.SDP);
			userEnrollmentMapping.setAlias(ldapUserVO.getTDtdsissouserAppID4());
			userEnrollmentMapping.setActive(isUserMemberOf(ldapUserVO, MemberAppEnum.SDP));
			userEnrollmentCollection.add(userEnrollmentMapping);
		}		
		// TODO check for the other member applications		
		
		return userEnrollmentCollection;
	}
	
	/**
	 * Set the user alias and role for the member app
	 * 
	 * @param ldapUserVO
	 * @param alias
	 * @param memberApp
	 * @param isEnrolled
	 */
	public void setLdapUserAliasAndRole(LdapUserVO ldapUserVO, String alias, MemberAppEnum memberApp, boolean isEnrolled){
		if(memberApp==MemberAppEnum.UPT){
			ldapUserVO.setTDtdsissouserAppID1(alias);
		}
		else if(memberApp==MemberAppEnum.CRP){
			ldapUserVO.setTDtdsissouserAppID2(alias);
			if(isEnrolled){
				ldapUserVO.addRole(generateLdapRole(MemberAppEnum.CRP));
			}
		}
//		else if(memberApp==MemberAppEnum.TDFX){
//			ldapUserVO.setTDtdsissouserAppID3(alias);
//			if(isEnrolled){
//				ldapUserVO.addRole(generateLdapRole(MemberAppEnum.TDFX));
//			}
//		}
		else if(memberApp==MemberAppEnum.ERP){
			ldapUserVO.setTDtdsissouserAppID3(alias);
			if(isEnrolled){
				ldapUserVO.addRole(generateLdapRole(MemberAppEnum.ERP));
			}
		} else if(memberApp==MemberAppEnum.SDP){
			ldapUserVO.setTDtdsissouserAppID4(alias);
			if(isEnrolled){
				ldapUserVO.addRole(generateLdapRole(MemberAppEnum.SDP));
			}
		}
		// TODO set the alias for the other member applications		
	}
	
	/**
	 * Generates the LDAP role
	 * 
	 * @param memberApp
	 * @return
	 */
	public String generateLdapRole(MemberAppEnum memberApp){
		String role = null;
		
		role = roleIdentityInstancePrefix
				+ memberApp.name()
				+ ","
				+ ldapIdentityDAO.getBaseDN();
		
		return role;
	}
	
	

//	/**
//	 * Creates a new user temporary in the Identity directory. 
//	 * This is usually created on the self registration. InetUserStatus is set to Temporary.
//	 * 
//	 * @param ldapUserVO
//	 */
//	public void createTempUser(LdapUserVO ldapUserVO){
//		String _logger_method="createTempUser";
//		if (logger.isDebugEnabled()) {
//			logger.debug("> "+_logger_method);
//		}
//
//		String presentationUid = ldapUserVO.getUid();
//		ldapUserVO.setUid( convertUidForStorage(presentationUid) );
//		
//		ldapUserVO.setInetUserStatus(InetUserStatusEnum.Temporary);
//		ldapIdentityDAO.createUser(ldapUserVO);
//		
//		if (logger.isDebugEnabled()) {
//			logger.debug("< "+_logger_method);
//		}
//	}
	
	/**
	 * Creates a new user. It creates the user in Authentication and Identity repository
	 * If an exception is thrown during the create process, the system tries to cleanup the data.
	 * 
	 * @param ldapUserVO
	 * @throws TrivialPasswordException
	 * @throws SSOException 
	 */
	public void createUser(LdapUserVO ldapUserVO) throws TrivialPasswordException, SSOException{
		String _logger_method="createUser";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}

		String presentationUid = ldapUserVO.getUid();
		String storedUid = convertUidForStorage(presentationUid);
		ldapUserVO.setUid( storedUid );
		
		try{
			// Do not create the user in Authentication Directory if the UID contains "\". 
			// This is the domain \ user delimiter so we are dealing with an employee
			if(presentationUid.indexOf(UptConstants.DOMAIN_USER_DELIMITER)==-1){
				if(ldapUserVO.getMigrationFlag()==null){
					ldapUserVO.setMigrationFlag(MigrationFlagEnum.Y);
				}
				ldapUserVO.setRoleAuthInstance(roleAuthInstance);
				ldapAuthenticationDAO.createUser(ldapUserVO);
			}
			
			ldapIdentityDAO.createUser(ldapUserVO);
		}
		catch(InvalidAttributeValueException iavEx){
			removeUser(presentationUid);
			if(iavEx.getMessage().indexOf( ERROR_PASSWORD_TRIVIALITY_CHECK )>-1){
				throw new TrivialPasswordException();
			}
			else{
				throw new SSOException(iavEx.toString());
			}
		}
		catch(Exception ex){
			// catch all other exceptions
			removeUser(presentationUid);
			throw new SSOException(ex.toString());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
	}


//	/**
//	 * Creates a new SSO user from a temporary user.
//	 * 
//	 * @param ldapUserVO
//	 */
//	public void createUserFromTemp(LdapUserVO ldapUserVO){
//		String _logger_method="createUserFromTemp";
//		if (logger.isDebugEnabled()) {
//			logger.debug("> "+_logger_method);
//		}
//		
//		String presentationUid = ldapUserVO.getUid();
//		ldapUserVO.setUid( convertUidForStorage(presentationUid) );
//		
//		ldapIdentityDAO.updateUser(ldapUserVO);
//		ldapIdentityDAO.removeTempPasswordForUser(ldapUserVO);
//
//		// Do not create the user in Authentication Directory if the UID contains "\". 
//		// This is the domain \ user delimiter so we are dealing with an employee
//		if(presentationUid.indexOf(UptConstants.DOMAIN_USER_DELIMITER)==-1){
//			if(ldapUserVO.getMigrationFlag()==null){
//				ldapUserVO.setMigrationFlag(MigrationFlagEnum.Y);
//			}
//			ldapUserVO.setRoleAuthInstance(roleAuthInstance);
//			ldapAuthenticationDAO.createUser(ldapUserVO);
//		}
//		
//		if (logger.isDebugEnabled()) {
//			logger.debug("< "+_logger_method);
//		}
//	}
	
	
	/**
	 * Updates a user. It updates the user in Authentication and Identity repository
	 * 
	 * @param ldapUserVO
	 * @throws RepeatedPasswordException
	 * @throws TrivialPasswordException 
	 * @throws SSOException 
	 */
	public void updateUser(LdapUserVO ldapUserVO) throws RepeatedPasswordException, TrivialPasswordException, SSOException{
		String _logger_method="updateUser";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}

		String presentationUid = ldapUserVO.getUid();
		ldapUserVO.setUid( convertUidForStorage(presentationUid) );
		
		ldapIdentityDAO.updateUser(ldapUserVO);
		// Do not update the user in Authentication Directory if the UID contains "\". 
		// This is the domain \ user delimiter so we are dealing with an employee
		if(presentationUid.indexOf(UptConstants.DOMAIN_USER_DELIMITER)==-1){
			try{
				if(StringUtils.isNotBlank(ldapUserVO.getPassword())){
					ldapUserVO.setMigrationFlag(MigrationFlagEnum.Y);
				}
				ldapAuthenticationDAO.updateUser(ldapUserVO);
			}
			catch(InvalidAttributeValueException iavEx){
				if(iavEx.getMessage().indexOf( ERROR_PASSWORD_IN_HISTORY )>-1){
					throw new RepeatedPasswordException();
				}
				else if(iavEx.getMessage().indexOf( ERROR_PASSWORD_TRIVIALITY_CHECK )>-1){
					throw new TrivialPasswordException();
				}
				else{
					throw new SSOException(iavEx.toString());
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
	}
	
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
	public void updateUserPassword(String uid, String password, String oldPassword, boolean isUserChangingItsOwnPassword) throws RepeatedPasswordException, TrivialPasswordException, SSOException, Exception{
		String _logger_method="updateUserPassword";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
		
		String storedUid = convertUidForStorage(uid);
		
		LdapUserVO ldapUserVO = new LdapUserVO();
		ldapUserVO.setUid(storedUid);
		ldapUserVO.setPassword(password);
		
		try{
			
			
			if (isUserChangingItsOwnPassword)
			{
				LdapContextSource source = new LdapContextSource();
				source.setUrl(authURL);
				source.setBase(authBaseDN);
				source.setUserDn("uid="+storedUid+","+userDnAuthInstance);
				source.setPassword(oldPassword);
				source.afterPropertiesSet();
				
				LdapTemplate userLdapTemplate = new LdapTemplate(source);
				ldapAuthenticationDAO.updateUserPassword(ldapUserVO,userLdapTemplate);
			}else{
				ldapAuthenticationDAO.updateUser(ldapUserVO);
			}
		}
		catch(InvalidAttributeValueException iavEx){
			if(iavEx.getMessage().indexOf( ERROR_PASSWORD_IN_HISTORY )>-1){
				throw new RepeatedPasswordException();
			}
			else if(iavEx.getMessage().indexOf( ERROR_PASSWORD_TRIVIALITY_CHECK )>-1){
				throw new TrivialPasswordException();
			}
			else{
				throw new SSOException(iavEx.toString());
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
	}
	
	
	/**
	 * Removes a user from the directory
	 * 
	 * @param uid
	 */
	public void removeUser(String uid){
		String _logger_method="removeUser";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}

		String storedUid = convertUidForStorage(uid);
		try{
			if( ldapIdentityDAO.findUserByUid(storedUid)!=null ){
				ldapIdentityDAO.removeUser(storedUid);
			}
		}
		catch (Exception e) {
			logger.error("Exception while trying to remove user from identity repository: " + uid, e);
		}
		
		try{
			if( ldapAuthenticationDAO.findUserByUid(storedUid)!=null ){
				ldapAuthenticationDAO.removeUser(storedUid);
			}
		}
		catch (Exception e) {
			logger.error("Exception while trying to remove user from authentication repository: " + uid, e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
	}
	
	
	/**
	 * Finds an user by uid.
	 * If fullDataSet is set to true, it returns the data from Identity 
	 * 
	 * @param uid
	 * @param fullDataSet
	 * @return
	 */
	public LdapUserVO findUserByUid(String uid, boolean fullDataSet){
		String _logger_method="findUserByUid";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}

		
		String storedUid = convertUidForStorage(uid);
		
		LdapUserVO ldapUserVO = ldapIdentityDAO.findUserByUid(storedUid);
		if(ldapUserVO!=null){
			if(fullDataSet){
				LdapUserVO authInstanceUser = ldapAuthenticationDAO.findUserByUid(storedUid);
				if(authInstanceUser!=null){
					ldapUserVO.setMigrationFlag( authInstanceUser.getMigrationFlag() );
					ldapUserVO.setPasswordExpirationTime( authInstanceUser.getPasswordExpirationTime() );
					ldapUserVO.setPasswordRetryCount( authInstanceUser.getPasswordRetryCount() );
					ldapUserVO.setRetryCountResetTime( authInstanceUser.getRetryCountResetTime() );	
					ldapUserVO.setPassword(authInstanceUser.getPassword());
				}
			}
			ldapUserVO.setUid( uid );
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return ldapUserVO;
	}
	
	
	/**
	 * Finds a user based on the app alias
	 * 
	 * @param alias
	 * @param memberApp
	 * @return
	 */
	public LdapUserVO findUserByAlias(String alias, MemberAppEnum memberApp){
		String _logger_method="findUserByAlias";
		if (logger.isTraceEnabled()) {
			logger.debug("> "+_logger_method);
		}

		String aliasAttrKey = null;
		if(memberApp==MemberAppEnum.UPT){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_1;
		}
		else if(memberApp==MemberAppEnum.CRP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_2;
		}
//		else if(memberApp==MemberAppEnum.TDFX){
//			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
//		}
		else if(memberApp==MemberAppEnum.ERP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
		}
		else if(memberApp==MemberAppEnum.SDP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_4;
		}		
		
		LdapUserVO ldapUserVO = ldapIdentityDAO.findUserByAlias(aliasAttrKey, alias);
		if(ldapUserVO!=null){
			ldapUserVO.setUid( convertUidForPresentation(ldapUserVO.getUid()) );
			LdapUserVO authInstanceUser = ldapAuthenticationDAO.findUserByUid(ldapUserVO.getUid());
			if(authInstanceUser!=null){
				ldapUserVO.setMigrationFlag( authInstanceUser.getMigrationFlag() );
				ldapUserVO.setPasswordExpirationTime( authInstanceUser.getPasswordExpirationTime() );
				ldapUserVO.setPasswordRetryCount( authInstanceUser.getPasswordRetryCount() );
				ldapUserVO.setRetryCountResetTime( authInstanceUser.getRetryCountResetTime() );	
				ldapUserVO.setPassword(authInstanceUser.getPassword());
			}
		}
		
		if (logger.isTraceEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return ldapUserVO;
	}
	
	/**
	 * Find the users with the aliases specified
	 * 
	 * @param aliasList
	 * @param memberApp
	 * @return
	 */
	public List<LdapUserVO> findUsersForAliases(List<String> aliasList, MemberAppEnum memberApp){
		String _logger_method="findUsersForAliases";
		if (logger.isTraceEnabled()) {
			logger.debug("> "+_logger_method);
		}
		
		String aliasAttrKey = null;
		if(memberApp==MemberAppEnum.UPT){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_1;
		}
		else if(memberApp==MemberAppEnum.CRP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_2;
		}
//		else if(memberApp==MemberAppEnum.TDFX){
//			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
//		}
		else if(memberApp==MemberAppEnum.ERP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
		}
		else if(memberApp==MemberAppEnum.SDP){
			aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_4;
		}		
		
		List<LdapUserVO> ldapUserList = ldapIdentityDAO.findUsersForAliases(aliasAttrKey, aliasList);
		if(ldapUserList == null){
			ldapUserList = new ArrayList<LdapUserVO>();
		}
		for(LdapUserVO ldapUserVO : ldapUserList){
			ldapUserVO.setUid( convertUidForPresentation(ldapUserVO.getUid()) );
		}

		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return ldapUserList;
	}


	
	/**
	 * Searches the LDAP server for users
	 * 
	 * @param ssoUserSearchCriteria
	 * @return
	 */
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria ssoUserSearchCriteria){
		String _logger_method="searchUser";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}

		List<FilterAttribute> filters = new ArrayList<FilterAttribute>();
		List<FilterAttribute> nonFilters = new ArrayList<FilterAttribute>();
		
		int idx = 0;
		for(MemberAppEnum memberApp :  ssoUserSearchCriteria.getMemberAppCollection()){
			if (memberApp == MemberAppEnum.UPT) 
			{
				filters.add(new FilterAttribute(
						LdapAttrContants.ATTR_TDSISSOUSER_APPID_1
						, ssoUserSearchCriteria.getMemberAppUsernames().containsKey(MemberAppEnum.UPT)
	                     ? ssoUserSearchCriteria.getMemberAppUsernames().get(MemberAppEnum.UPT)
	                     : ""
						));
			} else {
				if(ssoUserSearchCriteria.isOnlyActiveAppMembers())
				{
					filters.add(new FilterAttribute(
							LdapAttrContants.ATTR_NS_ROLE_DN
							, memberApp.name()
							));
				}
				else{
					if(memberApp==MemberAppEnum.CRP)
					{
						filters.add(new FilterAttribute(
								LdapAttrContants.ATTR_TDSISSOUSER_APPID_2
								, ssoUserSearchCriteria.getMemberAppUsernames().containsKey(MemberAppEnum.CRP)
			                     ? ssoUserSearchCriteria.getMemberAppUsernames().get(MemberAppEnum.CRP)
			                     : ""
			                   ));
					}
//					else if(memberApp==MemberAppEnum.TDFX)
//					{
//						filters.add(new FilterAttribute(
//								LdapAttrContants.ATTR_TDSISSOUSER_APPID_3
//								, ssoUserSearchCriteria.getMemberAppUsernames().containsKey(MemberAppEnum.TDFX)
//			                     ? ssoUserSearchCriteria.getMemberAppUsernames().get(MemberAppEnum.TDFX)
//			                     : ""
//								));
//					}	
					
					else if(memberApp==MemberAppEnum.ERP)
					{
						filters.add(new FilterAttribute(
								LdapAttrContants.ATTR_TDSISSOUSER_APPID_3
								, ssoUserSearchCriteria.getMemberAppUsernames().containsKey(MemberAppEnum.ERP)
			                     ? ssoUserSearchCriteria.getMemberAppUsernames().get(MemberAppEnum.ERP)
			                     : ""
								));
					}			
					else if(memberApp==MemberAppEnum.SDP)
					{
						filters.add(new FilterAttribute(
								LdapAttrContants.ATTR_TDSISSOUSER_APPID_4
								, ssoUserSearchCriteria.getMemberAppUsernames().containsKey(MemberAppEnum.SDP)
			                     ? ssoUserSearchCriteria.getMemberAppUsernames().get(MemberAppEnum.SDP)
			                     : ""
								));
					}								
					//add the other applications
				}
			}
			idx++;
		}
		//enrolled apps
		for(MemberAppEnum memberApp :  ssoUserSearchCriteria.getMemberAppEnrolled())
		{
			filters.add(new FilterAttribute(
					LdapAttrContants.ATTR_NS_ROLE_DN
					, memberApp.name()
					));
		}
		//de-enrolled apps
		for(MemberAppEnum memberApp :  ssoUserSearchCriteria.getMemberAppDeenrolled())
		{
			nonFilters.add(new FilterAttribute(
					LdapAttrContants.ATTR_NS_ROLE_DN
					, memberApp.name()
					));
		}

		String aliasAttrKey = null;
		if(ssoUserSearchCriteria.getAliasType() != null && ssoUserSearchCriteria.getAliasList().size() > 0)
		{
			if(ssoUserSearchCriteria.getAliasType()==MemberAppEnum.UPT){
				aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_1;
			}
			else if(ssoUserSearchCriteria.getAliasType()==MemberAppEnum.CRP){
				aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_2;
			}
//			else if(ssoUserSearchCriteria.getAliasType()==MemberAppEnum.TDFX){
//				aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
//			}
			else if(ssoUserSearchCriteria.getAliasType()==MemberAppEnum.ERP){
				aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_3;
			}
			else if(ssoUserSearchCriteria.getAliasType()==MemberAppEnum.SDP){
				aliasAttrKey = LdapAttrContants.ATTR_TDSISSOUSER_APPID_4;
			}
		}		

		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getUid())){
			String storedUid = convertUidForStorage(ssoUserSearchCriteria.getUid());
			ssoUserSearchCriteria.setUid(storedUid);
		}
		List<LdapUserVO> ldapUserList = ldapIdentityDAO.searchUser(
				ssoUserSearchCriteria, 
				filters, 
				nonFilters, 
				aliasAttrKey,
				ssoUserSearchCriteria.getAliasList()		
				);
		
		for(LdapUserVO ldapUserVO : ldapUserList){
			ldapUserVO.setUid( convertUidForPresentation(ldapUserVO.getUid()) );
		}
		
		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getUid())){
			String uid = convertUidForPresentation(ssoUserSearchCriteria.getUid());
			ssoUserSearchCriteria.setUid(uid);
		}		
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return ldapUserList;
	}
	
	
	/**
	 * Finds an employee
	 * 
	 * @param employeeId
	 * @param userDomain
	 * @return
	 */
	public LdapUserVO findEmployee(String employeeId, String userDomain) throws Exception{
		String _logger_method="findEmployee";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
		
		LdapUserVO ldapUserVO = getAdService().findUserById(employeeId, userDomain);
		
		if(ldapUserVO == null){
			logger.info("User "+employeeId +" is not found on "+userDomain);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return ldapUserVO;
	}

	public boolean isEmployeeInGroup(String uid, String domain, String[] groupNames) throws Exception{
				
		return getAdService().isUserMemberOf(groupNames,uid, domain);
	}
	

	/**
	 * Finds an employee
	 * 
	 * @param employeeId
	 * @param userDomain
	 * @return
	 */
	public List<LdapUserVO> searchEmployee(SsoUserSearchCriteria searchCriteria, String domain) throws Exception{
		String _logger_method="searchEmployee";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
		
		List<LdapUserVO> userList = getAdService().searchUser( searchCriteria,domain);
				
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return userList;
	}
	
	
	public List<LdapUserVO> searchEmployee(SsoUserSearchCriteria searchCriteria) throws Exception{
		String _logger_method="searchEmployee";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
		
		List<LdapUserVO> userList = getAdService().searchUser( searchCriteria);
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
		return userList;
	}
	
	
	/**
	 * cleanup User Before Update
	 * @param uid
	 */
	public void cleanupUserBeforeUpdate(LdapUserVO ldapUserVO)
	{
		String _logger_method="cleanupUserBeforeUpdate";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}		
		
		String presentationUid = ldapUserVO.getUid();
		ldapUserVO.setUid( convertUidForStorage(presentationUid) );
		
		ldapIdentityDAO.cleanupUserBeforeUpdate(ldapUserVO);
		
		if (logger.isDebugEnabled()) {
			logger.debug("< "+_logger_method);
		}
	}
	
	/**
	 * checks if the user's password is correct
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean authenticate(String uid, String password){
		return ldapAuthenticationDAO.authenticate(uid, password);
	}
	
	public boolean authenticate(String uid, String password, String userDN, String baseDN, String url) {
		return ldapAuthenticationDAO.authenticate(uid, password, userDN, baseDN, url);
	}
	
	/**
	 * Replaces the domain delimiter used for persistence, with \ 
	 * 
	 * @param persistedUid
	 * @return
	 */
	private String convertUidForPresentation(String persistedUid){
		String presentationUid;
		
		presentationUid = StringUtils.replace(persistedUid, persistedDelimiter, UptConstants.DOMAIN_USER_DELIMITER);
		
		return presentationUid;
	}

	/**
	 * Replaces the domain delimiter \ used for presentation with the domain 
	 * delimiter used for persistence
	 * 
	 * @param presentationUid
	 * @return
	 */
	private String convertUidForStorage(String presentationUid){
		String persistedUid;
		
        Matcher m = backslashPattern.matcher(presentationUid); 		// get a matcher object
        persistedUid = m.replaceFirst(persistedDelimiter);
		
		return persistedUid;
	}
	
	/**
	 * @param ldapAuthenticationDAO the ldapAuthenticationDAO to set
	 */
	public void setLdapAuthenticationDAO(LdapAuthenticationDAO ldapAuthenticationDAO) {
		this.ldapAuthenticationDAO = ldapAuthenticationDAO;
	}
	/**
	 * @param ldapIdentityDAO the ldapIdentityDAO to set
	 */
	public void setLdapIdentityDAO(LdapIdentityDAO ldapIdentityDAO) {
		this.ldapIdentityDAO = ldapIdentityDAO;
	}	

	/**
	 * @param propertyService the propertyService to set
	 */
	public void setPropertyService(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	public AdService getAdService()
	{
		return adService;
	}

	public void setAdService(AdService adService)
	{
		this.adService = adService;
	}

	public String getAuthURL() {
		return authURL;
	}

	public void setAuthURL(String authURL) {
		this.authURL = authURL.trim();
	}

	public String getAuthBaseDN() {
		return authBaseDN;
	}

	public void setAuthBaseDN(String authBaseDN) {
		this.authBaseDN = authBaseDN.trim();
	}

}
