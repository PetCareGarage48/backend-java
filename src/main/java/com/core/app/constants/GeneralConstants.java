package com.core.app.constants;


public class GeneralConstants {

	public static final String TOKEN_SECRET = "SomeRandomStringHere";

	/*REDIS_TOPICS*/
	public static final String USERS_CHANNEL = "users_channel";
	public static final String EXTRA_CHANNEL = "extra_channel";


	/*DB_COLLECTION_NAMES*/
	public static final String USERS = "users";
	public static final String APP_SETTINGS = "app_settings";
	public static final String TOKENS = "tokens";

	/*GENERAL FIELDS*/
	public static final String PAGE = "page";
	public static final String SIZE = "size";
	public static final String PATH = "path";
	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_SIZE = 20;
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	public static final String SUCCESS = "success";
	public static final String AUTHORIZATION = "Authorization";
	public static final String QUESTION_MARK = "?";
	public static final String USER_ID = "userId";
	public static final String USER_ROLE = "userRole";
	public static final String PASSWORD = "password";
	public static final int HOURS_FOR_EMAIL_VERIFICATION = 24;
	public static final int MINUTES_FOR_NEW_EMAIL_VERIFICATION_LINK = 15;
	public static final String EMAIL_VERIFIED_THYMELEAFE_TEMPLATE = "emailVerified";
	public static final String SOMETHING_WENT_WRONG = "Something went wrong. Error occurred: ";
	public static final String LOG_THIS = "### -> {}";

	/*MESSAGES*/
	public static final String SIGN_UP_CONFIRMATION_SUBJECT = "Registration Confirmation";
	public static final String SIGN_UP_COMPLETED_SUBJECT = "Registration Completed";
	public static final String EMAIL_SENT = "Email Sent";

	/*LOGGING MESSAGES*/
	public static final String SIGN_UP_CONFIRMATION_EMAIL_FAILED = "### -> Failed to send sign up confirmation email.";

}
