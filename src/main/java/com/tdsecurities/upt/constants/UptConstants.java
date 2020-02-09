package com.tdsecurities.upt.constants;

public class UptConstants {
	//public static final int DEFAULT_TIMEOUT_CR							= 30;
	
	public static final String ROLE_FMK_SYSADMIN = "fmk_sysadmin";
	//public static final int DEFAULT_TIMEOUT							= 30;
	public static final String DOMAIN_USER_DELIMITER					= "\\";
	public static final String DOMAIN_USER_DELIMITER_REG_EXP = "\\\\";
	
	// UPT message file
	public static final String MESSAGE_BUNDLE_PATH						= "uptMsgs";
	public static final String WBTS_MESSAGE_BUNDLE_PATH					= "wbtsMsgs";
	
    public static final String EXTERNAL_USER_SERVICE_CONFIGURATION_FILE_URI = "serviceConfig/externalUserServiceConfiguration.xml";
    public static final String CONFIG_ENCRYPTION_KEY					= "EncryptionKey";
        
    // property table
	public static final String SCOPE_LDAP 								= "LDAP";
	public static final String SCOPE_SPML								= "SPML";
    public static final String SCOPE_WORKFLOW							= "WORKFLOW";
    public static final String SCOPE_UPT								= "UPT";
    public static final String PROPERTY_UPT_URL 						= "UPT_URL";
    public static final String PROPERTY_CR_ADMIN_GROUP_EMAIL 			= "CR_ADMIN_GROUP_EMAIL";
    public static final String PROPERTY_CR_WF_PENDING_THRESHOLD			= "CR_WF_PENDING_THRESHOLD";
    public static final String PROPERTY_TDFX_ADMIN_GROUP_EMAIL 			= "TDFX_ADMIN_GROUP_EMAIL";
	public static final String PROPERTY_CR_SPML_URL						= "CR_URL";
	public static final String PROPERTY_CR_SERVICE_ACCOUNT_USER			= "CR_SERVICE_ACCOUNT_USER";
	public static final String PROPERTY_CR_SERVICE_ACCOUNT_PASSWORD		= "CR_SERVICE_ACCOUNT_PASSWORD";
	public static final String PROPERTY_UID_DOMAIN_DELIMITER			= "UID_DOMAIN_DELIMITER";
	public static final String PROPERTY_AUTH_INSTANCE_ROLE				= "AUTH_INSTANCE_ROLE";
	public static final String PROPERTY_IDENTITY_INSTANCE_ROLE_PREFIX	= "IDENTITY_INSTANCE_ROLE_PREFIX";
	public static final String PROPERTY_LDAP_PASSWORD_HISTORY_COUNT 	= "LDAP_PASSWORD_HISTORY_COUNT";
	public static final String PROPERTY_PASS_LOCKED_COUNT 				= "PASS_LOCKED_COUNT";
	public static final String PROPERTY_ERI_GROUP_TDS 					= "ERI_GROUP_TDS";
	public static final String PROPERTY_ERI_GROUP_TDBFG 				= "ERI_GROUP_TDBFG";
	public static final String PROPERTY_AUTH_USER_DN					= "AUTH_USER_DN";
    public static final String URI_CR_VIEW_REGISTRATION_REQUEST 		= "/cradmin/viewRegistrationRequest.action?wfId=";
    public static final String URI_CR_PENDING_REQUESTS 					= "/cradmin/viewCrPendingRequests.action";
    
    public static final String LOCALE_EN_CA								= "en_CA";
    public static final String LOCALE_FR_CA								= "fr_CA";
    
    
    //public static final String PASSWORD_EXPIRATION_TIME_YEARS_POLICY   	= "PASS_EXPIRATION_POLICY_YEARS";
    //public static final String PASSWORD_EXPIRATION_TIME_MONTHS_POLICY	= "PASS_EXPIRATION_POLICY_MONTHS";    
   // public static final String PASSWORD_EXPIRATION_TIME_DAYS_POLICY		= "PASS_EXPIRATION_POLICY_DAYS";
        
    public static final String CR_ADMIN_FIND_USERS_SSO_SEARCH_BATCH		= "CR_ADMIN_FIND_USERS_SSO_SEARCH_BATCH";
    public static final String ADMIN_FIND_USERS_SSO_SEARCH_BATCH		= "ADMIN_FIND_USERS_SSO_SEARCH_BATCH";
    public static final String SORT_ORDER_ASC							= "asc";
    public static final String SORT_ORDER_DESC							= "desc";
      
    // Reporting Tool  - Mailer
	public static final String REPORTING_MAIL_SUBJECT = "message.reporting.subject";
	public static final String REPORTING_MAIL_BODY = "message.reporting.body";
	
	// Reporting Tool - Quartz
	public static final String REPORT_SERVICE_BEAN_ID = "reportService";
	public static final String SCHEDULER_BEAN_NAME = "schedulerBean";
	public static final String REPORT_RESULT_CLEANUP_STARTUP_TRIGGER = "REPORT_RESULT_CLEANUP_STARTUP_TRIGGER";
	public static final String REPORT_RESULT_CLEANUP_JOB = "reportResultCleanupJob";
	public static final String REPORT_RESULT_CLEANUP_TRIGGER_NAME = "reportResultCleanupTrigger";
	public static final String REPORT_RESULT_CLEANUP_CRON_EXPRESSION_PROPERTY = "REPORT_RESULT_CLEANUP_CRON_EXPRESSION";
	public static final String SYSTEM = "SYSTEM"; // for getting the width of the page.
	
	// Reporting Tool - Parameter Types
	public static final String REPORT_PARAMETER_TEXT_BOX= "TEXTBOX";
	public static final String REPORT_PARAMETER_DROP_DOWN = "DROPDOWN";
	public static final String REPORT_PARAMETER_CALENDAR_INPUT = "CALENDARINPUT";
	
	public static final String GROUP_NAMES_DELIMITER 					= "|";
	public static final String STATUS_ENROLLED 							= "Enrolled";
	public static final String STATUS_DE_ENROLLED 						= "De-Enrolled";
	public static final String APP_REGISTER_EMPLOYEE 					=  "APP_REGISTER_EMPLOYEE";
	
	/** The Constant COMMA_STRING. */
	public static final String COMMA_STRING = ",";
}
