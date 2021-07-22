package com.chameleon.tollgate.otp.service;

import com.chameleon.tollgate.otp.dto.*;

public interface IOTPService {
	String Register(AuthOtp AO);
	String Verify(AuthOtp AO);
}
