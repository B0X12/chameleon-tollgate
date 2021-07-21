package com.chameleon.tollgate.database.define;

public enum Factor {
	USB("usb"),
	PATTERN("pattern"),
	FINGERPRINT("finger_print"),
	FACEID("face"),
	OTP("otp");
	
	private final String value; 
	
	Factor(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static Factor findBy(String value) {
		for(Factor factor : values()) {
			if(factor.toString().equals(value))
				return factor;
		}
		return null;
	}
}
