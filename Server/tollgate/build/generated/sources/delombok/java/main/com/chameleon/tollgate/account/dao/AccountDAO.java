package com.chameleon.tollgate.account.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.account.dto.*;

@Component
public class AccountDAO extends SQLiteManager implements IAccountDAO  {

	public AccountDAO() {
		if(!super.isOpen())
			super.open(false,true);
	}
	
	@Override
	public boolean checkIDPW(Account account) 
	{
		int Result = 0;

		String SqlInstruct = "SELECT COUNT(*) FROM account WHERE id='" + account.id + "' AND pwd='" + account.pwd + "'";
	
		try {
			Result = SearchQuery_int(SqlInstruct);
			if(Result == 1)
				return true;
		} catch(Exception e) {
			System.out.println("#logIn() Exception : " + e.toString());
		}
			
		return false;
	}

	@Override
	public boolean insertAccountInfo(Account account) 
	{
		boolean Result = false;

		String SqlInstruct = "INSERT INTO account(id,pwd) VALUES ('"+ account.id + "','" + account.pwd + "')";
		try {
			Result = SearchUpdate(SqlInstruct);
			if(Result == true)
				return true;
		} catch(Exception e) {
			System.out.println("#signUp() Exception : " + e.toString());
			return false;
		}
			
		return false;
	}
	
	@Override
	public boolean insertMapPCInfo(MapPC map_pc) 
	{
		boolean Result = false;

		String SqlInstruct = "INSERT INTO map_pc(id,pc,alias) VALUES ('"+ map_pc.id + "','" + map_pc.pc + "','" + map_pc.alias +"')";
		try {
			Result = SearchUpdate(SqlInstruct);
			if(Result == true)
				return true;
			
		} catch(Exception e) {
			System.out.println("#mappingPC() Exception : " + e.toString());
		}
		
		return false;
	}
	
	
	
	// ------------------------------------------------------------------------------------
	
	private boolean SearchUpdate(final String SqlCommand)
	{
		if(super.isOpen())
		{
			try {
			PreparedStatement state = super.connection.prepareStatement(SqlCommand);
			int result = state.executeUpdate();
			if(result == 1)
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
		if(super.isOpen())
		{
			try {
			PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
			result = state.executeQuery();
			resultValue = result.getInt(1);
			result.close();
			} catch(Exception e) {
				System.out.println("SearchQuery_int Exception : " + e);
			}
		}
		return resultValue;
	}
	
	private long SearchQuery_long(final String SqlInstruct)
	{
		long resultValue = -1;
		ResultSet result = null;
		if(super.isOpen())
		{
			try {
			PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
			result = state.executeQuery();
			resultValue = result.getLong(1);
			result.close();
			} catch(Exception e) {
				System.out.println("SearchQuery_long Exception : " + e);
			}
		}
		return resultValue;
	}
	
	private String SearchQuery_string(final String SqlInstruct)
	{
		String resultValue = null;
		ResultSet result = null;
		if(super.isOpen())
		{
			try {
			PreparedStatement state = super.connection.prepareStatement(SqlInstruct);
			result = state.executeQuery();
			resultValue = result.getString(1);
			result.close();
			} catch(Exception e) {
				System.out.println("SearchQuery_string Exception : " + e);
			}
			
		}
		return resultValue;	
	}
}
