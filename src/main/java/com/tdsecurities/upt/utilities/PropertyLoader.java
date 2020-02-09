package com.tdsecurities.upt.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertyLoader {

	private static final String LIST_ROOT_NAME_DOMAIN = "ad.domain";
	private static final String LIST_ROOT_NAME_URL = "ad.url";
	private static final String LIST_ROOT_NAME_BASE = "ad.baseDN";
	private static final String LIST_ROOT_NAME_USER = "ad.userDn";
	private static final String LIST_ROOT_NAME_PASSWORD = "ad.password";
	private static final String LIST_ROOT_NAME_TIMEOUT = "ad.connect_timeout";
	
	public static Map<String, List<AdConfig>> load(Properties properties){
		
		Map<String, List<AdConfig>> configMap= new HashMap<String, List<AdConfig>>();
		int firstIndex = 1;
		int secondIndex = 1;
		String domain = properties.getProperty(getListItemName(LIST_ROOT_NAME_DOMAIN, firstIndex, secondIndex));
		while(domain != null){
			while(domain != null){
				List<AdConfig> configList = configMap.get(domain.toLowerCase().trim());
				if(configList == null){
					configList = new ArrayList<AdConfig>();
					configMap.put(domain.toLowerCase().trim(), configList);
				}
				AdConfig config = new AdConfig();
				config.setUrl(properties.getProperty(getListItemName(LIST_ROOT_NAME_URL, firstIndex, secondIndex)).trim());
				config.setBaseDN(properties.getProperty(getListItemName(LIST_ROOT_NAME_BASE, firstIndex, secondIndex)).trim());
				config.setUserDN(properties.getProperty(getListItemName(LIST_ROOT_NAME_USER, firstIndex, secondIndex)).trim());
				config.setPassword(properties.getProperty(getListItemName(LIST_ROOT_NAME_PASSWORD, firstIndex, secondIndex)).trim());
				config.setTimeout(properties.getProperty(getListItemName(LIST_ROOT_NAME_TIMEOUT, firstIndex, secondIndex)).trim());
			
				configList.add(config);
				secondIndex = secondIndex+1;
				domain = properties.getProperty(getListItemName(LIST_ROOT_NAME_DOMAIN, firstIndex, secondIndex));
			}
			firstIndex = firstIndex + 1;
			secondIndex = 1;
			domain = properties.getProperty(getListItemName(LIST_ROOT_NAME_DOMAIN, firstIndex, secondIndex));
		}
		
		for(String domainStr : configMap.keySet()){
			DomainConfig.add(domainStr);
		}
		
		return configMap;
	}
	
	
	
	
	private static String getListItemName(String listRootName, int firstIndex, int secondIndex)
	{
		return String.format("%s_%s.%s", listRootName, firstIndex, secondIndex);
	}
}
