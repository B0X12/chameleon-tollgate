package com.chameleon.tollgate.usb.dao;

public interface IUSBDAO {
	int IsRegisteredUSB(String user, String usb_info) throws Exception;
}