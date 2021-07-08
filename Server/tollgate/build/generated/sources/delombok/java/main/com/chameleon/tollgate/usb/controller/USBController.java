package com.chameleon.tollgate.usb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.url.Auth;
import com.chameleon.tollgate.usb.service.IUSBService;

@RestController
public class USBController {

	@Autowired
	IUSBService service;

	/*
	 * ������ ���̵�� USB ������ ���ڷ� �޾�
	 * �����ͺ��̽��κ��� �ش� ������ �ִ��� �˻�
	 */
	@GetMapping(path = Auth.USB + "{user}/{usb_info}")
	public boolean verifyIfRegisteredUSB(@PathVariable("user") String user, @PathVariable("usb_info") String usb_info) throws Exception {

		System.out.println(user + " : " + usb_info); // ����׿�
		if (service.verifyUSB(user, usb_info)) {
			System.out.println(usb_info + " registered by " + user);	// ����׿�
			return true;
		}
		System.out.println(usb_info + " not registered to " + user);	// ����׿�
		return false;
	}

}
