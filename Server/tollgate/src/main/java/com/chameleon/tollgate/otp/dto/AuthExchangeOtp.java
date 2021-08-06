package com.chameleon.tollgate.otp.dto;

import lombok.Getter;

@Getter
public class AuthExchangeOtp 
{
	private String userId;
	private long timestamp;
	private String data;
}
