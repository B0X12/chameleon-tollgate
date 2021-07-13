package com.chameleon.tollgate.pattern.service;

public interface IAuthService {
	boolean SendSignal(String id, int timestamp) throws Exception;
	boolean SetPattern(String id, String pattern) throws Exception;
	boolean VerifyPattern(String id, String pattern) throws Exception;
}
