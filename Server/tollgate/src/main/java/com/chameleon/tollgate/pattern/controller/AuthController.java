package com.chameleon.tollgate.pattern.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.CommonCode;
import com.chameleon.tollgate.util.tollgateLog.dto.code.RestCode;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
public class AuthController {
	@Autowired
	AuthService service;
	
	@Autowired
	UserHistory history;
	
	SessionList sessions;
	AuthList status;
	
	public AuthController() {
		this.status = new AuthList();
		this.sessions = new SessionList();
	}

	@PostMapping(path=Path.REGIST_PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SetPattern(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, @RequestBody PatternPack entry, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, service.SetPattern(id, entry.getPattern(), req.getRemoteAddr()), entry.getTimestamp());
		return new ResponseEntity<>(respon, HttpStatus.OK); 
	}
	
	@GetMapping(path=Path.AUTH_PATTERN+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, String pc, long timestamp, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		this.sessions.add(id, timestamp);
		try {
			service.SendSignal(id, timestamp, pc);
		} catch (FirebaseMessagingException ex) {
			TollgateLog.e(req.getRemoteAddr(), id, LogFactor.COMMON, CommonCode.FCM_FAILED, "Failed send fcm message");
			this.status.remove(id);
			this.sessions.remove(id);
			throw ex;
		}
		this.status.add(id);
		Boolean result = status.waitVerify(id);
		this.status.remove(id);
		this.sessions.remove(id);

		history.write(id, HistoryFactor.PATTERN, pc, result ? HistoryResult.SUCCESS : HistoryResult.FAIL);
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
			@PathVariable("id") String id, boolean isLast, @RequestBody PatternPack entry, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp()))) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.COMMON, CommonCode.NO_SESSION, "There are no same session.");
			throw new InvalidRequestException(AuthError.NO_SESSION);
		}
		
		boolean result = false;
		result = service.VerifyPattern(id, entry.getPattern(), req.getRemoteAddr());
		
		if(result || isLast)
			this.status.verify(id, result);
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, entry.getTimestamp()),
				HttpStatus.OK);
	}
}
