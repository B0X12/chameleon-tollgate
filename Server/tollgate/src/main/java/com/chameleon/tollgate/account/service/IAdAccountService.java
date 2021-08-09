package com.chameleon.tollgate.account.service;

public interface IAdAccountService {
	public boolean login(String id, String pwd, String ip) throws Exception;
	
	public boolean logout(String id, String ip) throws Exception;
	
	public boolean mapAndroid(String id, String token, String ip) throws Exception;
	
	public String getID(String token) throws Exception;
}
