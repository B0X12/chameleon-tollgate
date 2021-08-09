package com.chameleon.tollgate.define;

public class Path {
	private static final String ACCOUNT = "/account/";
	public static final String LOGIN = ACCOUNT + "login/";
	public static final String SIGNUP = ACCOUNT + "signup/";
	public static final String MAP_PC = ACCOUNT + "map/pc/";
	
	private static final String AD_ACCOUNT = ACCOUNT + "android/";
	public static final String AD_LOGIN = AD_ACCOUNT + "login/";
	public static final String AD_MAP_AD = AD_ACCOUNT + "map/";
	public static final String AD_LOGOUT = AD_ACCOUNT + "logout/";
	public static final String AD_ID = AD_ACCOUNT + "id/";
	
	private static final String REGIST = "/register/";
	public static final String REGIST_USB = REGIST + "usb/";
	public static final String REGIST_PATTERN = REGIST + "pattern/";
	public static final String REGIST_FINGERPRINT = REGIST + "finger/";
	public static final String REGIST_FACEID = REGIST + "face/";
	public static final String REGIST_OTP = REGIST + "otp/";
	
	private static final String AUTH = "/auth/";
	public static final String AUTH_USB = AUTH + "usb/";
	public static final String AUTH_PATTERN = AUTH + "pattern/";
	public static final String AUTH_FINGERPRINT = AUTH + "finger/";
	public static final String AUTH_FACEID = AUTH + "face/";
	public static final String AUTH_OTP = AUTH + "otp/";
	
	public static final String INIT = "/init/";
	public static final String INIT_AND = INIT + "android/";
	public static final String INIT_USB = INIT + "usb/";
	public static final String INIT_PATTERN = INIT + "pattern/";
	public static final String INIT_FINGERPRINT = INIT + "finger/";
	public static final String INIT_FACE = INIT + "face/";
	
	public static final String HISTORY = "/history/";
}