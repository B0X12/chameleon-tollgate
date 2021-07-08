package com.chameleon.tollgate.usb.service;

public interface IUSBService {
	boolean verifyUSB(String user, String usb_info) throws Exception;
}
