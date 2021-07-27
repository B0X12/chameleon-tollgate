package com.chameleon.tollgate.otp.service;
import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.otp.OtpConfig;
import com.chameleon.tollgate.otp.dao.*;
import com.chameleon.tollgate.otp.dto.*;
import com.chameleon.tollgate.otp.module.HashType;
import com.chameleon.tollgate.otp.module.TOtp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OTPService implements IOTPService {
	private static final boolean TestDebug = true;

	public OTPService() { super(); }
	
	@Autowired
	private OtpDAO dao;
	
	//Current Time
	private long CurrentTime;
		
	//Totp SecretKey
	private String SecretKey = null;
	//Pc Sid
	private String PcSid = null;
	//Phone Token
	private String PhoneToken = null;
		
	//------------------------------------------------------------
	
	@Override
	public String Register(AuthOtp AO) 
	{
		CurrentTime = System.currentTimeMillis();
		
		if (AO.id != null && AO.otp == null) // OTP Certification Click
		{
			if(TestDebug)
				System.out.println("\r\nBeFore -> id : " + AO.id + " otp : " + AO.otp);
						
			SecretKey = RandSecretKeyCreate(AO);
			
			if(SecretKey == null)
				return ErrorKind.REGISTER_INFORMATION;
			
			if(TestDebug)
				System.out.println("SecretKey : " + this.SecretKey);
			
			AO.timestamp = CurrentTime;
			
			//OTP Create
			TOtp totp = new TOtp(SecretKey,OtpConfig.CreateCycle, OtpConfig.OtpSize, OtpConfig.Hashtype); // TOtp Object Create
			AO.otp = totp.ComputeTotp(CurrentTime); // Totp Six,Eit Code Create (Hash Calculate)
			
			if(TestDebug)
				System.out.println("After -> id : " + AO.id + " otp : " + AO.otp + "\r\n");
			
			
			try 
			{
				if(dao.Register(AO) == false) // DB Register
				{
					dao.Delete(AO); // OTP Table Delete
					return ErrorKind.REGISTER_DATABASE;
				}				
				//FCM Send
				Map<String, String> OtpDTO = new HashMap<String, String>();
				OtpDTO.put("Type", "OTP");
				OtpDTO.put("SecretKey", SecretKey);
				OtpDTO.put("ServerCurrentTime", Long.toString(CurrentTime));
				OtpDTO.put("CreateCycle",Integer.toString(OtpConfig.CreateCycle));
				OtpDTO.put("OtpSize", Integer.toString(OtpConfig.OtpSize));
				OtpDTO.put("HashType", Integer.toString(OtpConfig.Hashtype));

				
				FCMSender FS = new FCMSender();
				FS.send(FCMSender.msgBuilder().setTitle("OTP 인증요청")
											  .setBody("OTP 값을 확인해서 인증해주세요.")
											  .setToken(PhoneToken)
											  .setData(OtpDTO)
											  .setClickAction("android.intent.action.OTP")
											  .build());
				if(TestDebug)
					System.out.println("#Otp Register Susscess. \r\n");

				return ErrorKind.SUCCESS;
				

			} catch (Exception e) {
				dao.Delete(AO); // OTP Table Delete
				return ErrorKind.REGISTER_DATABASE;
			}
		}
			
		dao.Delete(AO); // OTP Table Delete
		return ErrorKind.REGISTER_INFORMATION;
	}

	
	@Override
	public String Verify(AuthOtp AO)
	{
		CurrentTime = System.currentTimeMillis();

		if (AO.id == null || AO.otp == null)
		{
			dao.Delete(AO); // OTP Table Delete
			return ErrorKind.VERIFY_INFORMATION;
		}

		
		long TimeCalculate =(TimeUnit.MILLISECONDS.toSeconds(dao.GetUserTimestamp(AO.id)) + OtpConfig.CreateCycle) - TimeUnit.MILLISECONDS.toSeconds(CurrentTime);

		if (TimeCalculate < 0) 
		{	
			dao.Delete(AO); // OTP Table Delete
			System.out.println("#OTP TimeOut (Remaining Seconds : "+TimeCalculate+")");
			return ErrorKind.VERIFY_TIMEOUT;
		}
		
		if (TestDebug)
			System.out.println("#OTP Time Susscess. (Remaining Seconds : " + TimeCalculate + ")\r\n");

		String DatabaseOTP = dao.GetUserOtp(AO.id); // Get DB User OTP Value

		if(DatabaseOTP == null)
			return ErrorKind.VERIFY_DATABASE;


		if (DatabaseOTP.equals(AO.otp) == false) // OTP Equals
		{
			dao.Delete(AO); // OTP Table Delete
			
			if (TestDebug)
				System.out.println("#Otp Certification Failed\r\n");
			
			return ErrorKind.VERIFY_FAIL;
		}

		if (TestDebug)
			System.out.println("#Otp Certification Susscess\r\n");
		
		dao.Delete(AO); // OTP Table Delete
		return ErrorKind.SUCCESS;

	}
	
	//----------------------------------------------------------------
	
	
	
	private String RandSecretKeyCreate(AuthOtp AO)
	{
		PcSid = dao.GetUserSid(AO.id);
		
		if(PcSid == null)
		{
			System.out.println("#Not Found Pc Sid");
			return null;
		}
		
		if(TestDebug)
			System.out.println("pc_Sid : " + PcSid);
		
		PhoneToken = dao.GetUserToken(AO.id);
		
		if(PhoneToken == null)
		{
			System.out.println("#Not Found Phone Token");
			return null;
		}
		
		if(TestDebug)
			System.out.println("PhoneToken : " + PhoneToken);

		
		int DivisionNumber = 0;

		switch(OtpConfig.Hashtype)
		{
		case HashType.SHA256:
			DivisionNumber = HashType.SHA256_SecretKeyLength/2;
			break;
			
		case HashType.SHA512:
			DivisionNumber = HashType.SHA512_SecretKeyLength/2;
			break;
			
		default:
			DivisionNumber = HashType.SHA1/2;				
		}
		
		StringBuilder SecretKey = new StringBuilder();
		
		for(int count = 0; count < DivisionNumber; count++) //sid
		{
			double RandValue = Math.random();
			int SelectedIndex = (int)(RandValue * PcSid.length());
			SecretKey.append(PcSid.charAt(SelectedIndex));
		}
		
		for(int count = 0; count < DivisionNumber; count++)//token
		{
			double RandValue = Math.random();
			int SelectedIndex = (int)(RandValue * PhoneToken.length());
			SecretKey.append(PhoneToken.charAt(SelectedIndex));
		}
		
		return SecretKey.toString();
	}
}
