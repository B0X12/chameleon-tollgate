package com.chameleon.tollgate.account.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.account.dto.*;
import com.chameleon.tollgate.account.exception.AccountError;
import com.chameleon.tollgate.account.exception.UserAlreadyExistException;

@Component
public class AccountDAO extends SQLiteManager implements IAccountDAO {

	public AccountDAO() {
		if (!super.isOpen())
			super.open(false, true);
	}

	@Override
	public boolean checkIDPW(Account account) throws SQLException {
		int Result = 0;

		String SqlInstruct = "SELECT COUNT(*) FROM account WHERE id='" + account.id + "' AND pwd='" + account.pwd + "'";
		Result = SearchQuery_int(SqlInstruct);
		if (Result == 1)
			return true;

		return false;
	}

	@Override
	public boolean insertAccountInfo(Account account) throws UserAlreadyExistException, SQLException {
		boolean Result = false;

		String SqlInstruct = "INSERT INTO account(id,pwd) VALUES ('" + account.id + "','" + account.pwd + "')";
		try {
			Result = SearchUpdate(SqlInstruct);
			if (Result == true)
				return true;
		} catch (SQLException se) {
			// 이미 등록된 회원이 있는 경우
			if (se.getErrorCode() == 19) {
				throw new UserAlreadyExistException(AccountError.USER_ALREADY_EXIST);
			} else {
				throw se;
			}
		}

		return false;
	}

	@Override
	public boolean insertMapPCInfo(MapPC map_pc){
		
		String SqlInstruct = "INSERT INTO map_pc(id,pc,alias) VALUES ('" + map_pc.id + "','" + map_pc.pc + "','"
				+ map_pc.alias + "')";
		try {
			return SearchUpdate(SqlInstruct);

		} catch (SQLException se) {
			System.out.println("#mappingPC() Exception : " + se.toString());
			return false;
		}
	}

	public String getUserByUID(String uid) throws SQLException {
		String Result = "";
		String SqlInstruct = "SELECT id FROM map_pc WHERE pc='" + uid + "'";

		try {
			Result = SearchQuery_string(SqlInstruct);
		} catch (SQLException se) {
			// 검색 결과 없음 - 해당 UID와 연동된 유저가 없음
			if (se.getErrorCode() == 0) {
				Result = "";
			} else {
				throw se;
			}
		}

		return Result;
	}
	
	public boolean deleteMapPCInfo(String uid) {
		String SqlInstruct = "DELETE FROM map_pc WHERE pc='" + uid + "'"; 
		try {
			SearchUpdate(SqlInstruct);
			return true;
		} catch (SQLException se) {
			return false;
		}
	}
	

	// ------------------------------------------------------------------------------------

	private boolean SearchUpdate(final String SqlCommand) throws SQLException {
		if (super.isOpen()) {
			PreparedStatement state = super.connection.prepareStatement(SqlCommand);
			int result = state.executeUpdate();
			if (result == 1)
				return true;
		}
		return false;
	}

	private int SearchQuery_int(final String SqlInstruct) throws SQLException {
		int resultValue = -1;
		ResultSet result = null;
		if (super.isOpen()) {
			PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
			result = state.executeQuery();
			resultValue = result.getInt(1);
			result.close();
		}
		return resultValue;
	}

	private long SearchQuery_long(final String SqlInstruct) {
		long resultValue = -1;
		ResultSet result = null;
		if (super.isOpen()) {
			try {
				PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
				result = state.executeQuery();
				resultValue = result.getLong(1);
				result.close();
			} catch (Exception e) {
				System.out.println("SearchQuery_long Exception : " + e);
			}
		}
		return resultValue;
	}

	private String SearchQuery_string(final String SqlInstruct) throws SQLException {
		String resultValue = null;
		ResultSet result = null;
		if (super.isOpen()) {
			PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
			result = state.executeQuery();
			resultValue = result.getString(1);
			result.close();
		}
		return resultValue;
	}

	

	
}
