package com.tdsecurities.upt.persistence;

import org.springframework.ldap.core.LdapTemplate;

import com.tdsecurities.upt.model.user.LdapUserVO;

/**
 * 
 * @author brinzf2
 *
 */
public interface LdapAuthenticationDAO {
	
	/**
	 * Finds an user by uid
	 * 
	 * @param uid
	 * @return
	 */
	public LdapUserVO findUserByUid(String uid);

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
	 * checks if the user's password is correct
	 * @param uid
	 * @param password
	 * @return
	 */
	public boolean authenticate(String uid, String password);
	
	public boolean authenticate(String uid, String password, String userDN, String baseDN, String url);

	public void updateUserExpirationTime(LdapUserVO ldapUserVO);
	
	public void updateUserPassword(LdapUserVO ldapUserVO, LdapTemplate userLdapTemplate);
}
