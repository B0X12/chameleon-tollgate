package com.chameleon.tollgate.otp.service;

import com.chameleon.tollgate.otp.dto.*;

public interface IOTPService {
	String DbRegister(final LogData ld, final AuthExchangeOtp AEO);
	String Verify(final LogData ld, final AuthExchangeOtp AEO);
	String AdRequestDataSecretKey(final AuthExchangeOtp AEO);
}
