package com.chameleon.tollgate.qr.dto;

import lombok.Getter;

@Getter
public class AuthExchangeQr {
	private String userId;
	private long timestamp;
	private String data;
}
