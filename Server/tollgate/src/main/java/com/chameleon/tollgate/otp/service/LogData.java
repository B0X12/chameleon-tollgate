package com.chameleon.tollgate.otp.service;

import lombok.Getter;

@Getter
public class LogData 
{
	private String userIp;
	private String userId;
	
	public LogData(final String userIp, final String userId)
	{
		this.userIp = userIp;
		this.userId = userId;
	}
}
