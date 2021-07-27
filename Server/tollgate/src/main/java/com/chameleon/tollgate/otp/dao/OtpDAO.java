package com.chameleon.tollgate.otp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.otp.dto.*;

@Component
public class OtpDAO extends SQLiteManager implements IOtpDAO {

	public OtpDAO() {
		if (!super.isOpen()) {
			super.open(false, true);
			initializeOTP();
		}
	}

	public void initializeOTP() {
		String Instruct = "DELETE FROM auth_otp";
		try {
			SearchUpdate(Instruct);
			System.out.println("initializeOTP Success");
		} catch (Exception e) {
			System.out.println("#initializeOTP Exception : " + e);
		}
	}

	@Override
	public long GetUserTimestamp(String UserID) {
		long Result = -1;
		String SqlInstruct = "SELECT timestamp FROM auth_otp WHERE id = '" + UserID + "'";
		Result = SearchQuery_long(SqlInstruct);

		return Result;
	}

	@Override
	public String GetUserOtp(String UserID) {
		String Result = null;
		String SqlInstruct = "SELECT otp FROM auth_otp WHERE id = '" + UserID + "'";
		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}

	@Override
	public int GetUserProcedure(String UserID) {
		int Result = -1;
		String SqlInstruct = "SELECT procedure FROM auth_otp WHERE id = '" + UserID + "'";
		Result = SearchQuery_int(SqlInstruct);

		return Result;
	}

	@Override
	public String GetUserSid(String UserID) {
		String Result = null;
		String SqlInstruct = "SELECT pc FROM map_pc WHERE id = '" + UserID + "'";

		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}

	@Override
	public String GetUserToken(String UserID) {
		String Result = null;
		String SqlInstruct = "SELECT token FROM map_android WHERE id = '" + UserID + "'";
		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}

	@Override
	public boolean Register(AuthOtp OCD) {
		boolean Result = false;
		String SqlInstruct = "INSERT INTO auth_otp(id,timestamp,otp) VALUES ('" + OCD.id + "','" + OCD.timestamp + "','" + OCD.otp + "')";

		Result = SearchUpdate(SqlInstruct);
		if (Result == true)
			return true;

		return Result;
	}

	@Override
	public boolean Delete(AuthOtp OCD) {
		boolean Result = false;
		String SqlInstruct = "DELETE FROM auth_otp WHERE id = '" + OCD.id + "'";

		Result = SearchUpdate(SqlInstruct);

		if (Result == true)
			return true;

		return Result;
	}

	// ------------------------------------------------------------------------

	private boolean SearchUpdate(final String SqlCommand) {
		if (super.isOpen()) {
			try {
				PreparedStatement state = super.connection.prepareStatement(SqlCommand);
				int result = state.executeUpdate();
				if (result == 1)
					return true;
			} catch (Exception e) {
				System.out.println("SearchUpdate Exception : " + e);
			}
		}
		return false;
	}

	private int SearchQuery_int(final String SqlInstruct) {
		int resultValue = -1;
		ResultSet result = null;
		if (super.isOpen()) {
			try {
				PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
				result = state.executeQuery();
				resultValue = result.getInt(1);
				result.close();
			} catch (Exception e) {
				System.out.println("SearchQuery_int Exception : " + e);
			}
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

	protected String SearchQuery_string(final String SqlInstruct) {
		String resultValue = null;
		ResultSet result = null;
		if (super.isOpen()) {
			try {
				PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
				result = state.executeQuery();
				resultValue = result.getString(1);
				result.close();
			} catch (Exception e) {
				System.out.println("SearchQuery_string Exception : " + e);
			}

		}
		return resultValue;
	}
}