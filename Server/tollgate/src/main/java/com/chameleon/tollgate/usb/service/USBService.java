package com.chameleon.tollgate.usb.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.database.define.Table;
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
	public boolean registerUSB(USBInfo usb_info) throws Exception {
		boolean result = dao.InsertUSBInfo(usb_info);
		if(result)
			dao.setInitFactor(usb_info.id, Factor.USB, true);
		return result;
	}


	@Override
	public List<USBInfo> getRegisteredUSBList(String user) throws SQLException {
		return dao.getRegisteredUSBList(user);
	}


	@Override
	public boolean unregisterUSB(String user, String usb_id) throws Exception {
		boolean result = dao.unregisterUSB(user, usb_id);
		if(result) {
			if(dao.getCountOf(Table.AUTH_USB, "id", user) == 0)
				dao.setInitFactor(user, Factor.USB, false);
		}
			
		return result;
	}


	@Override
	public boolean updateUSBAlias(USBInfo usb_info) {
		return dao.updateUSBAlias(usb_info);
	}

}