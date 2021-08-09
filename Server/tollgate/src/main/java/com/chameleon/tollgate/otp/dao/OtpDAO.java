package com.chameleon.tollgate.otp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;

@Component
public class OtpDAO extends SQLiteManager implements IOtpDAO 
{
	public OtpDAO() 
	{
		if (!super.isOpen()) {
			super.open(false, true);
		}
	}


	@Override
	public String GetUserSecretKey(final String userId) 
	{
		String Result = null;
		final String SqlInstruct = "SELECT secretkey FROM auth_otp WHERE id = '" + userId + "'";
		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}
	
	@Override
	public Long GetUserRegisterTime(final String userId) 
	{
		Long Result = null;
		final String SqlInstruct = "SELECT registertime FROM auth_otp WHERE id = '" + userId + "'";
		Result = SearchQuery_long(SqlInstruct);

		return Result;
	}


	@Override
	public String GetUserSid(final String userId) 
	{
		String Result = null;
		final String SqlInstruct = "SELECT pc FROM map_pc WHERE id = '" + userId + "'";

		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}

	@Override
	public String GetUserToken(final String userId) 
	{
		String Result = null;
		final String SqlInstruct = "SELECT token FROM map_android WHERE id = '" + userId + "'";
		Result = SearchQuery_string(SqlInstruct);

		return Result;
	}
	
	//------------------------------------------------------------------------------------------------------

	@Override
	public boolean Register(final String userId, final String secertKey, long registerTimeStamp) 
	{
		boolean Result = false;
		final String SqlInstruct = "INSERT INTO auth_otp(id,secretkey,registertime) VALUES ('" + userId + "','" + secertKey + "','" + registerTimeStamp +"')";

		Result = SearchUpdate(SqlInstruct);
		if (Result == true)
			return true;

		return Result;
	}


	//------------------------------------------------------------------------------------------------------

	private boolean SearchUpdate(final String SqlCommand) 
	{
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

	private int SearchQuery_int(final String SqlInstruct) 
	{
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

	private long SearchQuery_long(final String SqlInstruct) 
	{
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

	protected String SearchQuery_string(final String sqlInstruct) 
	{
		String resultValue = null;
		ResultSet result = null;
		if (super.isOpen()) {
			try {
				PreparedStatement state = super.connection.prepareStatement(sqlInstruct);
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