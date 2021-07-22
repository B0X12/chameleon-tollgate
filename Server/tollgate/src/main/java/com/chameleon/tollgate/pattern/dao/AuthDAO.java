package com.chameleon.tollgate.pattern.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.exception.*;
import com.chameleon.tollgate.pattern.dao.exception.PatternError;
import com.chameleon.tollgate.pattern.dao.exception.PatternResultException;
import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;

@Component
public class AuthDAO extends SQLiteManager implements IAuthDAO {
	public AuthDAO() {
		super();
	}
	
	@Override
	public boolean setPattern(String id, String pattern) throws Exception {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		
		PreparedStatement state;
		int count = super.getCountOf(Table.AUTH_PATTERN, "id", id);
		if(count == 0) {
			state = super.connection.prepareStatement("insert into " + Table.AUTH_PATTERN + " values(?, ?");
			state.setString(1, id);
			state.setString(2, pattern);
		}
		else if(count == 1) {
			state = super.connection.prepareStatement("update " + Table.AUTH_PATTERN + " set pattern = ? where id = ?");
			state.setString(1, pattern);
			state.setString(2, id);
		}
		else
			throw new DatabaseResultException(DBError.MANY_TOKEN);
		
		state.executeUpdate();
		
		return true;
	}
	
	@Override
	public String getPattern(String id) throws Exception {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		
		int count = super.getCountOf(Table.AUTH_PATTERN, "id", id);
		if(count < 1)
			throw new PatternResultException(PatternError.NO_PATTERN);
		else if(count > 1)
			throw new PatternResultException(PatternError.MANY_PATTERN);
		
		PreparedStatement state = super.connection.prepareStatement("select pattern from " + Table.AUTH_PATTERN + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
}