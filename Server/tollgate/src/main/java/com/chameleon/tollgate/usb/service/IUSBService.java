package com.chameleon.tollgate.usb.service;

import java.sql.SQLException;
import java.util.List;

import com.chameleon.tollgate.usb.dto.USBInfo;

public interface IUSBService {
	boolean verifyUSB(String user, String usb_info) throws SQLException;

	boolean registerUSB(USBInfo usb_info) throws Exception;

	List<USBInfo> getRegisteredUSBList(String user) throws SQLException;

	boolean unregisterUSB(String user, String usb_id) throws Exception;

	boolean updateUSBAlias(USBInfo usb_info);
}
