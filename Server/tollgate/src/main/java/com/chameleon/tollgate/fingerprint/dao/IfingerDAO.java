package com.chameleon.tollgate.fingerprint.dao;

import java.sql.SQLException;

public interface IfingerDAO {
	
	public String GetToken(String id) throws SQLException;
}