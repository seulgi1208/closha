package org.kobic.gwt.smart.closha.shared;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Constants implements IsSerializable{
	
	public final static String TITLE = "CLOSHA";
	public final static String UNTITLE = "";
	public final static String NULL = "";
	public final static String NULL_MESSAGE = "NULL";
	public final static String INIT_COUNT = "0";
	public final static String SPLASH = "/";
	public final static String SEPERATOR = "\\";
	public final static String SPACE = "&nbsp;";
	public final static String DOUBLE_SPACE = "&nbsp;&nbsp;";
	public final static String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";
	public final static String BR = "</br>";
	public final static String POINTS = "...";
	public final static String PX = "px";
	public final static String SPLIT_SAPCE = " ";
	public final static String SPLIT_TAB = "  ";
	public final static String UNDER_LINE = "_";
	public final static String FILE_SEPERATOR = "/";
	
	public final static String IS_ADMIN_SESSION_KEY = "7864D584-4CF8-43ED-9A9A-05570F03F31F";
	
	public final static String WAITING = "Waiting";
	public final static String RUNNING = "Runing";
	public final static String COMPLETE = "Complete";
			
//	public final static String DATA = "data";
	public final static String SCRIPT = "script";
	public final static String PROJECT = "project";
	public final static String LOG = "log";
	public final static String FILE = "file";
	public final static String DIRECTORY = "directory";
	public final static String TEMP = "temp";
	
	public final static String ROOT_ID = "-1";
	public final static String NET_ROOT = "0";
	
	public final static String INPUT_PRAM = "INPUT"; 
	public final static String OUTPUT_PRAM = "OUTPUT";		
	
	public final static String BOARD_WINDOW_ID = "BOARD_WINDOW";
	public final static String BOARD_WINDOW_TITLE = "Board";
	
	public final static String CLOSHA_ENTRANCE_WINDOW_ID = "CLOSHA_ENTRANCE_WINDOW_ID";
	
	public final static String LOGIN_WINDOW_ID = "LOGIN_WINDOW";
	
	public final static String REGISTER_WINDOW_ID = "REGISTER_WINDOW";
	
	public final static String AGREEMENT_WINDOW_ID = "AGREEMENT_WINDOW";
	
	public final static String CLOSHA_WINDOW_ID = "CLOSHA_WINDOW";
	
	public final static String INTRO_WINDOW_ID = "INTRO_WINDOW";
	public final static String INTRO_WINDOW_TITLE = "CLOSHA";
	
	public final static String FILE_WINDOW_ID = "FILE_WINDOW";
	public final static String FILE_WINDOW_TITLE = "File Browser";
	
	public final static String JOB_WINDOW_ID = "JOB_WINDOW";
	public final static String JOB_WINDOW_TITLE = "System Monitoring";
	
	public final static String REGISTRATION_WINDOW_ID = "REGISTRATION_WINDOW";
	public final static String REGISTRATION_WINDOW_TITLE = "Register Analysis Program";
	
	public final static String MONITOR_WINDOW_ID = "STATISTIC_WINDOW";
	public final static String MONITOR_WINDOW_TITLE = "Usage Statistics";
	
	public final static String PREFERENCES_WINDOW_ID = "PREFERENCES_WINDOW";
	public final static String PREFERENCES_WINDOW_TITLE = "Share";
	
	public final static String CONFIGURATION_WINDOW_ID = "CONFIGURATION_WINDOW";
	public final static String CONFIGURATION_WINDOW_TITLE = "System Settings";
	
	public final static String SOURCE_CODE_VIEWER_WINDOW_ID = "SOURCE_CODE_VIEWER_WINDOW";
	public final static String SOURCE_CODE_VIEWER_WINDOW_TITLE =  "Code Editor";
	
	public final static String GRID_COROR = "#CEDEF5";
	
	public final static String MAKE_PROJECT_WINDOW_ID = "MAKE_PROJECT_WINDOW";
	public final static String MAKE_PROJECT_WINDOW_TITLE = "New Project";
	
	public final static String USER_PREFERENCE_WINDOW_ID = "USER_PREFERENCE_WINDOW";
	public final static String USER_PREFERENCE_WINDOW_TITLE = "KSSO Login System User List";
	
	public final static String CONSOL_LOG_WINDOW_ID = "CONSOL_LOG_WINDOW";
	public final static String EXCUTE_HISTORY_WINDOW_ID = "EXCUTE_HISTORY_WINDOW";
	public final static String CONNECTER_MODULE_WINDOW_ID = "CONNECTER_MODULE_WINDOW";
	public final static String ALL_PARAMETERS_SETTING_WINDOW_ID = "ALL_PARAMETERS_SETTING_WINDOW";
	public final static String MODULE_DIALOG_WINDOW_ID = "MODULE_DIALOG_WINDOW";
	
	public final static String DEBUG = "debug";
	public final static String DEBUG_IMG = "closha/icon/bug.png";
	
	public final static String COMMENT = "comment";
	public final static String COMMENT_IMG = "closha/icon/award_star_gold_1.png";
	
	public final static String ISSUE = "issue";
	public final static String ISSUE_IMG = "closha/icon/award_star_silver_1.png";
	
	public final static String[] EXTENSIONS = new String[] { "alz", "ace", "arc", "arc",
			"b64", "bh", "bhx", "bin", "bz2", "cab", "ear", "enc", "gz", "ha",
			"hqx", "ice", "img", "jar", "mim", "pak", "rar", "sit", "tar",
			"tgz", "uue", "war", "xxe", "zip", "zoo" };
	
	public final static String LOG_FILE_EXTENSION = ".log";
	public final static String LOG_DIR_EXTENSION = "_log";
	
	public final static String MEDIP_SEQ = "MeDIP-seq";
	public final static String EXOM_SEQ = "Exom-seq Analysis";
	public final static String RNA_SEQ = "RNA-Seq Analysis";
	public final static String WHOLE_GENOME = "Whole-genome Analysis";
	public final static String CHIP_SEQ = "ChiP-seq";
	
	public final static String MODULE_START = "start";
	public final static int MODULE_START_KEY = 1;
	public final static String MODULE_END = "end";
	public final static int MODULE_END_KEY = 0;
	
	public final static String TOTAL_COUNT = "Total: ";
	public final static String RUN_COUNT = "Running: ";
	public final static String WAIT_COUNT = "Waiting: ";
	public final static String COMPLETE_COUNT = "Complete: ";
	public final static String ERROR_COUNT = "Error: ";
	
	public final static String PIPELINE = "PIPELINE";
	public final static String MODULE = "MODULE";
	
	public final static String LINUX = "LINUX";
	public final static String HADOOP = "HADOOP";
	
	public final static String YES = "YES";
	public final static String NO = "NO";
	
	public final static String DATABASE_CONFIGURATION_BUNDLE_TYPE = "DATABASE_CONFIGURATION_BUNDLE";
	
	public final static int MODULE_TYPE = 1;
	public final static int PIPELINE_TYPE = 0;
	
	public final static LinkedHashMap<String, String> WORKFLOW_TYPE() {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("HADOOP", "HADOOP");
		valueMap.put("LINUX", "LINUX");
		return valueMap;
	}
	
	public final static String SCRIPT_TYPE = "SCRIPT_MODULE";
	public final static String EXTENAL_TYPE = "EXTENAL_APPLICATION";
	
	public final static LinkedHashMap<String, String> APP_FORMAT_TYPE(){
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("SCRIPT_MODULE", "SCRIPT_MODULE");
		valueMap.put("EXTENAL_APPLICATION", "EXTENAL_APPLICATION");
		return valueMap;
	}
	
	public final static String NEW_PROJECT_DESIGN = "Design New Analysis Pipeline";
	public final static String NEW_PROJECT_DESIGN_ID = "00000000-0000-0000-0000-000000000000";
	
	public final static int IMG_DEFAULT_SIZE = 48;
	
	public final static int WAITING_STATUS = 0;
	public final static int RUNNING_STATUS = 1;
	public final static int COMPLETE_STATUS = 2;
	public final static int ERROR_STATUS = 3;
	
	public final static String SERVICE_NAME_KEY = "service.name";
	
	public final static String JAVA_KEY = "java";
	public final static String PERL_KEY = "perl";
	public final static String PYTHON_KEY = "python";
	public final static String SHELL_KEY = "shell";
	public final static String R_KEY = "r";
	
	public final static String UNIX_DIR_KEY = "closha.unix.dir";
	
	public final static String LOG_DIR_KEY = "closha.log.dir";
	
	public final static String TMP_DIR_KEY = "closha.tmp.dir";
	
	public final static String RAPIDANT_DIR_KEY = "closha.rapidant.dir";
	
	public final static String HDFS_DIR_KEY = "closha.hdfs.dir";
	public final static String HDFS_URL_KEY = "closha.hdfs.url";
	public final static String HDFS_USER_KEY = "closha.hdfs.user";
	
	public final static String PYTHON_PATH_KEY = "python.system.path";
	public final static String SHELL_PATH_KEY = "shell.system.path";
	public final static String JAVA_PATH_KEY = "java.system.path";
	public final static String PERL_PATH_KEY = "perl.system.path";
	public final static String R_PATH_KEY = "r.system.path";
	
	public final static String MAIL_USE_KEY = "mail.use";
	public final static String MAIL_HOST_SERVER_KEY = "mail.host.server";
	public final static String MAIL_AUTH_KEY = "mail.authentication";
	public final static String MAIL_PASSWD_KEY = "mail.password";

	public final static String JDBC_URL_KEY = "jdbc.url";
	public final static String JDBC_USER_NAME_KEY = "jdbc.user";
	public final static String JDBC_PASSWD_KEY = "jdbc.password";
	public final static String JDBC_DRIVER_KEY = "jdbc.driver";
	
	public final static String WEB_SCHEDULER_TIMING_KEY = "scheduler.web.timer";
	public final static String ENGINE_SCHEDULER_TIMING_KEY = "scheduler.engine.timer";
	
	public final static String PUBLIC_DATA_PATH_KEY = "public.data.path";
	public final static String PUBLIC_DATA_NAME_KEY = "public.data.name";
	
	public final static String CACHE_MASTER_HOST_KEY = "cache.master.host";
	public final static String CACHE_MASTER_IP_KEY = "cache.master.ip";
}