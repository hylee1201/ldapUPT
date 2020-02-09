package com.tdsecurities.upt.utilities;

import java.util.Properties;

import com.sun.identity.shared.configuration.SystemPropertiesManager;

public class IdentityConfigUtil
{
	//private static final Logger logger = Logger.getLogger(IdentityConfigUtil.class);
	
	// Cheating spring to set a static property.
	public static Properties initializeIdentityConfigProperties(Properties properties) {				
		SystemPropertiesManager.initializeProperties(properties);
		
		/*
		logger.info(
				"am.encryption.pwd property is: " + 
				SystemPropertiesManager.get("am.encryption.pwd")
		);
		*/		
		return properties;
	}
}
