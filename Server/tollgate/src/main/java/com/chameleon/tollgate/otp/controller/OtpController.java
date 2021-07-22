package com.chameleon.tollgate.otp.controller;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.otp.dto.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.otp.service.OTPService;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

@RestController
public class OtpController {
	@Autowired
	private OTPService otpService;

	@PostMapping(path = Path.REGIST_OTP)
	public ResponseEntity<Response<String>> OtpRegister(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthOtp AO, long timestamp) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		String result = otpService.Register(AO);
		
		Response<String> resp = new Response<String>(HttpStatus.OK, result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
		
	}


	@PostMapping(path = Path.AUTH_OTP)
	public ResponseEntity<Response<String>> OtpVerify(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthOtp AO, long timestamp) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		String result = otpService.Verify(AO);

		Response<String> resp = new Response<String>(HttpStatus.OK,result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
