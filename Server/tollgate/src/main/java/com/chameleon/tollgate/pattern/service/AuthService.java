package com.chameleon.tollgate.pattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.pattern.dao.AuthDAO;
import com.chameleon.tollgate.util.tollgateLog.TollgateLog;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.code.ConfigCode;

@Service
public class AuthService implements IAuthService {
	@Autowired
	AuthDAO dao;
	
	@Override
	public boolean SendSignal(String id, long timestamp, String pc) throws Exception {
		try {
			dao.open(true);
			String token = dao.getToken(id);
			dao.close();
			final String title = id + "님!";
			final String body = "등록하신 기기에서 패턴 인증 요청이 발생했어요.";
			final String click_action = "android.intent.action.AUTH_PATTERN";
			
			FCMSender fcm = new FCMSender();
			fcm.send(FCMSender.msgBuilder()
				.setTitle(title)
				.setBody(body)
				.setToken(token)
				.setClickAction(click_action)
				.putData("timestamp", String.valueOf(timestamp))
				.putData("pc", pc)
				.build());
		
			return true;
		} finally {
			this.dao.close();
		}
	}
	
	@Override
	public boolean SetPattern(String id, String pattern, String ip) throws Exception {
		try {
			dao.open();
			boolean result = dao.setPattern(id, pattern, ip);
			if(result) {
				dao.setInitFactor(id, Factor.PATTERN, true);
				TollgateLog.i(ip, id, LogFactor.PATTERN, ConfigCode.INIT_PATTERN, "Initialize user's pattern factor.");
			}
			dao.commit();
			dao.close();
			return result;
		} finally {
			this.dao.close();
		}
	}

	@Override
	public boolean VerifyPattern(String id, String pattern, String ip) throws Exception {
		try {
			dao.open(true);
			String exist = dao.getPattern(id, ip);
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