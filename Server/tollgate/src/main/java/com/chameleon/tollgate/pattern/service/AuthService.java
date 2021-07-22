package com.chameleon.tollgate.pattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.pattern.dao.AuthDAO;

@Service
public class AuthService implements IAuthService {
	@Autowired
	AuthDAO dao;
	
	@Override
	public boolean SendSignal(String id, long timestamp) throws Exception {
		try {
			dao.open(true);
			String token = dao.getToken(id);
			dao.close();
			final String title = "패턴 인증";
			final String body = "패턴 인증 요청이 발생했습니다.";
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
		} finally {
			this.dao.close();
		}
	}
	
	@Override
	public boolean SetPattern(String id, String pattern) throws Exception {
		try {
			dao.open();
			boolean result = dao.setPattern(id, pattern);
			dao.setInitFactor(id, Factor.PATTERN, true);
			dao.commit();
			dao.close();
			
			return result;
		} finally {
			this.dao.close();
		}
	}

	@Override
	public boolean VerifyPattern(String id, String pattern) throws Exception {
		try {
			dao.open(true);
			String exist = dao.getPattern(id);
			dao.close();
			
			int result = exist.compareTo(pattern);
			if(result != 0)
				return false;
		return true;
		} finally {
			this.dao.close();
		}
	}
}