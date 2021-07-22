package com.chameleon.tollgate.pattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.pattern.PatternPack;
import com.chameleon.tollgate.pattern.service.AuthService;
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

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

	@PostMapping(path=Path.REGIST_PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SetPattern(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, @RequestBody PatternPack entry) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
			
		Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, service.SetPattern(id, entry.getPattern()), entry.getTimestamp());
		return new ResponseEntity<>(respon, HttpStatus.OK); 
	}
	
	@GetMapping(path=Path.AUTH_PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, long timestamp) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		this.sessions.add(id, timestamp);
		service.SendSignal(id, timestamp);
		this.status.add(id);
		Boolean result = status.waitVerify(id);
		this.status.remove(id);
		this.sessions.remove(id);
		
		if(result == null) {
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.PARTIAL_CONTENT, false, timestamp),
					HttpStatus.PARTIAL_CONTENT);
		}
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, timestamp),
				HttpStatus.OK);
	}
	
	@PostMapping(path=Path.AUTH_PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> VerifyPattern(@RequestHeader(value = "User-Agent") String userAgent, 
			@PathVariable("id") String id, boolean isLast, @RequestBody PatternPack entry) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
		
		boolean result = false;
		result = service.VerifyPattern(id, entry.getPattern());
		
		if(result || isLast)
			this.status.verify(id, result);
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, entry.getTimestamp()),
				HttpStatus.OK);
	}
}
