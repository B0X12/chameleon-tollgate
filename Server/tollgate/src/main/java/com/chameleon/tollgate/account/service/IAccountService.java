package com.chameleon.tollgate.account.service;

import java.sql.SQLException;

import com.chameleon.tollgate.account.dto.*;

public interface IAccountService {
	public boolean logIn(Account account) throws SQLException;

	public boolean signUp(Account account) throws SQLException;

	public boolean mappingPC(MapPC map_pc);

	public String getUserByUID(String uid) throws SQLException;

	public boolean unmapUserByUID(String uid);	
}
