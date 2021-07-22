package com.chameleon.tollgate.otp.module;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;

public class KeyHash implements IKeyProvider {
    private final Object stateSync = new Object(); // Thread Sync
    private final byte[] KeyData;
    private final int SecretKeyLength;

    public KeyHash(final byte[] SecretKey)
    {
        if (!(SecretKey != null))
        {
            throw new NullPointerException("Not Found Key");
        }
        if (!(SecretKey.length > 0))
        {
            throw new IllegalArgumentException("Emtpy to Key");
        }

        this.SecretKeyLength = SecretKey.length;

        //Calculate Object Create
        BigDecimal KeyValue = new BigDecimal(SecretKeyLength);
        BigDecimal CalculateValue = new BigDecimal(16);

        BigDecimal EncryptionKeyLength = CalculateValue.multiply(KeyValue.divide(CalculateValue)); //(KeyValue/CalculateValue) = 1.25 * 16 (CalculateValue)
        this.KeyData = new byte[EncryptionKeyLength.byteValue()];

        synchronized (this.stateSync) //thread sync
        {
            System.arraycopy(SecretKey, 0, KeyData, 0, this.SecretKeyLength); // this.KeyDate to key Value copy
            // source, sourcePos, Destination, DestinationPos,Length
        }
    }

    public final byte[] ComputeHmac(final int hashtype,final byte[] data) //IKeyProvider
    {
        SecretKeySpec SecretHashKey = new SecretKeySpec(KeyData,GetStringHmacHash(hashtype)); // HMAC To SecretKey Create

        Mac hmac = null;
        try {
            hmac = Mac.getInstance(GetStringHmacHash(hashtype)); //Encryption Type Instance Create
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            hmac.init(SecretHashKey); //argument to Mac Object Initialize
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return hmac.doFinal(data); //Mac Object Finished
    }

    private static String GetStringHmacHash(final int hashtype)
    {
        switch (hashtype)
        {
            case HashType.SHA256:
                return "HmacSHA256";
            case HashType.SHA512:
                return "HmacSHA512";
        }
        return "HmacSHA1";
    }
}