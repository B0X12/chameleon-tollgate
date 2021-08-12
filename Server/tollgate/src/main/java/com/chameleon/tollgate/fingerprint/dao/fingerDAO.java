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

	// DB���� User�� �˻��ؼ� ����� ��ȯ��
	// ���⼭�� id�� token�� �޾ƿ;���

	public String GetToken(String id)
	{
		PreparedStatement pstmt = null;
		
		String sql = "SELECT token FROM map_android WHERE id = ?";
		// String sql = "SELECT token FROM map_android WHERE id = '" + id + "'";
		
		ResultSet RSresult = null; // select���� ��� table�� ��ȯ�ϹǷ� RSresult�� �������
		String result = null;
		
		if(super.isOpen())
		{
			try {
				pstmt = super.connection.prepareStatement(sql); // DB ����			
				pstmt.setString(1, id); // ?�� id�� �־���
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