package com.chameleon.tollgate.face.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.database.exception.DatabaseConnectException;
import com.chameleon.tollgate.database.exception.DatabaseResultException;

@Component
public class AuthFaceDAO extends SQLiteManager{
	public AuthFaceDAO() {
		super();
	}
	
	public boolean setFace(String id, String hash) throws Exception {
		if(!super.isOpen())
			throw new DatabaseConnectException("Not Connected with Databse");
		
		PreparedStatement state;
		int count = super.getCountOf(Table.AUTH_FACE, "id", id);
		if(count == 0) {
			state = super.connection.prepareStatement("insert into "+Table.AUTH_FACE+" values (?, ?)");
			state.setString(1, id);
			state.setString(2, hash);
		}
		else if(count == 1) {
			state = super.connection.prepareStatement("update " + Table.AUTH_FACE + " set hash = ? where id = ?;");
			state.setString(1, hash);
			state.setString(2, id);
		}
		else
			throw new DatabaseResultException("Too many face hash");
		
		state.executeUpdate();
		
		return true;
	}
	
	public String getFace(String id) throws Exception {
		if(!isOpen()) {
			throw new DatabaseConnectException("Not Connected with Database");
		}

		int count = getCountOf(Table.AUTH_FACE, "id", id);
		if(count < 1) 
			throw new DatabaseResultException("There is no matched face");
		else if(count > 1)
			throw new DatabaseResultException("Found Faces");
		
		PreparedStatement state = super.connection.prepareStatement("select hash from " + Table.AUTH_FACE + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
	
}
