package com.chameleon.tollgate.otp.service;
import com.chameleon.tollgate.otp.OtpConfig;
import com.chameleon.tollgate.otp.controller.OtpController;
import com.chameleon.tollgate.otp.dao.*;
import com.chameleon.tollgate.otp.dto.*;
import com.chameleon.tollgate.otp.module.TOtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OTPService implements IOTPService {

	public OTPService() { super(); }
	
	@Autowired
	private OtpDAO dao;
			
	//------------------------------------------------------------
	
	@Override
	public String DbRegister(final String userId) {
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 0  Start Id : " + userId);
		
		final long currentTime = System.currentTimeMillis(); // Get Current Time
		
		
		String secretKey = SecretKeyCreate(userId,currentTime); // Create SecretKey
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 1 Create SecretKey : " + secretKey);
		if (secretKey == null)
			return ReturnMessage.REGISTER_INFORMATION;

		boolean registerResult = dao.Register(userId,secretKey,currentTime); //Otp DB Register
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 2(Final) Otp Register Result : " + registerResult + "\r\n");

		if(! registerResult)
			return ReturnMessage.REGISTER_UNKNOWN;
		
		secretKey = null;
		return ReturnMessage.SUCCESS;
	}
	
	
	@Override
	public String Verify(final AuthExchangeOtp AEO)
	{		
		final long currentTime = System.currentTimeMillis(); // Get CurrentTime
		
		if (OtpController.DEBUGOTP)
			System.out.println("\r\n#Verify - 0  Start Id : " + AEO.getUserId() + " Otp : " + AEO.getData() + " timestamp : " + currentTime);


		if (AEO.getUserId() == null || AEO.getData() == null)
		{
			if (OtpController.DEBUGOTP)
				System.out.println("#Verify - Fail : Otp Data Information");
			return ReturnMessage.VERIFY_INFORMATION;		
		}
		
		String userSecretKey = dao.GetUserSecretKey(AEO.getUserId()); // Get User SecretKey
		
		TOtp totp = new TOtp(userSecretKey, OtpConfig.CreateCycle, OtpConfig.OtpSize, OtpConfig.Hashtype); // TOtp Object Create
		String calculateOtpNumber = totp.ComputeTotp(currentTime); // Get Otp value
		boolean verifyResult = AEO.getData().equals(calculateOtpNumber); // Client Otp Value Equals.(Server Otp Value)
		
		if (OtpController.DEBUGOTP)
			System.out.println("#Verify - 1(Final)  SecretKey : " + userSecretKey + "  Server Otp : " + calculateOtpNumber + " Client Otp : " + AEO.getData());
		
		
		if (verifyResult)
			return ReturnMessage.SUCCESS;
		
		userSecretKey = null;
		return ReturnMessage.VERIFY_FAIL;
	}
	
	
	@Override
	public String AdRequestDataSecretKey(final String userId) // Android Request SecretKey
	{
		 final String secretEncryptKey = dao.GetUserSecretKey(userId); // Get User SecretKey
			if (OtpController.DEBUGOTP)
				System.out.println("#AdRequestDataSecretKey - 0(Final)  id : " + userId + " SecretKey : " + secretEncryptKey);
		 return secretEncryptKey;
	}
	
	
	//----------------------------------------------------------------
	
	
	private String SecretKeyCreate(final String userId,final long timeStamp)
	{
		String pcSid = dao.GetUserSid(userId); // Get User Sid
		if (OtpController.DEBUGOTP)
			System.out.println("#SecretKeyCreate - 0  Start pcSid : " + pcSid);
		if(pcSid == null)
		{
			if (OtpController.DEBUGOTP) 
				System.out.println("#SecretKeyCreate  Fail : pcSid NULL");
			return null;
		}
				
		String phoneToken = dao.GetUserToken(userId); // Get User PhoneToken
		if (OtpController.DEBUGOTP)
			System.out.println("#SecretKeyCreate - 1  phoneToken : " + phoneToken);
		if(phoneToken == null)
		{
			if (OtpController.DEBUGOTP)
				System.out.println("#SecretKeyCreate  Fail : phoneToken NULL");
			return null;
		}
		
		
		//Register Create SecretKey - Start
		StringBuilder secretKey = new StringBuilder();
		secretKey.append("tollgate-");
		
		int maximumIndex = 0;
		maximumIndex = phoneToken.split(":")[0].length();
		secretKey.append(phoneToken.split(":")[0].substring(maximumIndex-4,maximumIndex)).append("-");
		
		final int countMaximum = 3;
		
		for(int mainCount=0; mainCount < countMaximum ; mainCount++)
		{
			maximumIndex = pcSid.split("-")[mainCount].length();
			secretKey.append(pcSid.split("-")[mainCount].charAt(maximumIndex-4)).append(pcSid.split("-")[mainCount].charAt(maximumIndex-1));
			final int sideValue = SecretKeyTimestampDigit(timeStamp,mainCount);
			secretKey.append(phoneToken.charAt(phoneToken.length()-sideValue));
			secretKey.append(pcSid.split("-")[mainCount].charAt(maximumIndex-3)).append(pcSid.split("-")[mainCount].charAt(maximumIndex-2));
			
			if(mainCount != countMaximum-1)
				secretKey.append("-");	
		}
		//Register Create SecretKey - End
		
		try 
		{
			if (OtpController.DEBUGOTP)
				System.out.println("#SecretKeyCreate - 2(Final)  SecretKey : " + secretKey.toString());
			return secretKey.toString(); //Return Create SecretKey
		} finally {
			secretKey = null;
			pcSid = phoneToken = null; // Data Reset
		}
	}
	
	private int SecretKeyTimestampDigit(final long timeStamp,final int digit)	
	{ 
		long result = 0;
		long tempTimeStamp = timeStamp;
		for(int mainCount=0; mainCount<digit+1; mainCount++)
		{
			result = tempTimeStamp % 10;
			tempTimeStamp /= 10;
		}
		return (int)result;
	}
}
