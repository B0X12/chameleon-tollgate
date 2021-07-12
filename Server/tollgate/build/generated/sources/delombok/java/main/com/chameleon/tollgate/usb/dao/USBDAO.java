package com.chameleon.tollgate.usb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.database.exception.*;
import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.database.define.Table;

@Component
public class USBDAO extends SQLiteManager implements IUSBDAO {
	public USBDAO() {
		super();
	}

	/*
	 * �Ʒ��� ���� �������� ��� ����(ī��Ʈ) ������
	 * ���� ��: 0�� ��� �����Ͱ� �������� ���� - ���� ����, 
	 * ���� ������ ��� �����Ͱ� ���� - ���� ����
	 */
	@Override
	public int IsRegisteredUSB(String user, String usb_info) throws Exception {
		if (!super.isOpen()) {
			throw new DatabaseConnectException("There is not connected with the database.");
		}
		
		/*
		 * ���� ������ 
		 * SELECT COUNT(*) FROM auth_usb WHERE id="user02" AND
		 * usb_info="USBVID_0781&PID_55674C530001151013119203";
		 */
		PreparedStatement state;
		state = super.connection.prepareStatement("SELECT COUNT(*) FROM auth_usb WHERE id=? AND usb_id=?");
		state.setString(1, user);
		state.setString(2, usb_info);

		ResultSet results = state.executeQuery();
		int count = results.getInt(1);
		results.close();

		return count;
	}
}