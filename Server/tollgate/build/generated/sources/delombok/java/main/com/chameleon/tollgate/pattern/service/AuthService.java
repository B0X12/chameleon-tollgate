package com.chameleon.tollgate.pattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.pattern.dao.AuthDAO;

@Service
public class AuthService implements IAuthService {
	@Autowired
	AuthDAO dao;
	
	@Override
	public boolean SendSignal(String id, int timestamp) throws Exception {
		dao.open(true);
		String token = dao.getToken(id);
		dao.close();
		final String title = "���� ����";
		final String body = "���� ���� ��û�� �߻��߽��ϴ�.";
		final String click_action = "android.intent.action.AUTH_PATTERN";
		
		FCMSender fcm = new FCMSender();
		fcm.send(FCMSender.msgBuilder()
			.setTitle(title)
			.setBody(body)
			.setToken(token)
			.setClickAction(click_action)
			.putData("timestamp", String.valueOf(timestamp))
			.build());
		
		return true;
	}
	
	@Override
	public boolean SetPattern(String id, String pattern) throws Exception {
		dao.open();
		boolean result = dao.setPattern(id, pattern);
		dao.commit();
		dao.close();
		
		return result;
	}

	@Override
	public boolean VerifyPattern(String id, String pattern) throws Exception {
		dao.open(true);
		String exist = dao.getPattern(id);
		dao.close();
		
		int result = exist.compareTo(pattern);
		if(result != 0)
			return false;
		return true;
	}
}