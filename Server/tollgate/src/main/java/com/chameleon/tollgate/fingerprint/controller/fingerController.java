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
import com.chameleon.tollgate.fingerprint.service.fingerService;
import com.chameleon.tollgate.define.Path;

/*
 * ��ȯ���ִ� ���� �� �� �پ�ȭ
 * ex. ���� ��� -> 301
 * 	   ���� ���� -> 302 
 */

@RestController
public class fingerController
{
	// ���� ��û�� ID�� Timestamp�� ��ü�� ��Ƽ� Service�� ������
	
	@Autowired
	fingerService service;

	SessionList sessions; //���̵�� timestamp�� ����
	AuthList status; //���̵𺰷� ���� �Ϸ� ���� ����
	
	public fingerController() {
		this.status = new AuthList();
		this.sessions = new SessionList();
	}
	
	
	// ���� ���� ��û
	@GetMapping(Path.AUTH_FINGERPRINT + "{id}")
	public ResponseEntity<Response<Boolean>> SendMessage(@PathVariable("id") String id
			, long timestamp,  @RequestHeader(value = "User-Agent") String userAgent) throws Exception
	{
		if (userAgent.equals("Tollgate-client")) // ���� ����
		{
			this.sessions.add(id, timestamp); // �ٸ� �Լ�����(�ȵ���̵忡�� ������ �Լ�) timestamp�� ����� �� �ֵ��� ���������� ����
			this.status.add(id); // ����ڰ� ���� ������ ���
			
			service.SendMessage(id, timestamp); // service ȣ��
			
			// Boolean(null ���� ����) - boolean
			Boolean result = this.status.waitVerify(id); // ����ڰ� ������ �� ����(sessions.verify()�� ����� �� ����) ��� �� ���� ��� ��ȯ\
			
			this.sessions.remove(id);
			this.status.remove(id);
			
			if(result == null) // Ÿ�Ӿƿ��� �߻��ϸ�
			{
				return new ResponseEntity<>(
						new Response<Boolean>(HttpStatus.PARTIAL_CONTENT, false, timestamp),
						HttpStatus.PARTIAL_CONTENT);
			}
			
			Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, result, timestamp);
			return new ResponseEntity<>(respon, HttpStatus.OK); // client�� ��� ���� - true / false
		}
		else // �츮 Ŭ���̾�Ʈ�� �ƴ� ��
		{
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}
	
	
	// �ȵ���̵� -> ������ ���� ������ ��
	@PostMapping(Path.AUTH_FINGERPRINT + "{id}")
	public ResponseEntity<Response<Boolean>> AuthResult(@PathVariable("id") String id
			, long timestamp, boolean restResult
			, @RequestHeader(value = "User-Agent") String userAgent) throws NoUserException, InvalidRequestException, UnauthorizedUserAgentException
	{
		if(!this.sessions.isExist(new SessionTime(id, timestamp)))
			throw new InvalidRequestException(AuthError.NO_SESSION);
		
		if (userAgent.equals("Tollgate-client")) // ��������
		{
			this.status.verify(id, restResult); // �ش� ������� ���� ����� ����
			
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
		else // �ٸ� ������ �������� ��
		{
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		}
	}	
}