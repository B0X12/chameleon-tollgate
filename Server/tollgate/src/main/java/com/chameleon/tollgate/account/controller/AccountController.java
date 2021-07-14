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
	
	// --------------- �α��� ---------------
	@PostMapping(path = "/account/login")
	public ResponseEntity<Response<Boolean>> logIn(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody Account account, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.logIn(account);

			// �α��� ����/���д� ��� ���� OK�� ó��, ��� ��(True/False)�� �α��� ���� �Ǵ�
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- ȸ�� ���� ---------------
	@PostMapping(path = "/account/signup")
	public ResponseEntity<Response<Boolean>> signUp(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody Account account, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {

			// ȸ�� ���� �õ�
			try {
				boolean result = accountService.signUp(account);

				// ȸ������ ����/���д� ��� ���� OK�� ó��, ��� ��(True/False)�� ȸ�� ���� ���� ���� �Ǵ�
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);

				// �������� ���� ���ܿ� ���� ó��
			} catch (SQLException se) {
				throw new SQLException("�� �� ���� �����ͺ��̽� ������ �߻��Ͽ����ϴ�");
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- ����ڿ� ��ǻ��(UID) ���� ---------------
	@PostMapping(path = "/account/map/pc/")
	public ResponseEntity<Response<Boolean>> mappingSIDWithUser(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody MapPC map_pc, long timestamp) throws UnauthorizedUserAgentException {

		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {

			// �����-UID ����
			if (accountService.mappingPC(map_pc)) {
				// �����-UID ���� ������ ���� OK�� ó��
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

	// --------------- UID�� ������ ����� �˻� ---------------
	@GetMapping(path = "/account/map/pc/" + "{uid}")
	public ResponseEntity<Response<String>> getUserByUID(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("uid") String uid, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {
			String result = accountService.getUserByUID(uid);

			// ����/���д� ��� ���� OK�� ó��, ��� ��(�˻��� UID ���ڿ�)�� Ž�� ���� �Ǵ�
			Response<String> resp = new Response<String>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- UID�� ����� �� ���� ���� ---------------
	@DeleteMapping(path = "/account/map/pc/" + "{uid}")
	public ResponseEntity<Response<Boolean>> unmapUserByUID(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("uid") String uid, long timestamp) throws UnauthorizedUserAgentException, SQLException {

		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.unmapUserByUID(uid);

			if (result == true) {
				// ������ ���� OK�� ó��
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, true, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				// ���д� ���� INTERNAL_SERVER_ERROR�� ó��
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR, false, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
}
