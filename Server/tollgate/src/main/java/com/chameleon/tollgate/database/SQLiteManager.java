package com.chameleon.tollgate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class SQLiteManager {
	static {
		try {
		Class.forName("org.sqlite.JDBC");
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	private static final String DATABASE_URL = "jdbc:sqlite:..\\tollgate_db.db";
	private static final int DEFAULT_VALID_TIMEOUT = 30;
	
	private Connection connection;
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
		if(this.isOpened)
			this.close();
		
		try {
			SQLiteConfig config = new SQLiteConfig();
			config.setReadOnly(isReadOnly);
			this.connection = DriverManager.getConnection(DATABASE_URL, config.toProperties());
			this.connection.setAutoCommit(autoCommit);
		}
		catch(SQLException ex) {
			System.out.println(ex);
			return false;
		}
		
		this.isOpened = true;
		return true;
	}
	
	public boolean close() {
		if(!this.isOpened)
			return true;
		
		try {
			this.connection.close();
		}
		catch(SQLException ex) {
			System.out.println(ex);
			return true;
		}
		return true;
	}
	
	public boolean isValid() {
		return this.isValid(DEFAULT_VALID_TIMEOUT);
	}
	
	public boolean isValid(int timeOut) {
		if(!this.isOpened)
			return false;
		try {
		return this.connection.isValid(timeOut);
		}
		catch (SQLException ex) {
			System.out.println(ex);
			return false;
		}
	}
}
