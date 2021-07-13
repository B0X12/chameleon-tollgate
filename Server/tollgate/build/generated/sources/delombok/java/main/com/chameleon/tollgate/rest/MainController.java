package com.chameleon.tollgate.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	
	/*
	 * ���� ���� ���� Ȯ��
	 */
	@GetMapping(path = "/")
	public String sendHello(@RequestHeader(value = "User-Agent") String userAgent) {
		if (userAgent.equals("Tollgate-client")) {
			return "Hello";
		} else {
			/*
			 * TODO: ���� �ڵ� �����ϵ��� ������ ��
			 */
			return "Denied";
		}
	}
	
	
	
}
