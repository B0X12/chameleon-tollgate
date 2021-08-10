package com.chameleon.tollgate.pattern.service;

public interface IAuthService {
	boolean SendSignal(String id, long timestamp, String pc) throws Exception;
	boolean SetPattern(String id, String pattern, String ip) throws Exception;
	boolean VerifyPattern(String id, String pattern, String ip) throws Exception;
}
