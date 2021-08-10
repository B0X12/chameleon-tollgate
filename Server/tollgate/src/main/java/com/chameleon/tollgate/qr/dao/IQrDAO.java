package com.chameleon.tollgate.qr.dao;

public interface IQrDAO {
	String GetUserSid(final String userID);
	String GetUserToken(final String userID);
	String GetUserSecretKey(final String userID);
	Long GetUserRegisterTime(final String userId);
	boolean GetUserIdFind(final String userId);
	boolean Register(final String userId, final String secertKey, final long registerTimeStamp);
}
