package com.chameleon.tollgate.account.dto;

import lombok.Getter;

@Getter
public class FactorPack {
	private String factor;
	private boolean value;
	private long timestamp;
}
