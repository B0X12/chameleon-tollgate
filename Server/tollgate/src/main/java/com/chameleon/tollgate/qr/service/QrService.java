package com.chameleon.tollgate.qr.service;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.otp.service.ReturnMessage;
import com.chameleon.tollgate.qr.dao.QrDAO;

@Service
public class QrService implements IQrService 
{
	@Autowired
	private QrDAO dao;
	
	@Override
	public String SendClientCreateQrData(final String userId)
	{
		String result = null;
		final long currentTime = System.currentTimeMillis();
		
		result = getSHA512(Long.toString(currentTime)+userId);
		return result;
	}
	
	
	@Override
	public String AdSendDecryptDataVerify(final String serverData,final String adData)
	{
		String result = ReturnMessage.SUCCESS;
		
		if(serverData == null || adData == null)
			result = ReturnMessage.FAIL_VERIFY_INFORMATION;
			
		if(!serverData.equals(adData))
			result = ReturnMessage.FAIL_VERIFY;
			
		return result;
	}
	
	@Override
	public boolean FindId(final String userId)
	{
		return dao.GetUserIdFind(userId);
	}

	
	//----------------------------------------------------------------------------------------------
	
	private String getSHA512(final String plainData) //Qr Create Data encrypt
	{
		String result = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(plainData.getBytes("utf8"));
			result = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) { };

		return result;
		
	}
	
}
