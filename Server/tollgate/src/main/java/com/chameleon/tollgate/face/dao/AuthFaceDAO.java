package com.chameleon.tollgate.face.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.exception.*;
import com.chameleon.tollgate.face.dao.exception.FaceError;
import com.chameleon.tollgate.face.dao.exception.FaceResultException;
import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;

@Component
public class AuthFaceDAO extends SQLiteManager implements IAuthFaceDAO{
	public AuthFaceDAO() {
		super();
	}
	
	@Override
	public boolean setFace(String id, String hashValue) throws Exception {
		if(!super.isOpen())
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		
		PreparedStatement state;
		int count = super.getCountOf(Table.AUTH_FACE, "id", id);
		if(count == 0) {
			state = super.connection.prepareStatement("insert into "+Table.AUTH_FACE+" values (?, ?)");
			state.setString(1, id);
			state.setString(2, hashValue);
		}
		else if(count == 1) {
			state = super.connection.prepareStatement("update " + Table.AUTH_FACE + " set hash = ? where id = ?;");
			state.setString(1, hashValue);
			state.setString(2, id);
		}
		else
			throw new DatabaseResultException(DBError.MANY_TOKEN);
		
		state.executeUpdate();
		
		return true;
	}
	
	public String getFace(String id) throws Exception {
		if(!isOpen()) {
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		}

		int count = getCountOf(Table.AUTH_FACE, "id", id);
		if(count < 1) 
			throw new FaceResultException(FaceError.NO_FACE);
		else if(count > 1)
			throw new FaceResultException(FaceError.MANY_FACE);
		
		PreparedStatement state = super.connection.prepareStatement("select hash from " + Table.AUTH_FACE + " where id = ?");
		state.setString(1, id);
		ResultSet rs = state.executeQuery();
		String result = rs.getString(1);
		rs.close();
		
		return result;
	}
	
}
