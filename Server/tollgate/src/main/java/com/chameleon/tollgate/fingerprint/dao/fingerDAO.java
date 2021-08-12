package com.chameleon.tollgate.fingerprint.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.SQLiteManager;

@Component
public class fingerDAO extends SQLiteManager implements IfingerDAO
{
	fingerDAO()
	{
		if(!super.isOpen())
			super.open(true, false); // open(read-only, save)
	}

	// DB에서 User를 검색해서 결과를 반환함
	// 여기서는 id로 token을 받아와야함

	public String GetToken(String id)
	{
		PreparedStatement pstmt = null;
		
		String sql = "SELECT token FROM map_android WHERE id = ?";
		// String sql = "SELECT token FROM map_android WHERE id = '" + id + "'";
		
		ResultSet RSresult = null; // select문의 경우 table을 반환하므로 RSresult를 써줘야함
		String result = null;
		
		if(super.isOpen())
		{
			try {
				pstmt = super.connection.prepareStatement(sql); // DB 연결			
				pstmt.setString(1, id); // ?에 id를 넣어줌
				RSresult = pstmt.executeQuery();
				result = RSresult.getString(1);
				RSresult.close();
				pstmt.close();
				
			} catch(Exception e) {
				System.out.println("#GetToken Exception : " + e);
			}
		}
		return result;
	}
}