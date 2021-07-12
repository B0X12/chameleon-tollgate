package com.chameleon.tollgate.usb.dao;

import com.chameleon.tollgate.database.exception.DatabaseConnectException;

public interface IUSBDAO {
	int IsRegisteredUSB(String user, String usb_info) throws Exception;
}