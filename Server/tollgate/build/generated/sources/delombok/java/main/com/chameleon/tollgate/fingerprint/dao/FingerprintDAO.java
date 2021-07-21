package com.chameleon.tollgate.fingerprint.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.database.SQLiteManager;

public class FingerprintDAO extends SQLiteManager implements IFingerprintDAO
{
	void fingerprintDAO()
	{
		if(super.isOpen())
			super.open(true, false); // open(read-only, save)
	}	

	// DB���� User�� �˻��ؼ� ����� ��ȯ��
	// ���⼭�� id�� token�� �޾ƿ;���

	public String GetToken(String id)
	{
		String query = "SELECT token FROM map_android WHERE id = '" + id + "'";
		String result = null;
		
		if(super.isOpen())
		{
			// ������ �˻�
			try {
				PreparedStatement state = super.connection.prepareStatement(query);
				ResultSet RSresult = state.executeQuery();
				result = RSresult.getString(1); // ��(|) ��ȣ, �޾ƿ� ���� 2��
				RSresult.close();
			} catch(Exception e) {
				System.out.println("#GetToken Exception : " + e);
			}
		}
		return result;
	}
}