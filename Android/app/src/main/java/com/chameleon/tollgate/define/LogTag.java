package com.chameleon.tollgate.define;

public class LogTag {
    private static final String _REST = "_Rest";
    private static final String _FCM = "_FCM";
    private static final String _USB = "_USB";
    private static final String _PATTERN = "_Pattern";
    private static final String _FINGERPRINT = "_FingerPrint";
    private static final String _FACEID = "_FaceID";
    private static final String _OTP = "_OTP";

    public static final String TOLLGATE = "Tollgate";
    public static final String REST = TOLLGATE + _REST;
    public static final String FCM = TOLLGATE + _FCM;

    public static final String AUTH_USB = TOLLGATE + _USB;
    public static final String AUTH_PATTERN = TOLLGATE + _PATTERN;
    public static final String AUTH_FINGERPRINT = TOLLGATE + _FINGERPRINT;
    public static final String AUTH_FACEID = TOLLGATE + _FACEID;
    public static final String AUTH_OTP = TOLLGATE + _OTP;

    public static final String REST_USB = TOLLGATE + _REST + _USB;
    public static final String REST_PATTERN = TOLLGATE + _REST + _PATTERN;
    public static final String REST_FINGERPRINT = TOLLGATE + _REST + _FINGERPRINT;
    public static final String REST_FACEID = TOLLGATE + _REST + _FACEID;
    public static final String REST_OTP = TOLLGATE + _REST + _OTP;
}
