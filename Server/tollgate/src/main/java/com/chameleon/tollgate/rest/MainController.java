package com.chameleon.tollgate.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	
	/*
	 * 서버 동작 여부 확인
	 */
	@GetMapping(path = "/")
	public String sendHello(@RequestHeader(value = "User-Agent") String userAgent) {
		if (userAgent.equals("Tollgate-client")) {
			return "Hello";
		} else {
			/*
			 * TODO: 에러 코드 리턴하도록 수정할 것
			 */
			return "Denied";
		}
	}
	
	
	
}
