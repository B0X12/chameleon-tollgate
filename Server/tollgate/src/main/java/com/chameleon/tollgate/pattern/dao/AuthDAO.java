package com.chameleon.tollgate.pattern.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.exception.*;
import com.chameleon.tollgate.pattern.dao.exception.PatternError;
import com.chameleon.tollgate.pattern.dao.exception.PatternResultException;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.CommonCode;
import com.chameleon.tollgate.util.tollgateLog.dto.code.PatternCode;
import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;

@Component
public class AuthDAO extends SQLiteManager implements IAuthDAO {
	public AuthDAO() {
		super();
	}

	public void finalize() {
		if (super.isOpen()) {
			super.close();
		}
	}
	
	@Override
	public boolean setPattern(String id, String pattern, String ip) throws Exception {
		if(!super.isOpen()) {
			TollgateLog.e(ip, id, LogFactor.COMMON, CommonCode.DB_CONECTION, "Can't connect to database.");
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		}
		
		PreparedStatement state;
		int count = super.getCountOf(Table.AUTH_PATTERN, "id", id);
		if(count == 0) {
			state = super.connection.prepareStatement("insert into " + Table.AUTH_PATTERN + " values(?, ?)");
			state.setString(1, id);
			state.setString(2, pattern);
			TollgateLog.i(ip, id, LogFactor.PATTERN, PatternCode.SET_PATTERN, "Insert user\'s pattern to \'" + pattern + "\'.");
		}
		else if(count == 1) {
			state = super.connection.prepareStatement("update " + Table.AUTH_PATTERN + " set pattern = ? where id = ?");
			state.setString(1, pattern);
			state.setString(2, id);
			TollgateLog.i(ip, id, LogFactor.PATTERN, PatternCode.SET_PATTERN, "Update user\'s pattern to \'" + pattern + "\'.");
		}
		else {
			TollgateLog.e(ip, id, LogFactor.PATTERN, PatternCode.MANY_TOKEN, "The user has too many tokens. There are " + count + " tokens.");
			throw new DatabaseResultException(DBError.MANY_TOKEN);
		}
		
		state.executeUpdate();
		
		return true;
	}
	
	@Override
	public String getPattern(String id, String ip) throws Exception {
		if(!super.isOpen()) {
			TollgateLog.e(ip, id, LogFactor.COMMON, CommonCode.DB_CONECTION, "Can't connect to database.");
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		}
		
		int count = super.getCountOf(Table.AUTH_PATTERN, "id", id);
		if(count < 1) {
			TollgateLog.w(ip, id, LogFactor.PATTERN, PatternCode.NO_PATTERN, "There are no pattern.");
			throw new PatternResultException(PatternError.NO_PATTERN);
		}
		else if(count > 1) {
			TollgateLog.w(ip, id, LogFactor.PATTERN, PatternCode.MANY_PATTERN, "There are too many patterns. There are " + count + " patterns.");
			throw new PatternResultException(PatternError.MANY_PATTERN);
		}
		
		PreparedStatement state = super.connection.prepareStatement("select pattern from " + Table.AUTH_PATTERN + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
}