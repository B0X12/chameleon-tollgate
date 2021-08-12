package com.chameleon.tollgate.otp.service;
import com.chameleon.tollgate.otp.OtpConfig;
import com.chameleon.tollgate.otp.controller.OtpController;
import com.chameleon.tollgate.otp.dao.*;
import com.chameleon.tollgate.otp.dto.*;
import com.chameleon.tollgate.otp.module.TOtp;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.OtpCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OTPService implements IOTPService {

	public OTPService() { super(); }
	
	@Autowired
	private OtpDAO dao;
			
	//------------------------------------------------------------
	
	@Override
	public String DbRegister(final LogData ld, final AuthExchangeOtp AEO) 
	{
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 0  Start Id : " + AEO.getUserId());
		
		final long currentTime = System.currentTimeMillis(); // Get Current Time
		
		
		//Create SecretKey
		String secretKey = SecretKeyCreate(ld,AEO.getUserId(),currentTime);
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 1 Create SecretKey : " + secretKey);
		
		
		//SecretKey Create Fail = ReturnMessage.FAIL_REGISTER_INFORMATION
		if (secretKey == null) 
		{
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.FAIL,"Register Fail - Create SecretKey");
			return ReturnMessage.FAIL_REGISTER_INFORMATION;
		}
		
		//Otp DB Register
		boolean registerResult = dao.Register(AEO.getUserId(),secretKey,currentTime);
		
		secretKey = null; //Reset
		if (OtpController.DEBUGOTP)
			System.out.println("#DbRegister - 2(Final) Otp Register Result : " + registerResult + "\r\n");

		// Register Fail = ReturnMessage.FAIL_REGISTER_UNKNOWN
		if(! registerResult)
		{
			TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.FAIL,"Register Fail");
			return ReturnMessage.FAIL_REGISTER_UNKNOWN;
		}

		//All Success = ReturnMessage.SUCCESS
		TollgateLog.w(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.SUCCESS,"Register Success");
		return ReturnMessage.SUCCESS;
	}
	
	
	@Override
	public String Verify(final LogData ld, final AuthExchangeOtp AEO)
	{		
		// Get CurrentTime
		final long currentTime = System.currentTimeMillis(); 
		
		if (OtpController.DEBUGOTP)
			System.out.println("Verify - 0  Start Id : " + AEO.getUserId() + " Otp : " + AEO.getData() + " timestamp : " + currentTime);
		
		// Get User SecretKey
		String userSecretKey = dao.GetUserSecretKey(AEO.getUserId());

		// Not Found SecretKey = ReturnMessage.FAIL_VERIFY_INFORMATION
		if(userSecretKey == null)
		{
			if (OtpController.DEBUGOTP)
				System.out.println("#Verify - Fail : Otp User SecretKey");
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"Verify Fail - Not Found User SecretKey");
			return ReturnMessage.FAIL_VERIFY_INFORMATION;		
		}
		
		//Create Totp Value
		TOtp totp = new TOtp(userSecretKey, OtpConfig.createCycle, OtpConfig.otpSize, OtpConfig.hashType); // TOtp Object Create
		final String calculateOtpNumber = totp.ComputeTotp(currentTime); // Get Otp value
		
		// Client Otp Value Equals (Server Otp Value)
		boolean verifyResult = AEO.getData().equals(calculateOtpNumber); 

		if (OtpController.DEBUGOTP)
			System.out.println("#Verify - 1(Final)  SecretKey : " + userSecretKey + "  Server Otp : " + calculateOtpNumber + " Client Otp : " + AEO.getData());

		userSecretKey = null; //User SecretKey Reset

		//Totp Value Equls Fail = ReturnMessage.FAIL_VERIFY
		if (verifyResult) 
			return ReturnMessage.SUCCESS;
		else
			return ReturnMessage.FAIL_VERIFY;
	}
	
	
	@Override
	public String AdRequestDataSecretKey(final AuthExchangeOtp AEO) // Android Request SecretKey
	{
		 final String secretEncryptKey = dao.GetUserSecretKey(AEO.getUserId()); // Get User SecretKey
		
		 if (OtpController.DEBUGOTP)
			System.out.println("#AdRequestDataSecretKey - 0(Final)  id : " + AEO.getUserId() + " SecretKey : " + secretEncryptKey);
		 
		 if(secretEncryptKey == null)
			 return ReturnMessage.UNKNOWN;
		 return secretEncryptKey;
	}
	
	
	//----------------------------------------------------------------
	
	
	private String SecretKeyCreate(final LogData ld, final String userId, final long timeStamp)
	{
		String pcSid = dao.GetUserSid(userId); // Get User Sid
		if (OtpController.DEBUGOTP)
			System.out.println("#SecretKeyCreate - 0  Start pcSid : " + pcSid);

		if(pcSid == null)
		{
			if (OtpController.DEBUGOTP) 
				System.out.println("#SecretKeyCreate  Fail : pcSid NULL");
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"SecretKey Create Fail - Not Found User Data : PC_SID");
			return null;
		}
				
		
		String phoneToken = dao.GetUserToken(userId); // Get User PhoneToken
		if (OtpController.DEBUGOTP)
			System.out.println("#SecretKeyCreate - 1  phoneToken : " + phoneToken);

		if(phoneToken == null)
		{
			if (OtpController.DEBUGOTP)
				System.out.println("#SecretKeyCreate  Fail : phoneToken NULL");
			TollgateLog.e(ld.getUserIp(),ld.getUserId(), LogFactor.OTP,OtpCode.NOTFOUND_DATA,"SecretKey Create Fail - Not Found User Data : PhoneToken");
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
			int sideValue = TimestampPositionNumber(timeStamp,mainCount+1);
   			secretKey.append(phoneToken.charAt( (phoneToken.length()-1) - sideValue) );
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
	
	private int TimestampPositionNumber(final long timeStamp,final int backPosition)	
	{ 
		final String longToStringTimeStamp = Long.toString(timeStamp);
		//return Integer.parseInt(longToStringTimeStamp.charAt(longToStringTimeStamp.length()- (backPosition+1)));
		return Character.getNumericValue(longToStringTimeStamp.charAt(longToStringTimeStamp.length() - backPosition));
	}
}
