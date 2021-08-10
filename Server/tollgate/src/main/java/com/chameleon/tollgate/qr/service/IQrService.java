package com.chameleon.tollgate.qr.service;

public interface IQrService {
	String SendClientCreateQrData(final String userId);
	String AdSendDecryptDataVerify(final String serverData,final String adData);
	boolean FindId(final String userId);
}
