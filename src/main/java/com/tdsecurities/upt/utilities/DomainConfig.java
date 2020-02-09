package com.tdsecurities.upt.utilities;

import java.util.ArrayList;
import java.util.List;

public class DomainConfig {

	private static List<String> domains = new ArrayList<String>();
	
	public static void add(String domain){
		domains.add(domain);
	}
	
	public static List<String> getDomains(){
		return domains;
	}
}
