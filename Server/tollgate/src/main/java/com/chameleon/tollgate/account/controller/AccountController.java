package com.chameleon.tollgate.account.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.account.dto.Account;
import com.chameleon.tollgate.account.dto.MapPC;
import com.chameleon.tollgate.account.service.AccountService;
import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.LoginCode;
import com.chameleon.tollgate.util.tollgateLog.dto.code.RestCode;

@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;

	// --------------- �α��� ---------------
	@PostMapping(path = Path.LOGIN)
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
	@PostMapping(path = Path.SIGNUP)
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
	@PostMapping(path = Path.MAP_PC)
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
	@GetMapping(path = Path.MAP_PC + "{uid}")
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
	@DeleteMapping(path = Path.MAP_PC + "{uid}")
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

	// --------------- ����� ���̵� �̿��Ͽ� Factor Flag �ҷ��� ---------------
	@GetMapping(path = Path.FACTOR_FLAG + "{user}")
	public ResponseEntity<Response<Integer>> getFactorFlagByUser(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("user") String user, long timestamp) throws UnauthorizedUserAgentException, SQLException {
		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {
			int result = accountService.getFactorFlagByUser(user);

			// ����/���д� ��� ���� OK�� ó��, ��� ��(�˻��� UID ���ڿ�)�� Ž�� ���� �Ǵ�
			Response<Integer> resp = new Response<Integer>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- ����� ���̵��� Factor Flag �� ������Ʈ ---------------
	@PutMapping(path = Path.FACTOR_FLAG + "{user}/{flag}/{enable}")
	public ResponseEntity<Response<Boolean>> updateFactorFlag(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("user") String user, @PathVariable("flag") int flag, @PathVariable("enable") boolean enable,
			long timestamp) throws UnauthorizedUserAgentException, SQLException {
		// User-Agent �� �˻�
		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.updateFactorFlag(user, flag, enable);

			// ����/���д� ��� ���� OK�� ó��, ��� ��(�˻��� UID ���ڿ�)�� Ž�� ���� �Ǵ�
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- ����ڿ� ������ PC�� ����� ������ ---------------
	@GetMapping(path = Path.MAP_PCLIST + "{user}")
	public ResponseEntity<Response<List<MapPC>>> getRegisteredPCList(
			@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("user") String user, long timestamp)
			throws UnauthorizedUserAgentException, SQLException {

		if (userAgent.equals("Tollgate-client")) {
			List<MapPC> result = accountService.getRegisteredPCList(user);
			Response<List<MapPC>> resp = new Response<List<MapPC>>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);

		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	// --------------- PC�� ���� ���� ---------------
	@PutMapping(path = Path.UPDATE_PC_ALIAS)
	public ResponseEntity<Response<Boolean>> updatePCAlias(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody MapPC mapPC, long timestamp) throws SQLException, UnauthorizedUserAgentException {

		if (userAgent.equals("Tollgate-client")) {
			boolean result = accountService.updatePCAlias(mapPC);
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
	
	@PutMapping(path = Path.ACCOUNT_PWD + "{id}")
	public ResponseEntity<Response<Boolean>> updatePwd(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("id") String id, String pwd, long timestamp, HttpServletRequest req) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT)) {
			TollgateLog.w(req.getRemoteAddr(), id, LogFactor.REST, RestCode.INVALID_REQUEST, "Invalid request with user-agent(" + userAgent + ").");
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
		
		boolean result = accountService.updatePwd(id, pwd);
		if(result)
			TollgateLog.i(req.getRemoteAddr(), id, LogFactor.LOGIN, LoginCode.SET_PWD, "Change user's password.");
		var response = new Response<Boolean>(HttpStatus.OK, result, timestamp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
