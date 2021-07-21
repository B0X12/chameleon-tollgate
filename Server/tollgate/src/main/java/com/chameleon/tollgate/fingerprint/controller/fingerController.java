package com.chameleon.tollgate.fingerprint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

//import com.chameleon.tollgate.rest.exception.FingerprintException
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;
import com.chameleon.tollgate.rest.exception.NoUserException;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.define.url.*;
import com.chameleon.tollgate.fingerprint.service.fingerService;

/*
 * 반환해주는 값을 좀 더 다양화
 * ex. 인증 취소 -> 301
 * 	   인증 실패 -> 302 
 */

@RestController
public class fingerController
{
	// 인증 요청한 ID와 Timestamp를 객체에 담아서 Service에 전송함
	
	@Autowired
	fingerService service;

	SessionList sessions; //아이디와 timestamp를 저장
	AuthList status; //아이디별로 인증 완료 여부 저장
	
	public fingerController() {
		this.status = new AuthList();
		this.sessions = new SessionList();
	}
	
	
	// 인증 시작 요청
	@GetMapping(Auth.FINGERPRINT + "{id}")
	public ResponseEntity<Response<Boolean>> SendMessage(@PathVariable("id") String id
			, long timestamp,  @RequestHeader(value = "User-Agent") String userAgent) throws Exception
	{
		if (userAgent.equals("Tollgate-client")) // 접근 제어
		{
			this.sessions.add(id, timestamp); // 다른 함수에서(안드로이드에서 접근할 함수) timestamp를 사용할 수 있도록 전역변수에 저장
			this.status.add(id); // 사용자가 인증 중임을 등록
			
			service.SendMessage(id, timestamp); // service 호출
			
			// Boolean(null 포함 가능) - boolean
			Boolean result = this.status.waitVerify(id); // 사용자가 인증될 때 까지(sessions.verify()가 실행될 때 까지) 대기 후 인증 결과 반환\
			
			this.sessions.remove(id);
			this.status.remove(id);
			
			if(result == null) // 타임아웃이 발생하면
			{
				return new ResponseEntity<>(
						new Response<Boolean>(HttpStatus.PARTIAL_CONTENT, false, timestamp),
						HttpStatus.PARTIAL_CONTENT);
			}
			
			Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(respon, HttpStatus.OK); // client로 결과 전송 - true / false
		}
		else // 우리 클라이언트가 아닐 때
		{
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
	
	
	// 안드로이드 -> 서버로 값을 전송할 때
	@PostMapping(Auth.FINGERPRINT + "{id}")
	public ResponseEntity<Response<Boolean>> AuthResult(@PathVariable("id") String id
			, long timestamp, boolean restResult
			, @RequestHeader(value = "User-Agent") String userAgent) throws NoUserException, InvalidRequestException, UnauthorizedUserAgentException
	{
		if(!this.sessions.isExist(new SessionTime(id, timestamp)))
			throw new InvalidRequestException(AuthError.NO_SESSION);
		
		if (userAgent.equals("Tollgate-client")) // 접근제어
		{
			this.status.verify(id, restResult); // 해당 사용자의 인증 결과를 설정
			
			if (restResult == true)
			{
				Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, true, timestamp);
				return new ResponseEntity<>(respon, HttpStatus.OK);
			}
			else
			{
				Response<Boolean> respon = new Response<Boolean>(HttpStatus.BAD_REQUEST, false, timestamp);
				return new ResponseEntity<>(respon, HttpStatus.BAD_REQUEST);
			}
		}
		else // 다른 곳에서 접속했을 때
		{
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}	
}