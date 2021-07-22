package com.chameleon.tollgate.otp.module;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
public class KeyHash implements IKeyProvider
{
	//private static final Object platformSupportSync = new Object();

	private final Object stateSync = new Object();
	private final byte[] KeyData;
	private final int SecretKeyLength;

	public KeyHash(byte[] Secretkey)
	{
		if (!(Secretkey != null))
		{
			throw new NullPointerException("Not Found Key");
		}
		if (!(Secretkey.length > 0))
		{
			throw new IllegalArgumentException("Emtpy to Key");
		}

		this.SecretKeyLength = Secretkey.length;
		
		//Calculate Object Create
		BigDecimal KeyValue = new BigDecimal(SecretKeyLength);
		BigDecimal CalculateValue = new BigDecimal(16);

		BigDecimal EncryptionKeyLength = CalculateValue.multiply(KeyValue.divide(CalculateValue)); //(KeyValue/CalculateValue) = 1.25 * 16 (CalculateValue)
		this.KeyData = new byte[EncryptionKeyLength.byteValue()];
		 
		synchronized (this.stateSync) //Thread sync
		{
			System.arraycopy(Secretkey, 0, KeyData, 0, this.SecretKeyLength); // this.KeyDate to key Value copy
			// source, sourcePos, Destination, DestinationPos,Length
		}

	}

	public final byte[] ComputeHmac(int hashtype,final byte[] data) //IKeyProvider
	{
		SecretKeySpec SecretHashkey = new SecretKeySpec(KeyData,GetStringHmacHash(hashtype)); // HMAC To SecretKey Create
		
		Mac hmac = null;
		try {
			hmac = Mac.getInstance(GetStringHmacHash(hashtype)); //Encryption Type Instance Create
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try {
			hmac.init(SecretHashkey); //argument to Mac Object Initialize
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return hmac.doFinal(data); //Mac Object Finished
	}
	
	private static String GetStringHmacHash(int Hashtype)
	{
		switch (Hashtype)
		{
			case HashType.SHA256:
				return "HmacSHA256";
			case HashType.SHA512:
				return "HmacSHA512";
		}
		return "HmacSHA1";
	}
}