package com.chameleon.tollgate.account.service;

public interface IAdAccountService {
	public boolean login(String id, String pwd) throws Exception;
	
	public boolean logout(String id) throws Exception;
	
	public boolean mapAndroid(String id, String token) throws Exception;
	
	public String getID(String token) throws Exception;
}
