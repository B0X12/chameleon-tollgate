package com.chameleon.tollgate.util.tollgateLog.dto.code;

import lombok.Getter;

@Getter
public enum LoginCode implements LogCode {
	AD_LOGIN("Login success.", 1),
	AD_LOGIN_FAILED("Login failed.", 2),
	AD_LOGOUT("Logout.", 3),
	AD_LOGOUT_FAILED("Logout failed.", 4),
	NO_USER("No user.", 5),
	MAP_ANDROID("Register the user's Android.", 6),
	RM_ANDROID("Unregister the user's Android.", 7);

	private final String errorMessage;
	private final int errorCode;
	
	private LoginCode(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
}