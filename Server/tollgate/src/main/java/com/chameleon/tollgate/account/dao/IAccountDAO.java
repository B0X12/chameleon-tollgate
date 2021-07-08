package com.chameleon.tollgate.account.dao;

import java.sql.SQLException;

import com.chameleon.tollgate.account.dto.*;
import com.chameleon.tollgate.account.exception.UserAlreadyExistException;

public interface IAccountDAO {
	public boolean checkIDPW(Account account) throws SQLException;
	public boolean insertAccountInfo(Account account) throws UserAlreadyExistException, SQLException;
	public boolean insertMapPCInfo(MapPC map_pc);
	public String getUserByUID(String uid) throws SQLException;
	public boolean deleteMapPCInfo(String uid);
}
