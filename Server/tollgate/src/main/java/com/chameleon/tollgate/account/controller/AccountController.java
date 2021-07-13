package com.chameleon.tollgate.account.controller;

import java.sql.SQLException;

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

import com.chameleon.tollgate.account.dto.Account;
import com.chameleon.tollgate.account.dto.MapPC;
import com.chameleon.tollgate.account.service.AccountService;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	// --------------- 로그인 ---------------
	@PostMapping(path = "/account/login")
	public ResponseEntity<Response<Boolean>> logIn(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody Account account, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent 값 검사
		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.logIn(account);

			// 로그인 성공/실패는 모든 응답 OK로 처리, 결과 값(True/False)로 로그인 여부 판단
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- 회원 가입 ---------------
	@PostMapping(path = "/account/signup")
	public ResponseEntity<Response<Boolean>> signUp(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody Account account, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent 값 검사
		if (userAgent.equals("Tollgate-client")) {

			// 회원 가입 시도
			try {
				boolean result = accountService.signUp(account);

				// 회원가입 성공/실패는 모든 응답 OK로 처리, 결과 값(True/False)로 회원 가입 성공 여부 판단
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);

				// 예상하지 못한 예외에 대한 처리
			} catch (SQLException se) {
				throw new SQLException("알 수 없는 데이터베이스 오류가 발생하였습니다");
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- 사용자와 컴퓨터(UID) 연동 ---------------
	@PostMapping(path = "/account/map/pc/")
	public ResponseEntity<Response<Boolean>> mappingSIDWithUser(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody MapPC map_pc, long timestamp) throws UnauthorizedUserAgentException {

		// User-Agent 값 검사
		if (userAgent.equals("Tollgate-client")) {

			// 사용자-UID 매핑
			if (accountService.mappingPC(map_pc)) {
				// 사용자-UID 매핑 성공은 응답 OK로 처리
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, true, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR, false, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- UID와 연동된 사용자 검색 ---------------
	@GetMapping(path = "/account/map/pc/" + "{uid}")
	public ResponseEntity<Response<String>> getUserByUID(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("uid") String uid, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent 값 검사
		if (userAgent.equals("Tollgate-client")) {
			String result = accountService.getUserByUID(uid);

			// 성공/실패는 모든 응답 OK로 처리, 결과 값(검색된 UID 문자열)로 탐색 여부 판단
			Response<String> resp = new Response<String>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- UID와 사용자 간 연동 해제 ---------------
	@DeleteMapping(path = "/account/map/pc/" + "{uid}")
	public ResponseEntity<Response<Boolean>> unmapUserByUID(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("uid") String uid, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent 값 검사
		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.unmapUserByUID(uid);

			if (result == true) {
				// 성공은 응답 OK로 처리
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, true, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				// 실패는 응답 INTERNAL_SERVER_ERROR로 처리
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR, false, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
}
