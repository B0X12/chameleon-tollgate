package com.chameleon.tollgate;

import android.content.Intent;
import android.util.Log;

import com.chameleon.tollgate.fcm.FCMService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String getHash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(value.getBytes("utf8"));
            return String.format("%0128x",new BigInteger(1,md.digest()));
        } catch (Exception ex) {
            Log.d("Tollgate", ex.getMessage());
            return null;
        }
    }

    public static Intent getTypeDataIntent(final Intent mainActivityIntent, final String type)
    {
        try {
            if(type.compareTo(FCMService.fcmIntent.getExtras().getString("Type")) == 0)
                return FCMService.fcmIntent;
        } catch(Exception e) {};

        try {
            if (type.compareTo(mainActivityIntent.getExtras().getString("Type")) == 0)
                return mainActivityIntent;
        } catch(Exception e) {};

        return null;
    }

    public static void FcmIntentDelete(){ FCMService.fcmIntent=null; }
}