package com.chameleon.tollgate.otp.service;

import com.chameleon.tollgate.otp.dto.*;

public interface IOTPService {
	String DbRegister(final String userId);
	String Verify(final AuthExchangeOtp AO);
	String AdRequestDataSecretKey(final String userId);
}
