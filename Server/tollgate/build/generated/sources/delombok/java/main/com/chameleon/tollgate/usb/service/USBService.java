package com.chameleon.tollgate.usb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.pattern.dao.AuthDAO;
import com.chameleon.tollgate.usb.dao.IUSBDAO;
import com.chameleon.tollgate.usb.dao.USBDAO;

@Service
public class USBService implements IUSBService {
	@Autowired
	USBDAO dao;
	
	@Override
	public boolean verifyUSB(String user, String usb_info) throws Exception {
		dao.open(true);
		
		if(dao.IsRegisteredUSB(user, usb_info) != 0) {
			dao.close();
			return true;
		}
		
		dao.close();
		return false;
	}
}