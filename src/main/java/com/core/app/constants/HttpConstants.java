package com.core.app.constants;


public class HttpConstants {

	/*HTTP_METHODS_NAMES*/
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String OPTIONS = "OPTIONS";
	public static final String HEAD = "HEAD";

	/*HEADERS*/
	public static final String AUTHORIZATION_HEADER = "Authorization";

	/*HTTP_RESPONSE_MESSSAGES*/
	public static final String INVALID_TOKEN = "Invalid token";
	public static final String FORBIDDEN = "You have no permissions to access this resource";
	public static final String MSG_SENT_TO_REDIS = "Successfully sent message to the channel ?";
	public static final String USER_EXISTS = "User with this email already exists. Please, use another email address.";
	public static final String USER_REGISTERED = "Successfully registered.";
	public static final String VERIFICATION_LINK_SENT = "We successfully sent verification link to your inbox. Please, verify your email address.";
	public static final String ACCOUNT_VERIFIED = "Your account is verified.";
	public static final String ACCOUNT_ALREADY_VERIFIED = "You already verified your account.";
	public static final String VERIFICATION_LINK_EXPIRED = "Verification link is expired. Please, request the new one.";
	public static final String VERIFICATION_LINK_REQUEST_LIMIT_EXCEEDED = "We just sent you new verification link. Please, check your inbox.";
	public static final String VERIFICATION_LINK_RESENT = "We just sent you new verification link, please check your inbox. You may request new verification link one time per 15 minutes.";
	public static final String ACCOUNT_MISSING = "Sorry, we can not find your account.";
	public static final String DATA_SAVING_FAILURE = "Failed to save data to in database.";
	public static final String INVALID_CREDENTIALS = "Email or Password is incorrect";
	public static final String NOT_VERIFIED = "Please verify your email address before accessing your account";
	public static final String ACCOUNT_SUSPENDED = "Your account is suspended!";
	public static final String TRY_AGAIN_LATER = "Something went wrong. Please try again later";
	public static final String LOGIN_SUCCESSFULLY = "Logged in successfully";
	public static final String LOGOUT_SUCCESSFULLY = "Logged out successfully";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String INVALID_USER_ID = "User ID is invalid";
	public static final String ACCOUNT_STATUS_CHANGED = "Successfully changed account status";
	public static final String ACCOUNT_DELETED = "Account is successfully deleted.";
	public static final String FAILED_TO_DELETE_ACCOUNT = "Failed to delete user account.";
	public static final String ADMIN_ACCOUNT_CREATED = "Admin account successfully created";
}
