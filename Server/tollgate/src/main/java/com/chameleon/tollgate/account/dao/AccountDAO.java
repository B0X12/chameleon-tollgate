package com.chameleon.tollgate.account.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.usb.dto.USBInfo;
import com.chameleon.tollgate.account.dto.*;
import com.chameleon.tollgate.account.exception.AccountError;
import com.chameleon.tollgate.account.exception.UserAlreadyExistException;

@Component
public class AccountDAO extends SQLiteManager implements IAccountDAO {

	public AccountDAO() {
		if (!super.isOpen())
			super.open(false, true);
	}

	public void finalize() {
		if (super.isOpen()) {
			super.close();
		}
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
				if(SearchUpdate("insert into auth_factor(id) values('" + account.id + "')"))
					if(SearchUpdate("insert into init_factor(id) values('" + account.id + "')"))
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
	public boolean insertMapPCInfo(MapPC map_pc) {

		String SqlInstruct = "INSERT INTO map_pc(id,pc,alias) VALUES ('" + map_pc.id + "','" + map_pc.pc + "','"
				+ map_pc.alias + "')";
		try {
			return SearchUpdate(SqlInstruct);

		} catch (SQLException se) {
			System.out.println("#mappingPC() Exception : " + se.toString());
			return false;
		}
	}

	@Override
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

	@Override
	public boolean deleteMapPCInfo(String uid) {
		String SqlInstruct = "DELETE FROM map_pc WHERE pc='" + uid + "'";
		try {
			SearchUpdate(SqlInstruct);
			return true;
		} catch (SQLException se) {
			return false;
		}
	}

	@Override
	public int getFactorFlagByUser(String user) throws SQLException {
		int Result = 0;
		String SqlInstruct = "SELECT factor FROM auth_factor WHERE id = '" + user + "'";

		try {
			Result = SearchQuery_int(SqlInstruct);
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			// 검색 결과 없음 - 해당 UID와 연동된 유저가 없음
			if (se.getErrorCode() == 0) {
				Result = 0;
			} else {
				throw se;
			}
		}

		// 검색 결과가 존재하지 않을 경우 0 반환, 그 외의 경우 플래그 값(int) 반환
		return Result;
	}

	@Override
	public List<MapPC> getRegisteredPCList(String user) throws SQLException {
		PreparedStatement pstmt = null;
		String SqlInstruct = "SELECT * FROM map_pc WHERE id='" + user + "'";
		ResultSet rs = null;
		List<MapPC> mapPCList = new ArrayList<>();

		pstmt = super.connection.prepareStatement(SqlInstruct);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("id");
			String pc = rs.getString("pc");
			String alias = rs.getString("alias");

			MapPC data = new MapPC();
			data.id = id;
			data.pc = pc;
			data.alias = alias;

			mapPCList.add(data);
		}

		return mapPCList;
	}

	@Override
	public boolean updatePCAlias(MapPC mapPC) {
		try {
			String SqlInstruct = "UPDATE map_pc SET alias = '" + mapPC.alias + "' WHERE id = '" + mapPC.id
					+ "' AND pc = '" + mapPC.pc + "'";
			SearchUpdate(SqlInstruct);
			return true;
		} catch (SQLException se) {
			return false;
		}
	}

	@Override
	public boolean updateFactorFlag(String user, int factorFlag, int flagToUpdate, boolean enable) {
		int calcFlag = factorFlag;
		
		// 플래그 enable일 경우
		if(enable) {
			calcFlag |= flagToUpdate;
		} else {
			calcFlag &= (~flagToUpdate);
		}
		
		try {
			String SqlInstruct = "UPDATE auth_factor SET factor = " + calcFlag + " WHERE id = '" + user + "'";
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
	
	public boolean updatePwd(String id, String pwd) throws SQLException {
		return SearchUpdate("update account set pwd = '" + pwd + "' where id = '" + id + "'");
	}
}
