package com.chameleon.tollgate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import com.chameleon.tollgate.database.exception.DBError;
import com.chameleon.tollgate.database.exception.DatabaseConnectException;
import com.chameleon.tollgate.database.exception.DatabaseResultException;
import com.chameleon.tollgate.util.Util;
import com.chameleon.tollgate.database.define.*;

public class SQLiteManager {
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	private static final String DATABASE_URL = "jdbc:sqlite:..\\tollgate_db.db";
	protected static final int DEFAULT_VALID_TIMEOUT = 30;

	public Connection connection;
	private boolean isOpened;

	public SQLiteManager() {
		isOpened = false;
	}

	public boolean isOpen() {
		return this.isOpened;
	}

	public boolean open() {
		return this.open(false, false);
	}

	public boolean open(boolean isReadOnly) {
		return this.open(isReadOnly, false);
	}

	public boolean open(boolean isReadOnly, boolean autoCommit) {
		if (this.isOpened)
			this.close();

		try {
			SQLiteConfig config = new SQLiteConfig();
			config.setReadOnly(isReadOnly);
			this.connection = DriverManager.getConnection(DATABASE_URL, config.toProperties());
			this.connection.setAutoCommit(autoCommit);
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}

		this.isOpened = true;
		return true;
	}

	public boolean close() {
		if (!this.isOpened)
			return true;

		try {
			this.connection.close();
		} catch (SQLException ex) {
			System.out.println(ex);
			return true;
		}
		return true;
	}

	public boolean isValid() {
		return this.isValid(DEFAULT_VALID_TIMEOUT);
	}

	public boolean isValid(int timeOut) {
		if (!this.isOpened)
			return false;
		try {
			return this.connection.isValid(timeOut);
		} catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}

	public void commit() throws Exception {
		this.connection.commit();
	}
	
	public String getToken(String id) throws Exception{
		if(!this.isOpened)
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		
		int count = getCountOf(Table.MAP_ANDROID, "id", id);
		if(count > 1)
			throw new DatabaseResultException(DBError.MANY_TOKEN);
		else if(count < 1)
			throw new DatabaseResultException(DBError.NO_TOKEN);
		
		PreparedStatement state = connection.prepareStatement("select token from " + Table.MAP_ANDROID + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();

		String result = rs.getString("token");
		rs.close();
		return result;
	}

	public int getCountOf(Table table, String column, String value) throws Exception {
		if(!this.isOpened)
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = connection.prepareStatement("select count(*) from " + table + " where " + column + " = ?");
		state.setString(1, value);
		ResultSet rs = state.executeQuery();

		int result = rs.getInt(1);
		rs.close();
		return result;
	}

	public boolean isInitFactor(String id, Factor factor) throws Exception {
		if(!this.isOpened)
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		PreparedStatement state = connection.prepareStatement("select " + factor + " from " + Table.INIT_FACTOR + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();

		int result = rs.getInt(1);
		rs.close();
		return Util.paresBoolean(result);
	}
	
	public int setInitFactor(String id, Factor factor, boolean value) throws Exception {
		if(!this.isOpened)
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		int count = getCountOf(Table.INIT_FACTOR, "id", id);
		PreparedStatement state;
		if(count == 0)
			state = connection.prepareStatement("insert into " + Table.INIT_FACTOR + "(" + factor + ", id) values(?, ?)");
		else
			state = connection.prepareStatement("update " + Table.INIT_FACTOR + " set " + factor + " = ? where id = ?");
		state.setInt(1, value? 1 : 0);
		state.setString(2,  id);
		return state.executeUpdate();
	}
}