package com.chameleon.tollgate.usb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.chameleon.tollgate.database.SQLiteManager;
import com.chameleon.tollgate.usb.dto.USBInfo;


@Component
public class USBDAO extends SQLiteManager implements IUSBDAO {
	public USBDAO() {
		if (!super.isOpen())
			super.open(false, true);
	}

	public void finalize() {
		if (super.isOpen()) {
			super.close();
		}
	}

	/*
	 * 아래의 실행 쿼리문의 결과 개수(카운트) 리턴함 리턴 값: 0일 경우 데이터가 존재하지 않음 - 인증 실패, 양의 정수일 경우 데이터가
	 * 존재 - 인증 성공
	 */
	@Override
	public boolean IsRegisteredUSB(String user, String usb_info) throws SQLException {
		/*
		 * 실행 쿼리문 SELECT COUNT(*) FROM auth_usb WHERE id="user02" AND
		 * usb_info="USBVID_0781PID_55674C530001151013119203";
		 */
		int Result = 0;

		String SqlInstruct = "SELECT COUNT(*) FROM auth_usb WHERE id='" + user + "' AND usb_id='" + usb_info + "'";
		Result = SearchQuery_int(SqlInstruct);
		if (Result == 1)
			return true;

		return false;
	}

	@Override
	public boolean InsertUSBInfo(USBInfo usb_info) throws SQLException {
		boolean Result = false;

		String SqlInstruct = "INSERT INTO auth_usb(id,usb_id,alias) VALUES ('" + usb_info.id + "','" + usb_info.usb_id
				+ "','" + usb_info.alias + "')";
		try {
			Result = SearchUpdate(SqlInstruct);
			if (Result == true)
				return true;
		} catch (SQLException se) {
			// 이미 등록된 USB 정보가 있는 경우
			if (se.getErrorCode() == 19) {
				return false;
			} else {
				throw se;
			}
		}
		return false;
	}

	@Override
	public List<USBInfo> getRegisteredUSBList(String user) throws SQLException {
		PreparedStatement pstmt = null;
		String SqlInstruct = "SELECT * FROM auth_usb WHERE id='" + user + "'";
		ResultSet rs = null;
		List<USBInfo> usbList = new ArrayList<>();

		pstmt = super.connection.prepareStatement(SqlInstruct);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("id");
			String usb_id = rs.getString("usb_id");
			String alias = rs.getString("alias");

			USBInfo data = new USBInfo();
			data.id = id;
			data.usb_id = usb_id;
			data.alias = alias;

			usbList.add(data);
		}

		return usbList;
	}

	public boolean unregisterUSB(String user, String usb_id) {
		String SqlInstruct = "DELETE FROM auth_usb WHERE id='" + user + "' AND usb_id='" + usb_id + "'";
		try {
			SearchUpdate(SqlInstruct);
			return true;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return false;
		}
	}

	// ---------------------------------------------------------

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