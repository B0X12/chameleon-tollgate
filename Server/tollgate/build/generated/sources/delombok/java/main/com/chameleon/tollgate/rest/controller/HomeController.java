package com.chameleon.tollgate.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

@RestController
public class HomeController {

	/*
	 * ���� ���� ���� Ȯ��
	 */
	@GetMapping(path = "/")		// https://192.168.0.1:8080/?timestamp=123123
	public ResponseEntity<Response<String>> sendHello(@RequestHeader(value = "User-Agent") String userAgent,
			long timestamp) throws UnauthorizedUserAgentException {

		if (userAgent.equals("Tollgate-client")) {
			Response<String> resp = new Response<String>(HttpStatus.OK, "Hello", timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
}
