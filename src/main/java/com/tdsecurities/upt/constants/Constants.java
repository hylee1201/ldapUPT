package com.tdsecurities.upt.constants;
/**
 * The Interface Constants.
 */
public interface Constants {
	
	/** The Constant STATUS_INACTIVE. */
	public static final String STATUS_INACTIVE = "I"; // Inactive

	/** The Constant STATUS_ACTIVE. */
	public static final String STATUS_ACTIVE = "A"; // Active

	/** The Constant STATUS_UNAPPROVED. */
	public static final String STATUS_UNAPPROVED = "U"; // Unapproved

	/** The Constant STATUS_LOCKED. */
	public static final String STATUS_LOCKED = "L"; // Account Locked
	
	/** The Constant STATUS_REJECTED. */
	public static final String STATUS_REJECTED = "R"; // Account Rejected
		
	/** The Constant VISITOR_CODE. */
	public static final String VISITOR_CODE = "fmk_visitor";
	
	/** The Constant BASE_USER_ROLE. */
	public static final String BASE_USER_ROLE = "fmk_base";
	
	/** The Constant PUBLIC_ROLE_CODE. */
	public static final String PUBLIC_ROLE_CODE = "fmk_public";
	
	/** The Constant ADMIN_ROLE_CODE. */
	public static final String ADMIN_ROLE_CODE = "fmk_admin";
	
	/** The Constant SYS_ADMIN_ROLE_CODE. */
	public static final String SYS_ADMIN_ROLE_CODE = "fmk_sysadmin";

	//public static final String CURRENT_USER = "CurrentUser";

	/** The Constant CURRENT_PAGE. */
	public static final String CURRENT_PAGE = "CurrentPage";

	/** The Constant TOP_MENU. */
	public static final String TOP_MENU = "topmenu";

	/** The Constant RIGHT_MENU. */
	public static final String RIGHT_MENU = "rightmenu";
	
	/** The Constant USER_SESSION_LISTENER. */
	public static final String USER_SESSION_LISTENER 	= "USER_SESSION_LISTENER";
	
	/** The Constant USER_TRACKING_SESSION_LISTENER. */
	public static final String USER_TRACKING_SESSION_LISTENER 	= "USER_TRACKING_SESSION_LISTENER";
	
	/** The Constant ANONYMOUS_USER. */
	public static final String ANONYMOUS_USER	= "anonymous";
	
	// properties scope
	/** The Constant SYSTEM_SCOPE. */
	public static final String SYSTEM_SCOPE = "SYSTEM";
	// session timeout property
	/** The Constant TIMEOUT. */
	public static final String TIMEOUT = "FMK_SESSION_TIMEOUT";
	// login retry
	/** The Constant FMK_LOGIN_RETRY. */
	public static final String FMK_LOGIN_RETRY = "FMK_LOGIN_RETRY";
	// password history count
	/** The Constant FMK_PASSWORD_HISTORY_COUNT. */
	public static final String FMK_PASSWORD_HISTORY_COUNT = "FMK_PASSWORD_HISTORY_COUNT";
	// password expiry period (days)
	/** The Constant FMK_PASSWORD_EXPIRES_DAYS. */
	public static final String FMK_PASSWORD_EXPIRES_DAYS = "FMK_PASSWORD_EXPIRES_DAYS";
	
	/** The Constant UPLOAD_FILE_MAX_SIZE. */
	public static final String UPLOAD_FILE_MAX_SIZE = "UPLOAD_FILE_MAX_SIZE"; 
	
	// tracking on flag
	/** The Constant FMK_TRACKING_ON. */
	public static final String FMK_TRACKING_ON = "FMK_TRACKING_ON";
	
	// tracking stack size
	/** The Constant FMK_TRACKING_STACK_SIZE. */
	public static final String FMK_TRACKING_STACK_SIZE = "FMK_TRACKING_STACK_SIZE";
	
	//request access default role
	/** The Constant FMK_REQUEST_ACCESS_DEFAULT_ROLE. */
	public static final String FMK_REQUEST_ACCESS_DEFAULT_ROLE = "FMK_REQUEST_ACCESS_DEFAULT_ROLE";
	
	//cookie expiration time (2 months)
	/** The Constant FMK_COOKIE_EXPIRED_TIME. */
	public static final int FMK_COOKIE_EXPIRED_TIME = 60*60*24*60;
	
	//separate between framework and application
	/** The Constant FMK. */
	public static final int FMK = 1;
	
	/** The Constant NOT_FMK. */
	public static final int NOT_FMK = 0;
	
	//Application Default Locale
	/** The Constant FMK_DEFAULT_LOCALE. */
	public static final String FMK_DEFAULT_LOCALE = "FMK_DEFAULT_LOCALE";
	
	//ACEGI rememberme service
	/** The Constant FMK_REMEMBER_PASSWORD. */
	public static final String FMK_REMEMBER_PASSWORD = "FMK_REMEMBER_PASSWORD";
	
	// Query Tool default max rows
	/** The Constant FMK_QUERY_TOOL_DEFAULT_MAX_ROWS. */
	public static final String FMK_QUERY_TOOL_DEFAULT_MAX_ROWS = "FMK_QUERY_TOOL_DEFAULT_MAX_ROWS";
	
	// User alias passed by the SSO agent
	/** The Constant APP_ALIAS. */
	public static final String APP_ALIAS					= "appAlias";

	/** The Constant FMK_UPT_ENABLE. */
	public static final String FMK_UPT_ENABLE = "FMK_UPT_ENABLE";
	
	/** The Constant MENUS_DAO. */
	public static final String MENUS_DAO = "menusDAO";
	
	/** The Constant MENU_ROLE_CODE. */
	public static final String MENU_ROLE_CODE = "fmk_menu_contributor";
	
	/** The Constant ERROR_NO_KEYWORD_RESULT_FOUND. */
	public static final String ERROR_NO_KEYWORD_RESULT_FOUND = "error.no.keyword.result.found";
	
	/** The Constant ERROR_NO_SCHEDULED_JOB_RESULT_FOUND. */
	public static final String ERROR_NO_SCHEDULED_JOB_RESULT_FOUND = "error.no.scheduledjob.found";
	
	/** The Constant ERROR_NO_SCHEDULED_JOB_RESULT_FOUND. */
	public static final String ERROR_NO_SCHEDULED_JOB_DETAILS_FOUND = "error.no.scheduledjobDetails.found";
	
	/** The Constant PAGING. */
	public static final String PAGING = "PAGING";
	
	/** The Constant FMK_SUBMENU_LEVEL. */
	public static final String FMK_SUBMENU_LEVEL = "submenuLevel";
	
	/** The Constant ENABLE_MENU_HOVERING. */
	public static final String ENABLE_MENU_HOVERING = "ENABLE_MENU_HOVERING";
	
	/** The Constant ENABLE_MENU_HOVERING_PROPERTY. */
	public static final String ENABLE_MENU_HOVERING_PROPERTY = "ENABLE_MENU_HOVERING";
	
	/** The Constant ENABLE_CONTEXT_SENSTIVE_HOVER_HELP. */
	public static final String FMK_ENABLE_CONTEXT_SENSTIVE_HOVER_HELP = "FMK_ENABLE_CONTEXT_SENSTIVE_HOVER_HELP";
	
	/** The Constant ENABLE_CONTEXT_SENSTIVE_HOVER_HELP. */
	public static final String ENABLE_CONTEXT_SENSTIVE_HOVER_HELP = "FMK_ENABLE_CONTEXT_SENSTIVE_HOVER_HELP";
	
	/** The Constant FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH_PROPERTY. */
	public static final String FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH_PROPERTY = "FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH";
	
	/** The Constant FMK_HOVER_HELP_NODES_TO_EXCLUDE_PROPERTY. */
	public static final String FMK_HOVER_HELP_NODES_TO_EXCLUDE_PROPERTY = "FMK_HOVER_HELP_NODES_TO_EXCLUDE";
	
	/** The Constant FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH. */
	public static final String FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH = "FMK_CONTEXT_SENSTIVE_HOVER_HELP_WIDTH";
	
	/** The Constant HOVER_HELP_NODES_TO_EXCLUDE. */
	public static final String FMK_HOVER_HELP_NODES_TO_EXCLUDE = "FMK_HOVER_HELP_NODES_TO_EXCLUDE";
	
	/** The Constant SUB_MENU_LEVEL_PROPERTY. */
	public static final String SUB_MENU_LEVEL_PROPERTY = "SUB_MENU_LEVEL";
	
	/** The Constant SCOPE_MENU. */
	public static final String SCOPE_MENU =  "MENU";
	
	/** The Constant PAGE_KEYWORDS. */
	public static final String PAGE_KEYWORDS = "PAGE_KEYWORDS";
	
	/** The Constant KEYWORD_SERVICE. */
	public static final String KEYWORD_SERVICE = "keywordService";

	/** The Constant PAGE. */
	public static final String PAGE = "page";
	
	/** The Constant GLOBAL. */
	public static final String GLOBAL = "global";
	
	/** The Constant TRUE. */
	public static final String TRUE = "true";	
	
	/** The Constant SCOPE_SYSTEM. */
	public static final String SCOPE_SYSTEM =  "SYSTEM";
	
	/** The Constant CACHE_REFRESHED. */
	public static final String CACHE_REFRESHED = "Cache has been refreshed on server ";
	
	/** The Constant MSG_ADD_KEYWORD. */
	public static final String MSG_ADD_KEYWORD = "msg.keyword.add";
	
	/** The Constant MSG_DELETE_KEYWORD. */
	public static final String MSG_DELETE_KEYWORD = "msg.keyword.del";
	
	/** The Constant MSG_UPDATE_KEYWORD. */
	public static final String MSG_UPDATE_KEYWORD = "msg.keyword.update";
	
	/** The Constant MSG_CACHE_REFRESHED. */
	public static final String MSG_CACHE_REFRESHED = "msg.cache.refreshed";
	
	/** The Constant ENTITY_NAME. */
	public static final String ENTITY_NAME = "entityName";
	
	/** The Constant SCOPE. */
	public static final String SCOPE = "scope";
	
	/** The Constant PERMISSION_TYPE. */
	public static final String PERMISSION_TYPE = "permissionType";
	
	/** The Constant SHOW_PERMISSION_TYPE. */
	public static final String SHOW_PERMISSION_TYPE = "SHOW";
	
	/** The Constant SCOPE_SECTION. */
	public static final String SCOPE_SECTION = "section";
	
	/** The Constant ENTITY_SCOPE. */
	public static final String ENTITY_SCOPE = "ENTITY";
	
	/** The Constant ERROR_NO_ENTITY_RESULT_FOUND. */
	public static final String ERROR_NO_ENTITY_RESULT_FOUND = "error.no.entity.result.found";
	
	/** The Constant RB_OBJECT. */
	public static final String RESOURCE_BUNDLE_VO = "ResourceBundleVO";
	
	/** The Constant FIRST_PAGE_NUMBER. */
	public static final String FIRST_PAGE_NUMBER = "1";
	
	/** The Constant search String. */
	public static final String SEARCH_STRING = "searchString";	
	
	/** The Constant PAGE_SIZE_VALUE. */
	public static final String PAGE_SIZE_VALUE = "10";
	
	/** The Constant PAGE_NUMBER. */
	public static final String PAGE_NUMBER = "pageNumber";
	
	/** The Constant PAGE_SIZE. */
	public static final String PAGE_SIZE = "pageSize";
	
	/** The Constant OPERATION_ADDNEW. */
	public static final String OPERATION_ADD_NEW = "Add";
	
	/** The Constant OPERATION_EDIT. */
	public static final String OPERATION_EDIT = "Edit";
	
	/** The Constant RECORD_DELETE_SUCCESS. */
	public static final String RECORD_DELETE_SUCCESS = "msg.record.delete";
	
	/** The Constant REQUEST_PARAM_LOCALE. */
	public static final String REQUEST_PARAM_LOCALE = "locale";
	
	/** The Constant DEFAULT_SEARCH_FILTER. */
	public static final String DEFAULT_SEARCH_FILTER = "%";
	
	/** The Constant QUERY_MAP_ATTR_LOCALE. */
	public static final String FLAG_YES = "Yes";

	/** The Constant QUERY_MAP_ATTR_LOCALE. */
	public static final String FLAG_NO = "No";
	
	/** The Constant STATE_DELETED. */
	public static final String STATE_DELETED = "Deleted";

	/** The Constant STATE_MODIFIED. */
	public static final String STATE_MODIFIED = "Modified";
	
	/** The Constant ERROR_UPLOAD. */
	public static final String ERROR_UPLOAD = "ERROR-UPLOAD";
	
	/** The Constant PATTERN_RB_RECORD_NAME. */
	public static final String PATTERN_RB_RECORD_NAME = "[a-zA-Z_0-9-\\.]*";
	
	/** The Constant ERROR_PATTERN_RECORD_NAME. */
	public static final String ERROR_PATTERN_RESOURCE_BUNDLE_NAME = "msg.invalid.resourceBundleNamePattern";
	
	/** The Constant UPDATE_CONTENT_EXIST. */
	public static final String ERROR_UPDATE_CONTENT_EXIST = "msg.invalid.msgKeyAlreadyExist";
	
	/** The Constant RECORD_UPDATE_SUCCESSFULLY. */
	public static final String RECORD_UPDATED_SUCCESSFULLY = "msg.success.recordUpdate";
	
	/** The Constant RECORD_ADDED_SUCCESSFULLY. */
	public static final String RECORD_ADDED_SUCCESSFULLY = "msg.success.added";
	
	/** The Constant STATE_NEW. */
	public static final String STATE_NEW = "New";
	
	/** The Constant MIME_TYPE_GIF. */
	public static final String MIME_TYPE_GIF = "gif";

	/** The Constant MIME_TYPE_BMP. */
	public static final String MIME_TYPE_BMP = "bmp";

	/** The Constant MIME_TYPE_JPEG. */
	public static final String MIME_TYPE_JPEG = "jpeg";

	/** The Constant MIME_TYPE_JPG. */
	public static final String MIME_TYPE_JPG = "jpg";

	/** The Constant MIME_TYPE_PNG. */
	public static final String MIME_TYPE_PNG = "png";

	/** The Constant MIME_TYPE_TIFF. */
	public static final String MIME_TYPE_TIFF = "tiff";

	/** The Constant MIME_TYPE_TIF. */
	public static final String MIME_TYPE_TIF = "tif";

	/** The Constant MIME_TYPE_PDF. */
	public static final String MIME_TYPE_PDF = "pdf";
	
	/** The Constant MSG_KEY. */
	public static final String MSG_KEY = "msgKey";
	
	/** The Constant MSG_UPDATE_ENTITY. */
	public static final String MSG_UPDATE_ENTITY = "msg.entity.update";
	
	/** The Constant MENU_REQUEST. */
	public static final String MENU_REQUEST = "menuRequest";
	
	/** The Constant USER_NAME. */
	public static final String USER_NAME = "userName";
	
	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";
	
	/** The Constant BASIC. */
	public static final String BASIC = "Basic ";

	/** The Constant LOCALE. */
	public static final String LOCALE = "locale";
	
	/** The Constant UTF. */
	public static final String UTF_8 = "UTF-8";
	
	/** The Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";
	
	/** The Constant FORWARD_SLASH. */
	public static final String FORWARD_SLASH = "/";
	
	/** The Constant COLON. */
	public static final String COLON = ":";
	
	/** The Constant FMK_MENU_REQUEST_TIMEOUT. */
	public static final String FMK_MENU_REQUEST_TIMEOUT 
		= "FMK_MENU_REQUEST_TIMEOUT";
	
	/** The Constant FMK_MENU_REQUEST_READ_TIMEOUT. */
	public static final String FMK_MENU_REQUEST_READ_TIMEOUT
		= "FMK_MENU_REQUEST_READ_TIMEOUT";
	
	/** The Constant HTTP_BAD_REQUEST. */
	public static final int HTTP_BAD_REQUEST = 400;
	
	/** The Constant NEW_LINE. */
	public static final String NEW_LINE = "<BR/>";
	
	/** The Constant COMMA_STRING. */
	public static final String COMMA_STRING = ",";
	
	/** The Constant FMK_REMOTE_HOSTS_TO_SKIP_FROM_TRACKING. */
	public static final String FMK_REMOTE_HOSTS_TO_SKIP_FROM_TRACKING = "FMK_REMOTE_HOSTS_TO_SKIP_FROM_TRACKING";

	/** The Constant SEO_KEYWORDS. */
	public static final String SEO_KEYWORDS = "SEO_KEYWORDS";
	
	/** The Constant SEO_DESCRIPTION. */
	public static final String SEO_DESCRIPTION = "SEO_DESCRIPTION";
	
	/** The Constant SEO_TITLE. */
	public static final String SEO_TITLE = "SEO_TITLE";
	
	/** The Constant PROJECT_TITLE. */
	public static final String PROJECT_TITLE = "project.title";
	
	/** The Constant PROJECT_DESCRIPTION. */
	public static final String PROJECT_DESCRIPTION = "text.project.description";
	
	/** The Constant PROJECT_KEYWORDS. */
	public static final String PROJECT_KEYWORDS = "text.project.keyword";
	
	/** The Constant ENTITY_ROLE_PERMISSION_ADD_SUCCESS. */
	public static final String ENTITY_ROLE_PERMISSION_ADD_SUCCESS = "msg.success.entity.role.permission";
	
	/** The Constant ENTITY_ROLE_PERMISSION_ADD_ERROR. */
	public static final String ENTITY_ROLE_PERMISSION_ADD_ERROR = "msg.error.entity.role.permission";
	
	/** The Constant ENTITY_ROLE_PERMISSION_ADD_SUCCESS. */
	public static final String ENTITY_ROLE_PERMISSION_DELETE_SUCCESS = "msg.success.entity.delete.role.permission";
	
	/** The Constant ENTITY_ROLE_PERMISSION_UPDATE_SUCCESS. */
	public static final String ENTITY_ROLE_PERMISSION_UPDATE_SUCCESS = "msg.success.update.entity.role.permission";
	
	/** The Constant ENTITY_ROLE_VALIDATION_ERROR. */
	public static final String ENTITY_ROLE_VALIDATION_ERROR = "msg.error.entity.role.validation";
	
	/** The Constant ENTITY_PERMISSION_VALIDATION_ERROR. */
	public static final String ENTITY_PERMISSION_VALIDATION_ERROR = "msg.error.entity.permission.validation";
	
	/** The Constant ENTITY_DESCRIPTION_BLANK_ERROR. */
	public static final String ENTITY_DESCRIPTION_BLANK_ERROR = "msg.error.entity.description.validation";
	
	/** The Constant ENTITY_DESCRIPTION_UPDATE_SUCCESS. */
	public static final String ENTITY_DESCRIPTION_UPDATE_SUCCESS = "msg.success.entity.description.validation";
	
	/** The Constant ROLES. */
	public static final String ROLES = "roles";
	
	/** The Constant SEO_METADATA_SERVICE. */
	public static final String SEO_METADATA_SERVICE = "seoMetadataService";
	
	/** The Constant ENTITY_SERVICE. */
	public static final String ENTITY_SERVICE = "entityService";

	/** The Constant SSO_PREVIOUS_TOKEN. */
	public static final String SSO_PREVIOUS_TOKEN = "SSO_PREVIOUS_TOKEN";
	
	/** The Constant SSO. */
	public static final String SSO = "SSO";
	
	/** The Constant SSO_COOKIE_NAME. */
	public static final String SSO_COOKIE_NAME = "SSO_COOKIE_NAME";
	
	/** The Constant FMK_ENABLE_MENU_CONTRIBUTION. */
	public static final String FMK_ENABLE_MENU_CONTRIBUTION = "FMK_ENABLE_MENU_CONTRIBUTION";
	
	/** The Constant UNAVAILABLE_ACTION. */
	public static final String UNAVAILABLE_ACTION = "/unavailable.action";
	
	/** The Constant MENU_LOADER. */
	public static final String MENU_LOADER = "menuLoader";
	
	/** The Constant SSO_ANONYMOUS_LOGIN. */
	public static final String SSO_ANONYMOUS_LOGIN = "SSO_ANONYMOUS_LOGIN";
	
	/** The Constant SSO_ANONYMOUS_USERNAME. */
	public static final String SSO_ANONYMOUS_USERNAME = "SSO_ANONYMOUS_USERNAME";

	/** The Constant HIDE_EMPTY_PLACE_HOLDER_NODE. */
	public static final String HIDE_EMPTY_PLACE_HOLDER_NODE = "FMK_HIDE_EMPTY_PLACE_HOLDER_NODE";

	/** The Constant HIDE_EMPTY_PARENT_NODE. */
	public static final String HIDE_EMPTY_PARENT_NODE = "FMK_HIDE_EMPTY_PARENT_NODE";

	/** The Constant TBA. */
	public static final String TBA = "TBA";

	/** The Constant DIAMOND_SCOPE. */
	public static final String DIAMOND_SCOPE = "DIAMOND";

	/** The Constant TEXT_EMAIL_SUBJECT. */
	public static final String TEXT_EMAIL_SUBJECT = "text.email.subject";

	/** The Constant TEXT_EMAIL_BODY. */
	public static final String TEXT_EMAIL_BODY = "text.email.body";

	/** The Constant MAIL. */
	public static final String MAIL = "MAIL";

	/** The Constant FMK_MAIL_SMTP_HOST. */
	public static final String FMK_MAIL_SMTP_HOST = "FMK_MAIL_SMTP_HOST";

	/** The Constant FMK_MAIL_FROM_ADDRESS. */
	public static final String FMK_MAIL_FROM_ADDRESS = "FMK_MAIL_FROM_ADDRESS";

	/** The Constant FMK_TECH_SUPPORT_EMAIL. */
	public static final String FMK_TECH_SUPPORT_EMAIL = "FMK_TECH_SUPPORT_EMAIL";

	/** The Constant MAIL_SMTP_AUTH. */
	public static final String MAIL_SMTP_AUTH = "MAIL_SMTP_AUTH";

	/** The Constant EMAIL_SWITCHED_OFF. */
	public static final String EMAIL_SWITCHED_OFF = "text.email.off.msg";

	/** The Constant EMAIL_SENDING_ERROR. */
	public static final String EMAIL_SENDING_ERROR = "text.email.failure.msg";

	/** The Constant EMAIL_SUCCESS_MSG. */
	public static final String EMAIL_SUCCESS_MSG = "text.email.success.msg";

	/** The Constant PROJECT_NAME. */
	public static final String PROJECT_NAME = "project.name";

	/** The Constant ERROR_INVALID_CRON_EXPRESSION. */
	public static final String ERROR_INVALID_CRON_EXPRESSION = "error.invalid.cron.expression";
	

	
	


	
}
