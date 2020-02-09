package com.tdsecurities.upt.persistence;

import java.util.List;

import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;

public interface AdTdsDAO {

	
	/**
	 * Finds an user by id
	 * 
	 * @param uid
	 * @return
	 */
	public LdapUserVO findUserById(String employeeId);
	
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria);

}
