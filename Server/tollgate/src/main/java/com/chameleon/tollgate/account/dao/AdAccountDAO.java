package com.chameleon.tollgate.account.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.database.exception.DBError;
import com.chameleon.tollgate.database.exception.DatabaseConnectException;

@Component
public class AdAccountDAO extends SQLiteManager implements IAdAccountDAO {
	public String getPassword(String id) throws DatabaseConnectException, SQLException {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		
		PreparedStatement state = super.connection.prepareStatement("select pwd from " + Table.ACCOUNT + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
	
	public boolean isExistToken(String id, String token) throws SQLException, DatabaseConnectException {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = super.connection.prepareStatement("select count(*) from " + Table.MAP_ANDROID + " where id = ? and token = ?");
		state.setString(1, id);
		state.setString(2, token);
		ResultSet rs = state.executeQuery();
		int result = rs.getInt(1);
		rs.close();
		
		if(result != 1)
			return false;
		return true;
	}
	
	public int setToken(String id, String token) throws SQLException, DatabaseConnectException {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = super.connection.prepareStatement("insert into " + Table.MAP_ANDROID + " values(?,?)");
		state.setString(1, id);
		state.setString(2, token);
		return state.executeUpdate();
	}
	
	public int removeToken(String id) throws SQLException, DatabaseConnectException {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = super.connection.prepareStatement("delete from " + Table.MAP_ANDROID + " where id = ?");
		state.setString(1, id);
		return state.executeUpdate();
	}
	
	public String getID(String token) throws DatabaseConnectException, SQLException {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = super.connection.prepareStatement("select id from " + Table.MAP_ANDROID + " where token = ?");
		state.setString(1, token);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
}