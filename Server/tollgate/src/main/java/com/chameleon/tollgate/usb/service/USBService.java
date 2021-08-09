package com.chameleon.tollgate.usb.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.usb.dao.USBDAO;
import com.chameleon.tollgate.usb.dto.USBInfo;


@Service
public class USBService implements IUSBService {
	@Autowired
	USBDAO dao;
	
	@Override
	public boolean verifyUSB(String user, String usb_info) throws SQLException {
		return dao.IsRegisteredUSB(user, usb_info);
	}
	
	
	@Override
	public boolean registerUSB(USBInfo usb_info) throws SQLException {
		return dao.InsertUSBInfo(usb_info);
	}


	@Override
	public List<USBInfo> getRegisteredUSBList(String user) throws SQLException {
		return dao.getRegisteredUSBList(user);
	}


	@Override
	public boolean unregisterUSB(String user, String usb_id) {
		return dao.unregisterUSB(user, usb_id);
	}


	@Override
	public boolean updateUSBAlias(USBInfo usb_info) {
		return dao.updateUSBAlias(usb_info);
	}

}