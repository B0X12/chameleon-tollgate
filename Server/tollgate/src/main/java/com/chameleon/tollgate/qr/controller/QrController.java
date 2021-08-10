package com.chameleon.tollgate.qr.controller;

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
import com.chameleon.tollgate.otp.dto.AuthExchangeOtp;
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

@RestController
public class QrController {
	private static final boolean DEBUGQR = true;
	
	@Autowired
	private QrService qrService;

	//WaitVerify
	SessionList sessions;
	AuthList status;
	
	
	//Client QR Create Encrypt(SHA) Data
	@PostMapping(path=Path.DATA_QR)
	public ResponseEntity<Response<String>> GetClientCreateQrData(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeQr AEQ) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals("Tollgate-client") == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		if(DEBUGQR)
			System.out.println("#GetClientCreateQrData -  Start Id : " + AEQ.getUserId());

		String result = ReturnMessage.VERIFY_INFORMATION;
		
		if(!qrService.FindId(AEQ.getUserId()))
		{
			if(DEBUGQR)
				System.out.println("#GetClientCreateQrData -  Fail : Null User Id");
			
			Response<String> resp = new Response<String>(HttpStatus.OK, result, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}

		result = qrService.SendClientCreateQrData(AEQ.getUserId()); //Get TimeStamp SHA512 Data

		if(DEBUGQR)
			System.out.println("#GetClientCreateQrData -  End Id : " + AEQ.getUserId() + " Send Data : " + result);

		Response<String> resp = new Response<String>(HttpStatus.OK, result, AEQ.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	
	//Client Qr Auth Wait
	@PostMapping(path = Path.AUTH_QR)
	public ResponseEntity<Response<Boolean>> WaitQrToClientVerifyAuth(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody AuthExchangeQr AEQ) throws UnauthorizedUserAgentException,Exception 
	{
		this.status = new AuthList();
		this.sessions = new SessionList();
		
		if (userAgent.equals(Property.USER_AGENT) == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);

		this.sessions.add(AEQ.getUserId(),AEQ.getTimestamp()); // Session Add
		this.status.add(AEQ.getUserId()); //Status Add
		this.status.findStatus(AEQ.getUserId()).data = AEQ.getData(); //Save QrData
		
		if(DEBUGQR)
			System.out.println("#WaitQrToClientVerifyAuth -  Start Id : " + AEQ.getUserId() + " Status : Wait");
		
		Boolean sessionResult = status.waitVerify(AEQ.getUserId()); // result Get (Timeout(60s) = false || function AndroidVerify Success = true else false)
		
		if(DEBUGQR)
			System.out.println("#WaitQrToClientVerifyAuth -  End Id : " + AEQ.getUserId() + " Status : End  Verify Result : " + sessionResult);
		
		this.status.remove(AEQ.getUserId()); //Status remove
		this.sessions.remove(AEQ.getUserId()); //Status remove
		
		if(sessionResult == null) // null = timeout
		{
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK,false, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		
		Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK,true, AEQ.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	
	//Client Auth Wait to Android Send
	@PostMapping(path = Path.AUTH_QR + "{id}")
	public ResponseEntity<Response<String>> AdSendData(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String userId,@RequestBody AuthExchangeQr AEQ) throws UnauthorizedUserAgentException,Exception 
	{
		if (userAgent.equals(Property.USER_AGENT) == false)
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		if(DEBUGQR)
			System.out.println("#AdSendData -  Start Id : " + AEQ.getUserId() + " Send Data : " + AEQ.getData());

		if(this.sessions.isExist(new SessionTime(AEQ.getUserId(),AEQ.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
	
		if(this.status.findStatus(AEQ.getUserId()).data == null)
		{
			Response<String> resp = new Response<String>(HttpStatus.OK, ReturnMessage.UNKNOWN, AEQ.getTimestamp());
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		

		String verifyResult = ReturnMessage.SUCCESS;

		if(! userId.equals(AEQ.getUserId())) // Qr Data ID And Path userId Eqauls
			verifyResult = ReturnMessage.VERIFY_INFORMATION;

		
		if(verifyResult == ReturnMessage.SUCCESS) //QrData And Android SendQrData Equals
			verifyResult = qrService.AdSendDecryptDataVerify(this.status.findStatus(AEQ.getUserId()).data,AEQ.getData());
		
		boolean verifyResultBool = false;
		if(verifyResult == ReturnMessage.SUCCESS) // QrData Equals Success = true
			verifyResultBool = true;
		
		if(DEBUGQR)
			System.out.println("#AdSendData -  End Id : " + AEQ.getUserId() + " Verify Result : " + verifyResult);

		
		this.status.verify(AEQ.getUserId(), verifyResultBool);
		
		Response<String> resp = new Response<String>(HttpStatus.OK, verifyResult, AEQ.getTimestamp());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
		
}
