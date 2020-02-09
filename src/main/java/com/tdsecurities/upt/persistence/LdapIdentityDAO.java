/**
 * 
 */
package com.tdsecurities.upt.persistence;

import java.util.List;

import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.persistence.ldap.FilterAttribute;

/**
 * @author brinzf2
 *
 */
public interface LdapIdentityDAO {

	/**
	 * Returns the base DN
	 * 
	 * @return
	 */
	public String getBaseDN();
	
	/**
	 * Creates a new user
	 * 
	 * @param ldapUserVO
	 */
	public void createUser(LdapUserVO ldapUserVO);

	/**
	 * Updates a user
	 * 
	 * @param ldapUserVO
	 */
	public void updateUser(LdapUserVO ldapUserVO);
	
	/**
	 * Removes a user from the repository
	 * 
	 * @param uid
	 */
	public void removeUser(String uid);	
	
	/**
	 * Removes the temporary password for a user
	 *  
	 * @param ldapUserVO
	 */
	public void removeTempPasswordForUser(LdapUserVO ldapUserVO);	
	
	/**
	 * Finds an user by uid
	 * 
	 * @param uid
	 * @return
	 */
	public LdapUserVO findUserByUid(String uid);

	/**
	 * Finds a user based on the app alias
	 * 
	 * @param aliasAttrKey
	 * @param alias
	 * @return
	 */
	public LdapUserVO findUserByAlias(String aliasAttrKey, String alias);

	/**
	 * Find the users with the aliases specified
	 * 
	 * @param aliasAttrKey
	 * @param aliasList
	 * @return
	 */
	public List<LdapUserVO> findUsersForAliases(String aliasAttrKey, List<String> aliasList);	
	
	/**
	 * Searches the LDAP server for users
	 * 
	 * @param ssoUserSearchCriteria
	 * @param roleAttrSearchArray
	 * @param roleValueSearchArray
	 * @return
	 */
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria ssoUserSearchCriteria, List<FilterAttribute> filters, List<FilterAttribute> nonFilters, String aliasType, List<String> aliasList);
	
	/**
	 * cleanup User Before Update
	 * @param uid
	 */
	public void cleanupUserBeforeUpdate(LdapUserVO ldapUserVO);
	
}
