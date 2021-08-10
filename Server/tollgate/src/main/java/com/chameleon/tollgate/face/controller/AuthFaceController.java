package com.chameleon.tollgate.face.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.face.dto.FacePack;

import org.springframework.web.bind.annotation.RequestHeader;

import com.chameleon.tollgate.face.service.AuthFaceService;
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
import com.chameleon.tollgate.util.tollgateLog.dto.code.FaceCode;
import com.chameleon.tollgate.util.userHistory.UserHistory;

@RestController
public class AuthFaceController {	
	@Autowired
	AuthFaceService service;
	@Autowired
	UserHistory history;

	SessionList sessions;
	AuthList status;
	
	AuthFaceController(){
		this.status = new AuthList();
		this.sessions = new SessionList();
	}
		
	// 얼굴 해시 등록
	@PostMapping(path=Path.REGIST_FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> registerFace(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, @RequestBody FacePack entry) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.NO_PRIVILEGE, "User-Agent value of request packet mismatched");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, 
				service.SetFace(id, entry.getHashValue()), 
				entry.getTimestamp());
		
		return new ResponseEntity<>(respon, HttpStatus.OK);
	}
	
	// 인증 시작 요청
	@GetMapping(path=Path.AUTH_FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, long timestamp) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.NO_PRIVILEGE, "User-Agent value of request packet mismatched");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		this.sessions.add(id, timestamp);
		
		
		TollgateLog.i(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.SIGNAL_SENT, "Sending FCM notification message to mobile device");
		service.SendSignal(id, timestamp);
		this.status.add(id);

		TollgateLog.i(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.START_WAIT, "Waiting for mobile device to response");
		Boolean result = status.waitVerify(id);
		TollgateLog.i(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.STOP_WAIT, "Waiting for response has finished");
		this.status.remove(id);
		this.sessions.remove(id);		

		if(result == null) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.TIMEOUT, "Mobile device not responded within time limit");
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.REQUEST_TIMEOUT, false, timestamp),
					HttpStatus.REQUEST_TIMEOUT);
		}
			
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, timestamp),
				HttpStatus.OK);

	}
	
		

	// 얼굴 인증 수행	
	@PostMapping(path=Path.AUTH_FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> VerifyFace(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, @RequestBody FacePack entry) throws Exception{
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.NO_PRIVILEGE, "User-Agent value of request packet mismatched");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
			
		if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
			
		TollgateLog.i(req.getRemoteAddr(), id, LogFactor.FACE, FaceCode.TIMEOUT, "Mobile device not responded within time limit");
		boolean result = service.VerifyFace(id, entry);
		
		this.status.verify(id, result);
		
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.OK, result, entry.getTimestamp()),
				HttpStatus.OK);
			
	}
}
