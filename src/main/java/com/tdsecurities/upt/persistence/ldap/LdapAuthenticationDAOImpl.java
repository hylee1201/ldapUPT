package com.tdsecurities.upt.persistence.ldap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

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

import com.tdsecurities.upt.constants.LdapAttrContants;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.MigrationFlagEnum;
import com.tdsecurities.upt.persistence.LdapAuthenticationDAO;

/**
 * 
 * @author brinzf2
 *
 */
public class LdapAuthenticationDAOImpl implements LdapAuthenticationDAO {
	private static final Logger logger = Logger.getLogger(LdapAuthenticationDAOImpl.class);
	
	private static final String LDAP_DATE_TIME_FORMAT = "yyyyMMddHHmmss'Z'";
	private static final String LDAP_TIME_ZONE_ID = "GMT";

	private LdapTemplate ldapTemplate;

	
	/**
	 * Finds an user by uid
	 * 
	 * @param uid
	 * @return
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

	public void updateUserPassword(LdapUserVO ldapUserVO, LdapTemplate userLdapTemplate){
		Name dn = buildDn(ldapUserVO.getUid());
		
		Attribute attr = new BasicAttribute(LdapAttrContants.ATTR_PASSWORD, ldapUserVO.getPassword());
	    ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
	    userLdapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
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
		                      LdapAttrContants.ATTR_EMPLOYEE_TYPE,
		                      LdapAttrContants.ATTR_PASSWORD_EXPIRATION_TIME,
		                      LdapAttrContants.ATTR_PASSWORD_RETRY_COUNT,
		                      LdapAttrContants.ATTR_RETY_COUNT_RESET_TIME};	
		
		return attrAray;
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
																"inetorgperson" });
		context.setAttributeValue(LdapAttrContants.ATTR_UID, ldapUserVO.getUid());
		if(StringUtils.isNotBlank(ldapUserVO.getFirstName()) && StringUtils.isNotBlank(ldapUserVO.getLastName())){
			context.setAttributeValue(LdapAttrContants.ATTR_CN, ldapUserVO.getFirstName() + " " + ldapUserVO.getLastName());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getLastName())){
			context.setAttributeValue(LdapAttrContants.ATTR_LAST_NAME, ldapUserVO.getLastName());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getLastName())){
			context.setAttributeValue(LdapAttrContants.ATTR_FIRST_NAME, ldapUserVO.getFirstName());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getLastName())){
			context.setAttributeValue(LdapAttrContants.ATTR_EMAIL_ADDRESS, ldapUserVO.getEmail());
		}
		if(StringUtils.isNotBlank(ldapUserVO.getPassword())){
			context.setAttributeValue(LdapAttrContants.ATTR_PASSWORD, ldapUserVO.getPassword());
		}				
		if(StringUtils.isNotBlank(ldapUserVO.getRoleAuthInstance())){
			context.setAttributeValue(LdapAttrContants.ATTR_NS_ROLE_DN, ldapUserVO.getRoleAuthInstance());
		}
		if(ldapUserVO.getMigrationFlag()!=null){
			context.setAttributeValue(LdapAttrContants.ATTR_EMPLOYEE_TYPE, ldapUserVO.getMigrationFlag().name());
		}
	}

	/**
	 * 
	 * @return
	 */
	private ContextMapper getContextMapper() {
		return new UserContextMapper();
	}
	
	/**
	 * update userExpirationTime after user is changing its own password
	 */
	public void updateUserExpirationTime(LdapUserVO ldapUserVO)
	{
		Name dn = buildDn(ldapUserVO.getUid());

		//format password expiration time
		DateFormat formatter = createLdapDateFormat();
	    String passwordExpirationTime = formatter.format(ldapUserVO.getPasswordExpirationTime());
		
		BasicAttribute attribute = new BasicAttribute(LdapAttrContants.ATTR_PASSWORD_EXPIRATION_TIME, passwordExpirationTime); 
		ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attribute);
		
		ModificationItem[] modificationItemArray = new ModificationItem[1];		
		modificationItemArray[0] = modificationItem;
		
		ldapTemplate.modifyAttributes(dn, modificationItemArray);
	}
	
	/**
	 * checks if the user's password is correct
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean authenticate(String uid, String password)
	{
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));
		filter.and(new EqualsFilter(LdapAttrContants.ATTR_UID, uid));

		return ldapTemplate.authenticate(
				//DistinguishedName.EMPTY_PATH, 
				buildRootDn(),
				filter.toString(), 
				password
				);
	}
	
	public boolean authenticate(String uid, String password, String userDN, String baseDN, String url)
	{
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));
		filter.and(new EqualsFilter(LdapAttrContants.ATTR_UID, uid));

		LdapContextSource source = new LdapContextSource();
		source.setUrl(url);
		source.setBase(baseDN);
		source.setUserDn("uid="+uid+","+userDN);
		source.setPassword(password);
		try {
			source.afterPropertiesSet();
		} catch (Exception e) {
			logger.error(e);
		}
		
		LdapTemplate userLdapTemplate = new LdapTemplate(source);
		return userLdapTemplate.authenticate(
				//DistinguishedName.EMPTY_PATH, 
				buildRootDn(),
				filter.toString(), 
				password
				);
	}

	/**
	 * @param ldapTemplate the ldapTemplate to set
	 */
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	protected static DateFormat createLdapDateFormat() {
		DateFormat format = new SimpleDateFormat(LDAP_DATE_TIME_FORMAT);
		format.setTimeZone(TimeZone.getTimeZone(LDAP_TIME_ZONE_ID));
		return format;
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
			if(StringUtils.isNotBlank(context.getStringAttribute(LdapAttrContants.ATTR_EMPLOYEE_TYPE))){
				MigrationFlagEnum migrationFlag = null;
				try{
					migrationFlag = MigrationFlagEnum.valueOf(context.getStringAttribute(LdapAttrContants.ATTR_EMPLOYEE_TYPE));
				}
				catch(Exception e){}
				ldapUserVO.setMigrationFlag(migrationFlag);
			}
			
			DateFormat formatter = createLdapDateFormat();
			
			String strAttribute;
			Date dateAttr;
			int intAttr;
			
			try {
				strAttribute = context.getStringAttribute(LdapAttrContants.ATTR_PASSWORD_EXPIRATION_TIME);
				if(strAttribute!=null){
					dateAttr = formatter.parse(strAttribute);
				}
				else{
					dateAttr = null;
				}
			} catch (ParseException e) {
				logger.error("Exception reading 'passwordExpirationTime' attribute: " + e);
				dateAttr = null;
			}
			ldapUserVO.setPasswordExpirationTime(dateAttr);
			
			try{
				strAttribute = context.getStringAttribute(LdapAttrContants.ATTR_PASSWORD_RETRY_COUNT);
				if(strAttribute!=null){
					intAttr = Integer.parseInt(strAttribute);
				}
				else{
					intAttr = 0;
				}
			}
			catch(NumberFormatException  e){
				logger.error("Exception reading 'passwordRetryCount' attribute: " + e);
				intAttr = 0;
			}
			ldapUserVO.setPasswordRetryCount(intAttr);

			try {
				strAttribute = context.getStringAttribute(LdapAttrContants.ATTR_RETY_COUNT_RESET_TIME);
				if(strAttribute!=null){
					dateAttr = formatter.parse(strAttribute);
				}
				else{
					dateAttr = null;
				}
			} catch (ParseException e) {
				logger.error("Exception reading 'retryCountResetTime' attribute: " + e);
				dateAttr = null;
			}
			ldapUserVO.setRetryCountResetTime(dateAttr);
			
			return ldapUserVO;
		}
	}
}
