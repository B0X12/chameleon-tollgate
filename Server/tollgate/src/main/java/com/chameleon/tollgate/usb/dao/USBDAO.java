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
	 * 아래의 실행 쿼리문의 결과 개수(카운트) 리턴함
	 * 리턴 값: 0일 경우 데이터가 존재하지 않음 - 인증 실패, 
	 * 양의 정수일 경우 데이터가 존재 - 인증 성공
	 */
	@Override
	public int IsRegisteredUSB(String user, String usb_info) throws Exception {
		if (!super.isOpen()) {
			throw new DatabaseConnectException(DBError.NO_CONNECTION);
		}
		
		/*
		 * 실행 쿼리문 
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