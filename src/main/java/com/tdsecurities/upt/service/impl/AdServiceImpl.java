package com.tdsecurities.upt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.tdsecurities.upt.exception.InvalidDomainException;
import com.tdsecurities.upt.model.user.LdapUserVO;
import com.tdsecurities.upt.model.user.SsoUserSearchCriteria;
import com.tdsecurities.upt.persistence.AdTdsDAO;
import com.tdsecurities.upt.persistence.ldap.AdTdsDAOImpl;
import com.tdsecurities.upt.service.AdService;
import com.tdsecurities.upt.utilities.AdConfig;
import com.tdsecurities.upt.utilities.DomainConfig;
import com.tdsecurities.upt.utilities.PropertyLoader;

public class AdServiceImpl implements AdService
{
	private static final Logger logger = Logger.getLogger(AdServiceImpl.class);

	private Properties properties;
	private Map<String, List<AdConfig>> configMap= new HashMap<String, List<AdConfig>>();
	
	public void init(){
		String _logger_method="init";
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
		configMap = PropertyLoader.load(properties);
		for(String str:configMap.keySet()){
			System.out.println("Domain:"+str);
			List<AdConfig> adList = configMap.get(str);
			for(AdConfig ad: adList){
				System.out.println(ad);
			}
		}
		
		for(String str : DomainConfig.getDomains()){
			System.out.println(str);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("> "+_logger_method);
		}
	}
	/**
	 * Finds an user by id 
	 * search all domains
	 * @param uid
	 * @return
	 */	
	public LdapUserVO findUserById(String employeeId) throws InvalidDomainException
	{
		LdapUserVO result = null;
				
		for(String domain: configMap.keySet()){
								
			result = findUserById(employeeId, domain);
			if(result!=null)break;
		}
		
		
		return result;
	}
	
	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria) throws InvalidDomainException{
		
		List<LdapUserVO> resultList = new ArrayList<LdapUserVO>();
		
		for(String domain: configMap.keySet()){
								
			List<LdapUserVO> results = searchUser(searchCriteria, domain);
			if(results!=null && results.size()!=0){
				resultList.addAll(results);
			}

			
		}
		
		
		return resultList;
	}
	
	public List<AdConfig> findDomainConfig(String domain) throws InvalidDomainException {
		List<AdConfig> adConfigList = configMap.get(domain.toLowerCase());
		if(adConfigList == null){
			throw new InvalidDomainException(domain);
		}
		return adConfigList;
	}
	
	/**
	 * 
	 */
	public LdapUserVO findUserById(String employeeId, String domain) throws InvalidDomainException
	{
		LdapUserVO result = null;
		
		List<AdConfig> adConfigList = configMap.get(domain.toLowerCase());
		
		if(adConfigList == null){
			throw new InvalidDomainException(domain);
		}
		for(AdConfig config: adConfigList)
		{
			boolean isAdError = false;
			
			try
			{
				AdTdsDAO dao = getAdDao(
						config.getUrl(), 
						config.getBaseDN(), 
						config.getUserDN(), 
						config.getPassword(),
						config.getTimeout()							
							);
					
				result = dao.findUserById(employeeId);
			}
			catch(Exception ex)
			{
				logger.info("AD Service exception: ");
				logger.info(ex);
					
				isAdError = true;
			}
				
			if (! isAdError)
			{
				//no error, exit current group
				break;
			}
		}
		//result null means no user found in this domain
		
		return result;
	}
	
	public boolean isUserMemberOf(String[] groupNames, String employeeId, String domain) throws InvalidDomainException{
		
		LdapUserVO ldapUserVO= findUserById(employeeId,domain);
		if(ldapUserVO == null) return false;
		else{
			return ldapUserVO.isEmployeeInGroup(groupNames);
		}
	}
	

	public List<LdapUserVO> searchUser(SsoUserSearchCriteria searchCriteria, String domain) throws InvalidDomainException{
		List<LdapUserVO> result = null;

		
		List<AdConfig> adConfigList = configMap.get(domain.toLowerCase());
		
		if(adConfigList == null){
			throw new InvalidDomainException(domain);
		}
		
		logger.debug("searching in domain "+domain);
		for(AdConfig config: adConfigList)
		{
				boolean isAdError = false;
			
				try
				{
					AdTdsDAO dao = getAdDao(
							config.getUrl(), 
							config.getBaseDN(), 
							config.getUserDN(), 
							config.getPassword(),
							config.getTimeout()							
								);
						
					
					result = dao.searchUser(searchCriteria);
				}
				catch(Exception ex)
				{
					logger.info("AD Service exception: ");
					logger.info(ex);
					
					isAdError = true;
				}
				
				if (! isAdError)
				{
					//no error, exit current group
					break;
				}
				
		}
		
		if(result!=null){
			for(LdapUserVO user: result){
				user.setDomain(domain);
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private AdTdsDAOImpl getAdDao(String url, String base, String userDN, String password, String connectTimeout) throws Exception
	{
		LdapContextSource source = new LdapContextSource();
		source.setUrl(url);
		source.setBase(base);
		source.setUserDn(userDN);
		source.setPassword(password);
		source.setReferral("follow");
		
		Map baseEnvironmentProperties = new Hashtable();
		baseEnvironmentProperties.put("com.sun.jndi.ldap.connect.timeout", connectTimeout);		
		source.setBaseEnvironmentProperties(baseEnvironmentProperties);
		
		source.afterPropertiesSet();
		
		LdapTemplate template = new LdapTemplate(source);		
		
		AdTdsDAOImpl adDao = new AdTdsDAOImpl();
		adDao.setLdapTemplate(template);
		
		return adDao;
	}
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}


}
