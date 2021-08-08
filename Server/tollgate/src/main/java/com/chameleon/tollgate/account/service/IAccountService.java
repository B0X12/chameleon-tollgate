package com.chameleon.tollgate.account.service;

import java.sql.SQLException;
import java.util.List;

import com.chameleon.tollgate.account.dto.*;

public interface IAccountService {
	public boolean logIn(Account account) throws SQLException;

	public boolean signUp(Account account) throws SQLException;

	public boolean mappingPC(MapPC map_pc);

	public String getUserByUID(String uid) throws SQLException;

	public boolean unmapUserByUID(String uid);

	public int getFactorFlagByUser(String user) throws SQLException;

	List<MapPC> getRegisteredPCList(String user) throws SQLException;

	boolean updatePCAlias(MapPC mapPC);

	boolean updateFactorFlag(String user, int flagToUpdate, boolean enable);	
}
