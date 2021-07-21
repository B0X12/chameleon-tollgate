package com.chameleon.tollgate.usb.dao;

import java.sql.SQLException;
import java.util.List;

import com.chameleon.tollgate.usb.dto.USBInfo;

public interface IUSBDAO {
	boolean IsRegisteredUSB(String user, String usb_info) throws SQLException;

	boolean InsertUSBInfo(USBInfo usb_info) throws SQLException;

	List<USBInfo> getRegisteredUSBList(String user) throws SQLException;
}