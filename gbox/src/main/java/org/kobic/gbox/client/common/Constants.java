package org.kobic.gbox.client.common;

public class Constants {

	private Constants() {
		// restrict instantiation
	}
	
	/* -------------------------------- ID -------------------------------- */
	public static final String LOGIN_FRAME_ID = "LOGIN";
	public static final String LOGIN_TITLE_ID = "loginTitle";
	public static final String LOGIN_BUTTON_LOGIN_ID = "loginButton";
	public static final String LOGIN_BUTTON_REGISTER_ID = "registerButton";
	public static final String LOGIN_TEXT_ACTIONTARGET_ID = "loginActionTarget";
	public static final String LOGIN_GRIDPANE_G_ID = "loginWindow";
	
	public static final String REGISGER_FRAME_ID = "REGISTER";
	public static final String RAPIDANT_FRAME_ID = "RAPIDANT";
	public static final String TRANSFER_TAB_ID = "TRANSFER_ID";
	public static final String TRANSFER_TAB_TATILE = "Transfers";
	public static final String LOGS_TAB_ID = "LOGS_ID";
	public static final String ROOT_ID = "-1";
	public static final String FTP_ID = "hadoop";
	/* -------------------------------------------------------------------- */
	
	
	/* ------------------------------- Img -------------------------------- */
	public static final String APP_ICON = "icons/gbox_app_32icon.png";
	
	public static final String LOGIN_MAIN_BG = "icons/gbox_login_bg_0.png";
	public static final String LOGIN_MAIN_TITLE_IMAGE = "icons/gbox_main_title.png";
	
	public static final String REGISTER_TITLE_IMAGE = "icons/gbox_register_title.png";
	/* -------------------------------------------------------------------- */
	
	
	/* ----------------------------- Message ------------------------------ */
	public static final String APP_RECHECK_INTERNET_TEXT = " : Please check your Internet connection and rerun the GBox.";
	public static final String APP_FTP_INSTABILITY_TEXT = " : FTP connection failed, do you want to run anyway?";
	public static final String APP_CLOSE_TEXT = "The application will be terminated.\nAll in-progress work will be canceled.";
	public static final String APP_OCCUR_NOT_VALIDATION_TEXT = "A failure occurred during user authentication.";
	public static final String APP_DISCONNECT_TEXT = "The system has been disconnected.Please restart the GBox.";
	public static final String APP_UPDATE_TEXT = "Please update to the latest version: %s Open.";
	
	public static final String LOGIN_PROMPT_IDTEXTFIELD_TEXT = "ID";
	public static final String LOGIN_PROMPT_PASSWORD_TEXT = "PASSWORD";
	public static final String LOGIN_BUTTON_LOGIN_TEXT = "LOGIN";
	public static final String LOGIN_BUTTON_REGISTER_TEXT = "REGISTER";
	public static final String LOGIN_TOOLTIP_IDTEXTFIELD_TEXT = "Please enter your ID";
	public static final String LOGIN_TOOLTIP_PASSWORD_TEXT = "Please enter your password";
	public static final String LOGIN_NOT_VALIDATION_INFO_TEXT = "Login information is not valid.\nPlease check again and try.";
	public static final String LOGIN_VALIDATION_INFO_TEXT = "User Name: %s\n"
			+ "Organization: %s\n"
			+ "Position: %s\n"
			+ "Email: %s\n"
			+ "Registration Date: %s\n"
			+ "Last Access Date: %s\n";
	public static final String LOGIN_CONNECT_SERVER_TEXT = "The GBox server is connected normally.";
	public static final String LOGIN_NOT_VALIDATION_USERINFO_TEXT = "               User information does not match.";
	
	public static final String REGISTER_PROMPT_IDTEXTFIELD_TEXT = "* ID / Combine English characters and numbers up to 12 characters";
	public static final String REGISTER_PROMPT_PASSWORD_TEXT = "* PASSWORD / Combine capital letters, lowercase letters, numbers, and special characters up to 20 characters";
	public static final String REGISTER_PROMPT_CONFIRMPW_TEXT = "* Confirm Password";
	public static final String REGISTER_PROMPT_NAMETEXTFIELD_TEXT = "* User name";
	
	public static final String COMMONSUTIL_SEND_MSG = "User Name: %s \n"
			+ "ID: %s \n"
			+ "Organization: %s \n"
			+ "IP: %s \n"
			+ "Data Size: %s \n"
			+ "Transferred Data Size: %s \n"
			+ "Speed: %s \n"
			+ "List: %s \n"
			+ "Code: %s \n"
			+ "Log: %s \n";
	
	public static final String MENUBAR_SHOW_CONTACT_MSG = 
			"It is a data transmission application provided by KOBIC to transmit a large amount of genomic data.\n\n"+
			"Version: " + Constants.VERESION + "\n" +
			"Release Date: " + Constants.RELEASE_DATE + "\n\n" +
			"System inquiry ▶ \n\nAdmin email: kogun82@kribb.re.kr\nPhone: (+82)42-879-8539";
	/* -------------------------------------------------------------------- */
	
	
	/* ------------------------- ADDRESS & PORT --------------------------- */
	public static final String SERVER_ADDRESS = "210.218.222.129";
	public static final int SERVER_TCP_PORT = 5930;
	public static final int SERVER_TCP_FAIRNESS_PORT = 5931;
	public static final int SERVER_UDP_PORT = 5932;
	public static final int SERVER_UDP_FAIRNESS_PORT = 5933;
	public static final int THRIFT_PORT = 8001;
	
	public static final String FTP_ADRESS = "210.218.222.129";
	public static final int FTP_PORT = 21;
	/* -------------------------------------------------------------------- */
	
	
	/* ----------------------------- Format ------------------------------ */
	public static final String DEFAULT_PROGRESS = "0.0%";
	public static final String DEFAULT_SPEED = "--";
	public static final String DEFAULT_TIME = "--:--:--";
	public static final String DATE_PATTERN = "yyyy-MM-dd(a)HH:mm";
	public static final String TIME_PATTERN = "HH:mm:ss";
	/* -------------------------------------------------------------------- */
	
	
	/* ---------------------------- Constants ----------------------------- */
	public static final int UPLOAD = 0;
	public static final int DOWNLOAD = 1;
	
	public static final int START = 0;
	public static final int RUN = 1;
	public static final int END = 2;
	public static final int ERROR = 3;
	public static final int CANCEL = 4;
	
	public static final int FAIRNESS_DEFAULT = 0;
	public static final int FAIRNESS_WEIGHTED = 1;
	public static final int FAIRNESS_FIX_1000 = 1000;
	public static final int FAIRNESS_FIX_100 = 100;
	public static final int FAIRNESS_FIX_50 = 50;
	public static final int FAIRNESS_FIX_10 = 10;
	
	public static final int MAX_DATA_POINTS = 100 ;
	/* -------------------------------------------------------------------- */
	
	
	/* ------------------------------- Style ------------------------------- */
	public static final String FONT_VERDANA = "Verdana";
	
	public static final String STYLE_ARIAL = "-fx-font-family: Arial;"; 
	public static final String STYLE_PROMPT_COLOR_STAR = "-fx-prompt-text-fill: #FFDDFF;";
	/* --------------------------------------------------------------------- */
	
	public static final String[] REGISTER_EMAIL_LIST = {
			"naver.com",
			"gmail.com",
			"hanmail.net",
			"nate.com",
			"korea.kr",
			"hotmail.com",
			"korea.com",
			"empal.com",
			"chollian.net",
			"yahoo.com",
			"seoulmetro.co.kr",
			"empas.com",
			"dreamwiz.com",
			"chol.com",
			"cyworld.com",
	};
	/* -------------------------------------------------------------------- */
	
	
	public static final String SPACE = "";
	public static final String NULL = "null";
	public static final String VERESION = "3.6";
	public static final String TITLE = "GBox " + VERESION;
	public static final String FULL_TITLE = "GBox High-speed Transmission System";
	public static final String RELEASE_DATE = "2019-11-20";
	
	public static final String LOGS_TAB_TITLE = "Logs";
	public static final String ROOT_NAME = "PC";
	public static final String ROOT_PATH = "/";
	public static final String SEPARATOR = "/";
	
	public static final String RAPIDANT_PATH = "/BiO/rapidant/data/group/AnonymousGroup";
	public static final String HDFS_ROOT_PATH = "/express";
	
	public static final String FTP_PASSWD = "1";
	
	public static final String READY_STATUS = "ready";
	public static final String SEND_STATUS = "sending";
	public static final String COMPLETE_STATUS = "complete";
	
}
