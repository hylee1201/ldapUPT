package com.tdsecurities.upt.persistence.ldap;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.NotFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;

import com.tdsecurities.upt.constants.InetUserStatusEnum;
import com.tdsecurities.upt.constants.LdapAttrContants;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.persistence.LdapIdentityDAO;

/**
 * 
 * @author brinzf2
 *
 */
public class LdapIdentityDAOImpl implements LdapIdentityDAO {
	private static final Logger logger = Logger.getLogger(LdapIdentityDAOImpl.class);

	private LdapTemplate ldapTemplate;

	/**
	 * Returns the base DN
	 * 
	 * @return
	 */
	public String getBaseDN(){
		String baseDN = ((LdapContextSource)ldapTemplate.getContextSource()).getBaseLdapPathAsString();
		return baseDN;
	}

	/**
	 * Creates a new user
	 * 
	 * @param ldapUserVO
	 */
	public void createUser(LdapUserVO ldapUserVO){
		DirContextAdapter context = new DirContextAdapter(buildDn(ldapUserVO));
		mapToContext(ldapUserVO, context);
		ldapTemplate.bind(context);
	}

	/**
	 * Updates a user
	 * 
	 * @param ldapUserVO
	 */
	public void updateUser(LdapUserVO ldapUserVO){
		Name dn = buildDn(ldapUserVO.getUid());
		DirContextOperations context = ldapTemplate.lookupContext(dn);
		mapToContext(ldapUserVO, context);
		ldapTemplate.modifyAttributes(context);
	}
	
	/**
	 * Removes a user from the repository
	 * 
	 * @param uid
	 */
	public void removeUser(String uid){
		Name dn = buildDn(uid);
		ldapTemplate.unbind(dn);
	}
	
	/**
	 * Removes the temporary password for a user
	 *  
	 * @param ldapUserVO
	 */
	public void removeTempPasswordForUser(LdapUserVO ldapUserVO){
		Name dn = buildDn(ldapUserVO.getUid());
		DirContextOperations context = ldapTemplate.lookupContext(dn);
		context.removeAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_12, ldapUserVO.getTDtdsissouserAppID12());
		ldapTemplate.modifyAttributes(context);
	}

	/**
	 * cleanup User Before Update
	 * @param uid
	 */
	public void cleanupUserBeforeUpdate(LdapUserVO ldapUserVO)
	{
		Name dn = buildDn(ldapUserVO.getUid());
		DirContextOperations context = ldapTemplate.lookupContext(dn);
//		DirContextAdapter  context = (DirContextAdapter)ldapTemplate.lookup(dn, getAttributesOfInterest(), new OperationsContextMapper());
		
		//secret questions & answers
		for(String questionAnswer: ldapUserVO.getQuestionAnswerCollection())
		{
			context.removeAttributeValue(
					LdapAttrContants.ATTR_QUESTION_ANSWER, 
					questionAnswer);
		}
		ldapTemplate.modifyAttributes(context);
		
		// application roles
		ModificationItem[] modificationItemArray = new ModificationItem[ldapUserVO.getRoleCollection().size()];
		int idx = 0;
		for(String applicationRole : ldapUserVO.getRoleCollection()){
			BasicAttribute attribute = new BasicAttribute(LdapAttrContants.ATTR_NS_ROLE_DN, applicationRole); 
			ModificationItem modificationItem = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
			modificationItemArray[idx++] = modificationItem;
		}
		ldapTemplate.modifyAttributes(dn, modificationItemArray);
		
		
		//legacy user
		modificationItemArray = new ModificationItem[ldapUserVO.getLegacyUserApplicationCollection().size()];
		idx = 0;
		for(String legacyUserApplication : ldapUserVO.getLegacyUserApplicationCollection()){
			BasicAttribute attribute = new BasicAttribute(LdapAttrContants.ATTR_LEGACY_USER, legacyUserApplication); 
			ModificationItem modificationItem = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
			modificationItemArray[idx++] = modificationItem;
		}
		ldapTemplate.modifyAttributes(dn, modificationItemArray);	
	}
	
	/**
	 * Finds an user by uid
	 * 
	 * @param uid
	 */
	public LdapUserVO findUserByUid(String uid){
		LdapUserVO ldapUserVO = null; 

		Name dn = buildDn(uid);
		try{
			ldapUserVO = (LdapUserVO)ldapTemplate.lookup(dn, getAttributesOfInterest(), getContextMapper());
		}
		catch(NameNotFoundException nnfEx){
			logger.info("Name not found: " + uid);
			ldapUserVO = null;
		}
		
		return ldapUserVO;
	}

	/**
	 * Finds a user based on the app alias
	 * 
	 * @param aliasAttrKey
	 * @param alias
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LdapUserVO findUserByAlias(String aliasAttrKey, String alias){
		LdapUserVO ldapUserVO = null; 

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));		
		filter.and(new LikeFilter(aliasAttrKey, alias));
		
		List<LdapUserVO> userList = ldapTemplate.search(
												//DistinguishedName.EMPTY_PATH,
												buildRootDn(),
												filter.encode(),
												SearchControls.SUBTREE_SCOPE, 
												getAttributesOfInterest(),
												getContextMapper());
		if(userList!=null && userList.size()>0){
			ldapUserVO = userList.get(0);
		}
		
		return ldapUserVO;
	}
	
	/**
	 * Find the users with the aliases specified
	 * 
	 * @param aliasAttrKey
	 * @param aliasList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LdapUserVO> findUsersForAliases(String aliasAttrKey, List<String> aliasList){
		OrFilter orFilter = new OrFilter();
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));		
		for(String alias : aliasList){
			filter = new AndFilter();
			filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));		
			filter.and(new EqualsFilter(aliasAttrKey, alias));
			orFilter.or(filter);
		}
		
		List<LdapUserVO> userList = ldapTemplate.search(
				//DistinguishedName.EMPTY_PATH, 
				buildRootDn(),
				orFilter.encode(),
				SearchControls.SUBTREE_SCOPE, 
				getAttributesOfInterest(),
				getContextMapper());
		
		return userList;
	}
	
	/**
	 * Searches the LDAP server for users
	 * 
	 * @param ssoUserSearchCriteria
	 * @param roleAttrSearch
	 * @param roleValueSearch
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria ssoUserSearchCriteria, List<FilterAttribute> filters, List<FilterAttribute> nonFilters, String aliasType, List<String> aliasList) {
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));		
		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getFirstName())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_FIRST_NAME, ssoUserSearchCriteria.getFirstName()));
		}
		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getLastName())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_LAST_NAME, ssoUserSearchCriteria.getLastName()));
		}
		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getEmail())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_EMAIL_ADDRESS, ssoUserSearchCriteria.getEmail()));
		}
		if(StringUtils.isNotBlank(ssoUserSearchCriteria.getUid())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_UID, ssoUserSearchCriteria.getUid()));
		}
		if(ssoUserSearchCriteria.getUserStatus()!=null){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_INET_USER_STATUS, ssoUserSearchCriteria.getUserStatus().toString()));
		}
		
		for(FilterAttribute filterAttribute: filters)
		{
			filter.and(new WhitespaceWildcardsFilter(filterAttribute.getName(), filterAttribute.getValue()));
		}
		for(FilterAttribute filterAttribute: nonFilters)
		{
			filter.and(
					new NotFilter(
							new WhitespaceWildcardsFilter(filterAttribute.getName(), filterAttribute.getValue()))
					)
					;
		}
		
		if (aliasType != null && aliasList.size() > 0)
		{
			OrFilter orFilter = new OrFilter();
		
			for(String alias : aliasList){
				orFilter.or(new EqualsFilter(aliasType, alias));
			}
			
			filter.and(orFilter);
		}
		
		List<LdapUserVO> userList = ldapTemplate.search(
											//DistinguishedName.EMPTY_PATH,
											buildRootDn(),
											filter.encode(),
											SearchControls.SUBTREE_SCOPE, 
											getAttributesOfInterest(),
											getContextMapper());
	
		return userList;
	}



	/**
	 * 
	 * @return
	 */
	private ContextMapper getContextMapper() {
		return new UserContextMapper();
	}
	
	/**
	 * Creates an array with the attributes to be returned by LDAP
	 * 
	 * @return
	 */
	private String[] getAttributesOfInterest(){
		String attrAray[] = new String[] { 
		                      LdapAttrContants.ATTR_UID,
		                      LdapAttrContants.ATTR_FIRST_NAME,
		                      LdapAttrContants.ATTR_LAST_NAME,
		                      LdapAttrContants.ATTR_EMAIL_ADDRESS,
		                      LdapAttrContants.ATTR_INET_USER_STATUS,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_1,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_2,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_3,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_4,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_5,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_6,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_7,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_8,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_9,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_10,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_11,
		                      LdapAttrContants.ATTR_TDSISSOUSER_APPID_12,
		                      LdapAttrContants.ATTR_NS_ROLE_DN ,
		                      LdapAttrContants.ATTR_QUESTION_ANSWER,
		                      LdapAttrContants.ATTR_PASSWORD_EXPIRATION_TIME,
		                      LdapAttrContants.ATTR_LEGACY_USER };	
		
		return attrAray;
	}
	
	/**
	 * Builds the Root DN
	 * 
	 * @return
	 */
	private Name buildRootDn(){
		DistinguishedName dn = new DistinguishedName();
		dn.add(LdapAttrContants.ATTR_OU, LdapAttrContants.VALUE_PEOPLE);
		
		return dn;
	}
	
	/**
	 * Builds a DN
	 * 
	 * @param ldapUserVO
	 * @return
	 */
	private Name buildDn(LdapUserVO ldapUserVO) {
		return buildDn(ldapUserVO.getUid());
	}
	
	/**
	 * Builds a DN
	 * 
	 * @param uid
	 * @return
	 */
	private Name buildDn(String uid) {
		DistinguishedName dn = new DistinguishedName();
		dn.add(LdapAttrContants.ATTR_OU, LdapAttrContants.VALUE_PEOPLE);
		dn.add(LdapAttrContants.ATTR_UID, uid);
		
		return dn;
	}
	
	/**
	 * Sets the user attributes into context
	 * 
	 * @param person
	 * @param context
	 */
	private void mapToContext(LdapUserVO ldapUserVO, DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[]{ "top",
																"person", 
																"organizationalperson", 
																"inetorgperson", 
																"inetadmin",
																"inetuser", 
																"iplanet-am-managed-person",
																"iplanet-am-user-service", 
																"iPlanetPreferences",
																"sunAMAuthAccountLockout", 
																"sunFederationManagerDataStore",
																"sunFMSAML2NameIdentifier",
																"sunIdentityServerLibertyPPService", 
																"TDtdsissouserAuxClass" });
		context.setAttributeValue(LdapAttrContants.ATTR_UID, ldapUserVO.getUid());
		if(ldapUserVO.getInetUserStatus()!=null){
			context.setAttributeValue(LdapAttrContants.ATTR_INET_USER_STATUS, ldapUserVO.getInetUserStatus().name());
		}
		context.setAttributeValue(LdapAttrContants.ATTR_CN, ldapUserVO.getFirstName() + " " + ldapUserVO.getLastName());
		context.setAttributeValue(LdapAttrContants.ATTR_FIRST_NAME, ldapUserVO.getFirstName());
		context.setAttributeValue(LdapAttrContants.ATTR_LAST_NAME, ldapUserVO.getLastName());
		context.setAttributeValue(LdapAttrContants.ATTR_EMAIL_ADDRESS, ldapUserVO.getEmail());
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID1())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_1, ldapUserVO.getTDtdsissouserAppID1());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID2())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_2, ldapUserVO.getTDtdsissouserAppID2());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID3())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_3, ldapUserVO.getTDtdsissouserAppID3());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID4())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_4, ldapUserVO.getTDtdsissouserAppID4());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID5())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_5, ldapUserVO.getTDtdsissouserAppID5());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID6())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_6, ldapUserVO.getTDtdsissouserAppID6());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID7())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_7, ldapUserVO.getTDtdsissouserAppID7());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID8())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_8, ldapUserVO.getTDtdsissouserAppID8());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID1())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_9, ldapUserVO.getTDtdsissouserAppID9());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID10())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_10, ldapUserVO.getTDtdsissouserAppID10());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID11())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_11, ldapUserVO.getTDtdsissouserAppID11());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getTDtdsissouserAppID12())){
			context.setAttributeValue(LdapAttrContants.ATTR_TDSISSOUSER_APPID_12, ldapUserVO.getTDtdsissouserAppID12());
		}
		
		for(String role: ldapUserVO.getRoleCollection()){
			context.addAttributeValue(LdapAttrContants.ATTR_NS_ROLE_DN, role);
		}
		
		for(String questionAnswer: ldapUserVO.getQuestionAnswerCollection()){
			context.addAttributeValue(LdapAttrContants.ATTR_QUESTION_ANSWER, questionAnswer);
		}
		
		for(String application: ldapUserVO.getLegacyUserApplicationCollection()){
			context.addAttributeValue(LdapAttrContants.ATTR_LEGACY_USER, application);
		}
	}
	
	
	/**
	 * LDAP user context mapper class
	 * 
	 * @author brinzf2
	 *
	 */
	private static class UserContextMapper extends AbstractContextMapper {
		public Object doMapFromContext(DirContextOperations context) {
			LdapUserVO ldapUserVO = new LdapUserVO();
			ldapUserVO.setUid(context.getStringAttribute(LdapAttrContants.ATTR_UID));
			ldapUserVO.setFirstName(context.getStringAttribute(LdapAttrContants.ATTR_FIRST_NAME));
			ldapUserVO.setLastName(context.getStringAttribute(LdapAttrContants.ATTR_LAST_NAME));
			ldapUserVO.setEmail(context.getStringAttribute(LdapAttrContants.ATTR_EMAIL_ADDRESS));
			if(StringUtils.isNotBlank(context.getStringAttribute(LdapAttrContants.ATTR_INET_USER_STATUS))){
				ldapUserVO.setInetUserStatus(InetUserStatusEnum.valueOf(context.getStringAttribute(LdapAttrContants.ATTR_INET_USER_STATUS)));
			}
			ldapUserVO.setTDtdsissouserAppID1(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_1));
			ldapUserVO.setTDtdsissouserAppID2(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_2));
			ldapUserVO.setTDtdsissouserAppID3(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_3));
			ldapUserVO.setTDtdsissouserAppID4(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_4));
			ldapUserVO.setTDtdsissouserAppID5(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_5));
			ldapUserVO.setTDtdsissouserAppID6(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_6));
			ldapUserVO.setTDtdsissouserAppID7(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_7));
			ldapUserVO.setTDtdsissouserAppID8(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_8));
			ldapUserVO.setTDtdsissouserAppID9(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_9));
			ldapUserVO.setTDtdsissouserAppID10(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_10));
			ldapUserVO.setTDtdsissouserAppID11(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_11));
			ldapUserVO.setTDtdsissouserAppID12(context.getStringAttribute(LdapAttrContants.ATTR_TDSISSOUSER_APPID_12));
			
			//roles
			String rolesArray[] = context.getStringAttributes(LdapAttrContants.ATTR_NS_ROLE_DN);
			if(rolesArray!=null){
				for(int i=0; i<rolesArray.length; i++){
					ldapUserVO.addRole(rolesArray[i]);
				}
			}
			
			//questions & answers
			String questionAnswerArray[] = context.getStringAttributes(LdapAttrContants.ATTR_QUESTION_ANSWER);
			if(questionAnswerArray!=null){
				for(int i=0; i<questionAnswerArray.length; i++){
					ldapUserVO.getQuestionAnswerCollection().add(questionAnswerArray[i]);
				}
			}
			
			//legacy users
			String legacyUserArray[] = context.getStringAttributes(LdapAttrContants.ATTR_LEGACY_USER);
			if(legacyUserArray!=null){
				for(int i=0; i<legacyUserArray.length; i++){
					ldapUserVO.addLegacyUserApplication(legacyUserArray[i]);
				}
			}
			
			return ldapUserVO;
		}
	}
	
	/**
	 * @param ldapTemplate the ldapTemplate to set
	 */
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
}
