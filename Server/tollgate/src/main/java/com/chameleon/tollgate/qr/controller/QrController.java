package com.chameleon.tollgate.qr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.otp.service.ReturnMessage;
import com.chameleon.tollgate.qr.dto.AuthExchangeQr;
import com.chameleon.tollgate.qr.service.QrService;
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.QrCode;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;

@RestController
public class QrController {
	private static final boolean DEBUGQR = true;
	
	@Autowired
	private QrService qrService;

	@Autowired
	UserHistory history;
	
	//WaitVerify
	SessionList sessions;
	AuthList status;
	
	
	//Client QR Create Encrypt(SHA) Data
	@PostMapping(path=Path.DATA_QR)
	public ResponseEntity<Response<String>> GetClientCreateQrData(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeQr AEQ,HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		if(DEBUGQR)
			System.out.println("#GetClientCreateQrData -  Start Id : " + AEQ.getUserId());
		
		//Start
		TollgateLog.i(hsr.getRemoteAddr(), AEQ.getUserId(), LogFactor.QR,QrCode.START_GETDATACLIENT,"(GetClientCreateQrData) Start");

		//Request Data Check
		if(AEQ.getUserId() == null || AEQ.getTimestamp() == 0)
		{
			if(DEBUGQR)
				System.out.println("#GetClientCreateQrData QR - End Request Data Check Fail  UserId: " + AEQ.getUserId());
			
			TollgateLog.w(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR,QrCode.NOTFOUND_DATA,"(GetClientCreateQrData) Request Data Check Fail" + AEQ.getUserId());
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL_REGISTER_INFORMATION, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		
		// DataBase UserId Search
		if(!qrService.FindId(AEQ.getUserId())) 
		{
			if(DEBUGQR)
				System.out.println("#GetClientCreateQrData -  Fail : Null User Id");
			
			TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR,QrCode.NOTFOUND_DATA,"(GetClientCreateQrData) Not Found Register User DataBase");

			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL_VERIFY_INFORMATION, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}

		
		//Get Data
		String result = qrService.SendClientCreateQrData(AEQ.getUserId()); //Get TimeStamp SHA512 Data

		if(DEBUGQR)
			System.out.println("#GetClientCreateQrData -  End Id : " + AEQ.getUserId() + " Send Data : " + result);

		//End
		TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR,QrCode.END_GETDATACLIENT,"(GetClientCreateQrData) End Result : " + result);

		Response<String> resp = new Response<String>(HttpStatus.OK, result, AEQ.getTimestamp());
		result = null; //data Reset
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	
	//Client Qr Auth Wait
	@PostMapping(path = Path.AUTH_QR)
	public ResponseEntity<Response<Boolean>> WaitQrToClientVerifyAuth(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeQr AEQ,String sid, HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		if(DEBUGQR)
			System.out.println("#WaitQrToClientVerifyAuth -  Start Id : " + AEQ.getUserId() + " Status : Wait");

		//Start
		TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR, QrCode.START_WAITCLIENT,"(WaitQrToClientVerifyAuth) Start");

		
		if (userAgent.equals(Property.USER_AGENT) == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);		
		
		//Request Data Check
		if(AEQ.getUserId() == null || AEQ.getTimestamp() == 0 || AEQ.getData() == null)
		{
			if(DEBUGQR)
				System.out.println("#WaitQrToClientVerifyAuth QR - Request Data Check Fail  UserId: " + AEQ.getUserId());
			
			TollgateLog.w(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR,QrCode.NOTFOUND_DATA,"(WaitQrToClientVerifyAuth) Request Data Check Fail");
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK,false, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		this.status = new AuthList();
		this.sessions = new SessionList();
		
		this.sessions.add(AEQ.getUserId(),AEQ.getTimestamp()); // Session Add
		this.status.add(AEQ.getUserId()); //Status Add
		this.status.findStatus(AEQ.getUserId()).data = AEQ.getData(); //Save QrData
				
		TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR, QrCode.VERIFY_WAIT,"(WaitQrToClientVerifyAuth) Wait");
		Boolean sessionResult = status.waitVerify(AEQ.getUserId()); // result Get (Timeout(60s) = false || function AndroidVerify Success = true else false)

		this.status.remove(AEQ.getUserId()); //Status remove
		this.sessions.remove(AEQ.getUserId()); //Status remove

		
		if(DEBUGQR)
			System.out.println("#WaitQrToClientVerifyAuth -  End Id : " + AEQ.getUserId() + " Status : End  Verify Result : " + sessionResult);
		
		
		// Wait Result
		if(sessionResult == null) // null = timeout
		{
			TollgateLog.i(hsr.getRemoteAddr(), AEQ.getUserId(), LogFactor.QR, QrCode.VERIFY_TIMEOUT,"(WaitQrToClientVerifyAuth) Wait Fail : TimeOut");
			history.write(AEQ.getUserId(), HistoryFactor.QR,sid,HistoryResult.FAIL);
		}
		else if(sessionResult == false) // false = id success data Fail
		{
			TollgateLog.w(hsr.getRemoteAddr(), AEQ.getUserId(), LogFactor.QR, QrCode.FAIL,"(WaitQrToClientVerifyAuth) Wait Fail : Not Match" + AEQ.getUserId());
			history.write(AEQ.getUserId(), HistoryFactor.QR,sid,HistoryResult.FAIL);
		}
		else
		{
			TollgateLog.i(hsr.getRemoteAddr(), AEQ.getUserId(), LogFactor.QR, QrCode.SUCCESS,"(WaitQrToClientVerifyAuth) Wait Success" + AEQ.getUserId());
			history.write(AEQ.getUserId(), HistoryFactor.QR,sid,HistoryResult.SUCCESS);
			
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK,true, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		//Fail Return
		Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK,false, AEQ.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	
	}
	
	
	//Client Auth Wait to Android Send
	@PostMapping(path = Path.AUTH_QR + "{id}")
	public ResponseEntity<Response<String>> AdSendData(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String userId,@RequestBody AuthExchangeQr AEQ,HttpServletRequest hsr) throws UnauthorizedUserAgentException,Exception 
	{
		//Start
		TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR, QrCode.START_AD_DATA,"(AdSendData) Start");

		if (userAgent.equals(Property.USER_AGENT) == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		if(DEBUGQR)
			System.out.println("#AdSendData -  Start Id : " + AEQ.getUserId() + " Send Data : " + AEQ.getData());
		
		if(AEQ.getUserId() == null || AEQ.getTimestamp() == 0 || AEQ.getData() == null)
		{
			if(DEBUGQR)
				System.out.println("#WaitQrToClientVerifyAuth QR - Request Data Check Fail  UserId: " + AEQ.getUserId());
			
			TollgateLog.w(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR,QrCode.NOTFOUND_DATA,"(AdSendData) Request Data Check Fail" + AEQ.getUserId());
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.FAIL_VERIFY_INFORMATION, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}

		if(this.sessions.isExist(new SessionTime(AEQ.getUserId(),AEQ.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
	
		// Not Found Session
		if(this.status.findStatus(AEQ.getUserId()).data == null) 
		{
			if(DEBUGQR)
				System.out.println("#AdSendData - Wait Client Not Found Start Id : " + AEQ.getUserId() + " Send Data : " + AEQ.getData());
			TollgateLog.w(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR, QrCode.NOTMATCH_DATA,"(AdSendData) Not Found DataBase Register UserID");

			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.UNKNOWN, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		

		String verifyResult = ReturnMessage.SUCCESS;

		if(! userId.equals(AEQ.getUserId())) // Qr Data ID And Path userId Eqauls
			verifyResult = ReturnMessage.FAIL_VERIFY_INFORMATION;
		
		if(verifyResult == ReturnMessage.SUCCESS) //QrData And Android SendQrData Equals
			verifyResult = qrService.AdSendDecryptDataVerify(this.status.findStatus(AEQ.getUserId()).data,AEQ.getData());
		
		boolean verifyResultBool = false;
		if(verifyResult == ReturnMessage.SUCCESS) // QrData Equals Success = true
			verifyResultBool = true;
		
		//Client Wait Send
		this.status.verify(AEQ.getUserId(), verifyResultBool);

		
		if(DEBUGQR)
			System.out.println("#AdSendData -  End Id : " + AEQ.getUserId() + " Verify Result : " + verifyResult);

		//Start
		TollgateLog.i(hsr.getRemoteAddr(),AEQ.getUserId(), LogFactor.QR, QrCode.END_AD_DATA,"(AdSendData) End Result : " + verifyResultBool);

				
		Response<String> resp = new Response<String>(HttpStatus.OK, verifyResult, AEQ.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
		
}
