package com.tdsecurities.upt.persistence.ldap;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;

import com.tdsecurities.upt.constants.LdapAttrContants;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.persistence.AdTdsDAO;

public class AdTdsDAOImpl implements AdTdsDAO {
	
	public static final String DN_FILTER_NAME		= "rnet";
	
	private LdapTemplate ldapTemplate;

	
	/**
	 * Finds an user by id
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LdapUserVO findUserById(String employeeId){
		LdapUserVO ldapUserVO = null; 

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));		
		filter.and(new EqualsFilter(LdapAttrContants.ATTR_S_AMA_ACCOUNT_NAME, employeeId));
		List<LdapUserVO> userList = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
		
		userList = filter(userList);
		
		if(userList!=null && userList.size()>0){
			ldapUserVO = userList.get(0);
		}
		
		return ldapUserVO;
	}

	
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria){
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapAttrContants.VALUE_PERSON));	
		if(StringUtils.isNotBlank(searchCriteria.getFirstName())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_FIRST_NAME, searchCriteria.getFirstName()));
		}
		if(StringUtils.isNotBlank(searchCriteria.getLastName())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_LAST_NAME, searchCriteria.getLastName()));
		}
		if(StringUtils.isNotBlank(searchCriteria.getEmail())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_EMAIL_ADDRESS, searchCriteria.getEmail()));
		}
		if(StringUtils.isNotBlank(searchCriteria.getUid())){
			filter.and(new WhitespaceWildcardsFilter(LdapAttrContants.ATTR_S_AMA_ACCOUNT_NAME, searchCriteria.getUid()));
		}
		
		List<String> aliasList = searchCriteria.getAliasList();
		if ( aliasList.size() > 0)
		{
			OrFilter orFilter = new OrFilter();
		
			for(String alias : aliasList){
				orFilter.or(new EqualsFilter(LdapAttrContants.ATTR_S_AMA_ACCOUNT_NAME, alias));
			}
			
			filter.and(orFilter);
		}
		List<LdapUserVO> userList = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
		
		
		
		return  filter(userList);
	}
	
	/**
	 * 
	 * @return
	 */
	private ContextMapper getContextMapper() {
		return new UserContextMapper();
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
			ldapUserVO.setUid(context.getStringAttribute(LdapAttrContants.ATTR_S_AMA_ACCOUNT_NAME));
			ldapUserVO.setFirstName(context.getStringAttribute(LdapAttrContants.ATTR_FIRST_NAME));
			ldapUserVO.setLastName(context.getStringAttribute(LdapAttrContants.ATTR_LAST_NAME));
			ldapUserVO.setEmail(context.getStringAttribute(LdapAttrContants.ATTR_EMAIL_ADDRESS));
			ldapUserVO.setCompany(context.getStringAttribute(LdapAttrContants.ATTR_COMPANY));
			ldapUserVO.setOccupation(context.getStringAttribute(LdapAttrContants.ATTR_TITLE));
			ldapUserVO.setPhoneNumber(context.getStringAttribute(LdapAttrContants.ATTR_PHONE));
			ldapUserVO.setDistiguishedName(context.getStringAttribute(LdapAttrContants.ATTR_DISTIGUISHED_NAME));
			
			String memberArray[] = context.getStringAttributes(LdapAttrContants.ATTR_MEMBER_OF);
			if(memberArray!=null){
				for(int i=0; i<memberArray.length; i++){
					ldapUserVO.addGroupMember(memberArray[i]);
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

	private List<LdapUserVO> filter(List<LdapUserVO> userList){
		if(userList !=null){
			for(Iterator<LdapUserVO> itr= userList.iterator(); itr.hasNext();){
				LdapUserVO userVO = itr.next();
				if(userVO.getDistiguishedName()!=null ){
					boolean deleteEntry = false;
					StringTokenizer tokens = new StringTokenizer(userVO.getDistiguishedName(), ",");
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken().toLowerCase().trim();
						if(token.indexOf("dc=")==0 && token.indexOf(DN_FILTER_NAME)!= -1){
							deleteEntry = true;
							break;
						}
					}
					if(deleteEntry){
						itr.remove();
					}
				}
			}
		}
		
		return userList;
	}
}
