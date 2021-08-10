package com.chameleon.tollgate.otp.controller;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.otp.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.otp.service.OTPService;
import com.chameleon.tollgate.otp.service.ReturnMessage;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

@RestController
public class OtpController {
	public static boolean DEBUGOTP = true;

	@Autowired
	private OTPService otpService;

	@PostMapping(path = Path.REGIST_OTP)
	public ResponseEntity<Response<String>> OtpRegister(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeOtp AO, long timestamp) throws UnauthorizedUserAgentException,Exception 
	{
		if(DEBUGOTP)
			System.out.println("#REGISTER OTP -  START Id : " + AO.getUserId());
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		String result = otpService.DbRegister(AO.getUserId());

		if(DEBUGOTP)
			System.out.println("#REGISTER OTP -  END Result : " + result);
		
		Response<String> resp = new Response<String>(HttpStatus.OK, result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);	
	}

	@PostMapping(path = Path.AUTH_OTP)
	public ResponseEntity<Response<String>> OtpVerify(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeOtp AEO, long timestamp) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		if(DEBUGOTP)
			System.out.println("#Verify OTP -  START Id : " + AEO.getUserId() + " SendOtpValue : " + AEO.getData());
		
		String result = otpService.Verify(AEO);

		if(DEBUGOTP)
			System.out.println("#Verify OTP -  END Id : " + AEO.getUserId() + " Result : " + result);
		
		
		Response<String> resp = new Response<String>(HttpStatus.OK,result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	
	@PostMapping(path=Path.DATA_OTP+"{id}")
	public ResponseEntity<Response<String>> AdGetDataSecretKey(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String userId,@RequestBody AuthExchangeOtp AEO) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		if(DEBUGOTP)
			System.out.println("#AdSendSecretKey OTP -  START Id : " + AEO.getUserId());
		
		String result = ReturnMessage.VERIFY_INFORMATION;
		
		if(AEO.getUserId().equals(userId))
			result = otpService.AdRequestDataSecretKey(AEO.getUserId());
		
		
		if(DEBUGOTP)
			System.out.println("#AdSendSecretKey OTP -  END Id : " + AEO.getUserId() + " Send Data : " + result);


		Response<String> resp = new Response<String>(HttpStatus.OK, result, AEO.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
