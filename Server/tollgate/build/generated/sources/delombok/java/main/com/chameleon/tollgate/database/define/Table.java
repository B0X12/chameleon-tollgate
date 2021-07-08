package com.chameleon.tollgate.database.define;

public enum Table {
	ACCOUNT("account"),
	AUTH_FACE("auth_face"),
	AUTH_OTP("auth_otp"),
	AUTH_PATTERN("auth_pattern"),
	AUTH_USB("auth_usb"),
	AUTH_FACTOR("auth_factor"),
	INIT_FACTOR("init_factor"),
	MAP_PC("map_pc"),
	MAP_ANDROID("map_android");
	
	private final String value; 
	
	Table(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
