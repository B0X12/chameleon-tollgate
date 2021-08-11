package com.chameleon.tollgate.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.account.dto.TokenPack;
import com.chameleon.tollgate.account.service.AdAccountService;
import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.RestCode;

@RestController
public class AdAccountController {
	@Autowired
	private AdAccountService service;

	@GetMapping(path=Path.AD_LOGIN+"{id}")
	public ResponseEntity<Response<Boolean>> login(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, String pwd, long timestamp, HttpServletRequest req) throws Exception{
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.login(id, pwd, req.getRemoteAddr()), timestamp),
				HttpStatus.OK);
	}
	
	@PostMapping(path=Path.AD_MAP_AD+"{id}")
	public ResponseEntity<Response<Boolean>> mapAndroid(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, @RequestBody TokenPack entry, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		boolean result = this.service.mapAndroid(id, entry.getToken(), req.getRemoteAddr());
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, result, entry.getTimestamp()),
				HttpStatus.OK);
	}
	
	@DeleteMapping(path=Path.AD_LOGOUT+"{id}")
	public ResponseEntity<Response<Boolean>> logout(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, long timestamp, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.logout(id, req.getRemoteAddr()), timestamp),
				HttpStatus.OK);
	}
	
	@GetMapping(path=Path.AD_ID)
	public ResponseEntity<Response<String>> getID(@RequestHeader(value = "User-Agent") String userAgent,
			String token, long timestamp, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), token, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
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