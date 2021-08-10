package com.chameleon.tollgate.usb.controller;

import java.sql.SQLException;
import java.util.List;

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

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;
import com.chameleon.tollgate.usb.dto.USBInfo;
import com.chameleon.tollgate.usb.service.IUSBService;
import com.chameleon.tollgate.util.userHistory.UserHistory;
import com.chameleon.tollgate.util.userHistory.dto.HistoryFactor;
import com.chameleon.tollgate.util.userHistory.dto.HistoryResult;

@RestController
public class USBController {

	@Autowired
	IUSBService service;
	@Autowired
	UserHistory history;

	/*
	 * 유저의 아이디와 USB 정보를 인자로 받아 데이터베이스로부터 해당 정보가 있는지 검색
	 */
	@GetMapping(path = Path.AUTH_USB + "{user}/{usb_info}")
	public ResponseEntity<Response<Boolean>> verifyIfRegisteredUSB(
			@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("user") String user,
			@PathVariable("usb_info") String usb_info, long timestamp, String sid)
			throws UnauthorizedUserAgentException, SQLException {

		history.write(user, HistoryFactor.USB, sid, HistoryResult.SUCCESS);
		
		if (userAgent.equals("Tollgate-client")) {
			boolean result = service.verifyUSB(user, usb_info);
			
			// 등록된 USB일 경우
			if (result == true) {
				System.out.println(usb_info + " registered by " + user); // 디버그용
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
			// 등록되지 않은 USB일 경우
			else {
				System.out.println(usb_info + " not registered to " + user); // 디버그용
				Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}

		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	/*
	 * 등록된 USB 목록 출력
	 */
	@GetMapping(path = Path.AUTH_USB + "{user}")
	public ResponseEntity<Response<List<USBInfo>>> getRegisteredUSBList(
			@RequestHeader(value = "User-Agent") String userAgent, @PathVariable("user") String user, long timestamp)
			throws UnauthorizedUserAgentException, SQLException {

		if (userAgent.equals("Tollgate-client")) {

			
			List<USBInfo> result = service.getRegisteredUSBList(user);
			Response<List<USBInfo>> resp = new Response<List<USBInfo>>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);

		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	/*
	 * 새로운 USB 등록
	 */
	@PostMapping(path = Path.REGIST_USB)
	public ResponseEntity<Response<Boolean>> registerUSBInfo(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody USBInfo usb_info, long timestamp) throws Exception {

		if (userAgent.equals("Tollgate-client")) {
			boolean result = service.registerUSB(usb_info);
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}

	/*
	 * 기존에 등록된 USB 삭제
	 */
	@DeleteMapping(path = Path.REGIST_USB + "{user}/{usb_id}")
	public ResponseEntity<Response<Boolean>> unregisterUSBInfo(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable("user") String user, @PathVariable("usb_id") String usb_id, long timestamp)
			throws Exception {

		if (userAgent.equals("Tollgate-client")) {
			boolean result = service.unregisterUSB(user, usb_id);

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
	
	/*
	 * USB 별명 변경
	 */
	@PutMapping(path = Path.UPDATE_USB_ALIAS)
	public ResponseEntity<Response<Boolean>> updateUSBAlias(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody USBInfo usb_info, long timestamp)
			throws SQLException, UnauthorizedUserAgentException {

		if (userAgent.equals("Tollgate-client")) {
			boolean result = service.updateUSBAlias(usb_info);
			Response<Boolean> resp = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
}
