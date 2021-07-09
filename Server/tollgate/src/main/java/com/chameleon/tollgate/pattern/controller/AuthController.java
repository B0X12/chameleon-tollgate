package com.chameleon.tollgate.pattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.url.Auth;
import com.chameleon.tollgate.define.url.Register;
import com.chameleon.tollgate.pattern.PatternPack;
import com.chameleon.tollgate.pattern.service.AuthService;
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;

@RestController
public class AuthController {
	@Autowired
	AuthService service;
	
	SessionList sessions;
	AuthList status;
	
	public AuthController() {
		this.status = new AuthList();
		this.sessions = new SessionList();
	}

	@PostMapping(path=Register.PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SetPattern(@PathVariable("id") String id, @RequestBody PatternPack entry) throws Exception {
		Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, service.SetPattern(id, entry.getPattern()), entry.getTimestamp());
		return new ResponseEntity<>(respon, HttpStatus.OK); 
	}
	
	@GetMapping(path=Auth.PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@PathVariable("id") String id, long timestamp) throws Exception {
		this.sessions.add(id, timestamp);
		service.SendSignal(id, timestamp);
		this.status.add(id);
		boolean result = status.waitVerify(id);
		this.status.remove(id);
		this.sessions.remove(id);
		if(result)
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.OK, result, timestamp),
					HttpStatus.OK);
		else
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.BAD_REQUEST, result, timestamp),
					HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path=Auth.PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> VerifyPattern(@PathVariable("id") String id, @RequestBody PatternPack entry) throws Exception {
		if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
		
		boolean result = false;
		try {
			result = service.VerifyPattern(id, entry.getPattern());
		} catch (Exception ex) {
			this.status.verify(id, false);
		}
		
		if(result)
			this.status.verify(id, true);
		else
			this.status.verify(id, false);
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, entry.getTimestamp()),
				HttpStatus.OK);
	}
}
