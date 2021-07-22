package com.chameleon.tollgate.face.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.url.Auth;
import com.chameleon.tollgate.define.url.Register;
import com.chameleon.tollgate.face.dto.FacePack;
import com.chameleon.tollgate.face.service.AuthFaceService;
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.tollgateLog.tollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.faceCode;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;
import com.chameleon.tollgate.util.userHistory.dto.historyRecord;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
	@PostMapping(path=Register.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> registerFace(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, @RequestBody FacePack entry) throws Exception {
		
		if (userAgent.equals("Tollgate-client")) {
			Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, service.SetFace(id, entry.getHashValue()), entry.getTimestamp());
			return new ResponseEntity<>(respon, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}

		
	}
	
	// 인증 시작 요청
	@GetMapping(path=Auth.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, long timestamp) throws Exception {
		if (userAgent.equals("Tollgate-client")) {
			
			tollgateLog.e(req.getRemoteAddr(), LogFactor.FACE, faceCode.NO_FACE, "test Log");
			history.leave("tester", HistoryFactor.FACE, "test", HistoryResult.SUCCESS);
			
			ArrayList<historyRecord> tmp = history.get(id);

			this.sessions.add(id, timestamp);
			service.SendSignal(id, timestamp);
			this.status.add(id);
			boolean result = status.waitVerify(id);
			
//			if(result == Null)
//				return new ResponseEntity<>(new Response<Boolean>(HttpStatus.OK, result, timestamp), HttpStatus.OK);
			
			this.status.remove(id);
			this.sessions.remove(id);		

			if(result) {
				System.out.println("Verified");
				return new ResponseEntity<>(
						new Response<Boolean>(HttpStatus.OK, result, timestamp),
						HttpStatus.OK);
			}
			else {
				System.out.println("Failed");
				return new ResponseEntity<>(
						new Response<Boolean>(HttpStatus.OK, result, timestamp),
						HttpStatus.OK);
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
	
			
	@PostMapping(path=Auth.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> VerifyFace(@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("id") String id, HttpServletRequest req, @RequestBody FacePack entry) throws Exception{
		if (userAgent.equals("Tollgate-client")) {
			if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp())))
				throw new InvalidRequestException(AuthError.NO_SESSION);
			
			if(service.VerifyFace(id, entry)) {
				this.status.verify(id, true);
				return new ResponseEntity<>(
						new Response<Boolean>(HttpStatus.OK, true, entry.getTimestamp()),
						HttpStatus.OK);
			}
			
			this.status.verify(id, false);
			
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.BAD_REQUEST, false, entry.getTimestamp()),
					HttpStatus.BAD_REQUEST);
			
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
	

}
