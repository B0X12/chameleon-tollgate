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
	 * 유저의 아이디와 USB 정보를 인자로 받아
	 * 데이터베이스로부터 해당 정보가 있는지 검색
	 */
	@GetMapping(path = Auth.USB + "{user}/{usb_info}")
	public boolean verifyIfRegisteredUSB(@PathVariable("user") String user, @PathVariable("usb_info") String usb_info) throws Exception {

		System.out.println(user + " : " + usb_info); // 디버그용
		if (service.verifyUSB(user, usb_info)) {
			System.out.println(usb_info + " registered by " + user);	// 디버그용
			return true;
		}
		System.out.println(usb_info + " not registered to " + user);	// 디버그용
		return false;
	}

}
