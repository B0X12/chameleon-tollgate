package com.chameleon.tollgate.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.account.dto.TokenPack;
import com.chameleon.tollgate.account.service.AdAccountService;
import com.chameleon.tollgate.define.url.Account;
import com.chameleon.tollgate.rest.Response;

@RestController
public class AdAccountController {
	@Autowired
	private AdAccountService service;

	@GetMapping(path=Account.AD_LOGIN+"{id}")
	public ResponseEntity<Response<Boolean>> login(@PathVariable("id") String id, String pwd, long timestamp) throws Exception{
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.login(id, pwd), timestamp),
				HttpStatus.OK);
	}
	
	@PostMapping(path=Account.AD_MAP_AD+"{id}")
	public ResponseEntity<Response<Boolean>> mapAndroid(@PathVariable("id") String id, @RequestBody TokenPack entry) throws Exception {
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.mapAndroid(id, entry.getToken()), entry.getTimestamp()),
				HttpStatus.OK);
	}
	
	@DeleteMapping(path=Account.AD_LOGOUT+"{id}")
	public ResponseEntity<Response<Boolean>> logout(@PathVariable("id") String id, long timestamp) throws Exception {
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.logout(id), timestamp),
				HttpStatus.OK);
	}
	
	@GetMapping(path=Account.AD_ID)
	public ResponseEntity<Response<String>> getID(String token, long timestamp) throws Exception {
		String result = this.service.getID(token);
		if(result == null)
			return new ResponseEntity<>(
					new Response<>(HttpStatus.NO_CONTENT, "", timestamp),
					HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, result, timestamp),
				HttpStatus.OK);
	}
}