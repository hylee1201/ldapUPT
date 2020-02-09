package com.tdsecurities.upt.service;

import java.util.List;

import com.tdsecurities.upt.exception.InvalidDomainException;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.utilities.AdConfig;

public interface AdService
{
	/**
	 * Finds an user by id
	 * 
	 * @param uid
	 * @return
	 */
	public LdapUserVO findUserById(String employeeId, String domain) throws InvalidDomainException;
	
	public LdapUserVO findUserById(String employeeId) throws InvalidDomainException;
	
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria, String domain) throws InvalidDomainException;
	
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria) throws InvalidDomainException;
	
	public boolean isUserMemberOf(String[] groupName, String employeeId, String domain) throws InvalidDomainException;
	
	public List<AdConfig> findDomainConfig(String domain) throws InvalidDomainException;
	

}
