package com.chameleon.tollgate.otp.controller;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.otp.dto.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.otp.service.LogData;
import com.chameleon.tollgate.otp.service.OTPService;
import com.chameleon.tollgate.otp.service.ReturnMessage;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.OtpCode;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;

@RestController
public class OtpController {
	public static boolean DEBUGOTP = true;

	@Autowired
	private OTPService otpService;
	
	@Autowired
	UserHistory history;
	
	
	@PostMapping(path = Path.REGIST_OTP) // - 1
	public ResponseEntity<Response<String>> OtpRegister(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeOtp AEO, long timestamp,HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		//LogData Create & INIT
		LogData ld = new LogData(hsr.getRemoteAddr(),AEO.getUserId());
		
		if(DEBUGOTP)
			System.out.println("#REGISTER OTP -  START Id : " + AEO.getUserId());
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.START_REGISTER,"Start (Otp Register)");
		
		
		if (userAgent.equals("Tollgate-client") == false)
		{
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTMATCH_DATA,"Fail UserAgent");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}


		//Request Data Check
		if(AEO.getUserId() == null || AEO.getTimestamp() == 0)
		{
			if(DEBUGOTP)
				System.out.println("#REGISTER OTP - End Request Data Check Fail  UserId: " + AEO.getUserId());
			
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"(Otp Register) - Request Data Check Fail");
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL_REGISTER_INFORMATION, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}


		String result = otpService.DbRegister(ld, AEO); // Result Save

		//Result
		if(DEBUGOTP)
			System.out.println("#REGISTER OTP -  END Result : " + result);
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.RESULT_REGISTER,"Register Result : " + result); 

		
		Response<String> resp = new Response<String>(HttpStatus.OK, result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}


	
	@PostMapping(path = Path.AUTH_OTP) // - 2
	public ResponseEntity<Response<String>> OtpVerify(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeOtp AEO, long timestamp,String sid,HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		//LogData Create & INIT
		LogData ld = new LogData(hsr.getRemoteAddr(),AEO.getUserId());

		if(DEBUGOTP)
			System.out.println("#Verify OTP -  START Id : " + AEO.getUserId() + " SendOtpValue : " + AEO.getData());		
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.START_VERIFY,"Verify Start");

		if (userAgent.equals("Tollgate-client") == false)
		{
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTMATCH_DATA,"Verify Fail UserAgent");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}


		//Request Data Check
		if(AEO.getUserId() == null || AEO.getTimestamp() == 0 || AEO.getData() == null)
		{
			if(DEBUGOTP)
				System.out.println("#Verify OTP -  End Request Data Check Fail  UserId: " + AEO.getUserId());
			
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"Verify Request Data Check Fail");
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL_REGISTER_INFORMATION, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		
		//Result
		String result = otpService.Verify(ld,AEO);

		if(DEBUGOTP)
			System.out.println("#Verify OTP -  END Id : " + AEO.getUserId() + " Result : " + result);
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.RESULT_VERIFY,"Verify Result : " + result);		
		
		if(result == ReturnMessage.SUCCESS)
			history.write(ld.getUserId(), HistoryFactor.OTP,sid,HistoryResult.SUCCESS);
		else
			history.write(ld.getUserId(), HistoryFactor.OTP,sid,HistoryResult.FAIL);
			
		
		Response<String> resp = new Response<String>(HttpStatus.OK,result, timestamp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	
	@PostMapping(path=Path.DATA_OTP+"{id}") // - 3
	public ResponseEntity<Response<String>> AdGetDataSecretKey(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String userId,@RequestBody AuthExchangeOtp AEO,HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		//LogData Create & INIT
		LogData ld = new LogData(hsr.getRemoteAddr(),AEO.getUserId());

		if(DEBUGOTP)
			System.out.println("#AdSendSecretKey OTP -  START Id : " + AEO.getUserId());
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.START_AD_DATA,"AdSecretKey Start");

		
		if (userAgent.equals("Tollgate-client") == false)
		{
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTMATCH_DATA,"AdSecretKey Fail UserAgent");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		//Request Data Check
		if(AEO.getUserId() == null || AEO.getTimestamp() == 0)
		{
			if(DEBUGOTP)
				System.out.println("#AdSendSecretKey OTP - Request Data Check Fail  UserId: " + AEO.getUserId());
			
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"Request Data Check Fail" + AEO.getUserId());
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL, AEO.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		
		//Not Equals Request UserId and Url Path UserId
		if(! AEO.getUserId().equals(userId))
		{
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTMATCH_DATA,"Request Data Not Match  Url Path UserId : " + userId);
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL, AEO.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);

		}
		
		//Result
		String result = otpService.AdRequestDataSecretKey(AEO); // GetSecretKey Fail : ReturnMessage.UNKNOWN
		
		if(DEBUGOTP)
			System.out.println("#AdSendSecretKey OTP -  END Id : " + AEO.getUserId() + " Send Data : " + result);
		TollgateLog.i(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.RESULT_AD_DATA,"Result : " + result);


		Response<String> resp = new Response<String>(HttpStatus.OK, result, AEO.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
}
