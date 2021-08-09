package com.chameleon.tollgate.fingerprint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.fingerprint.dao.fingerDAO;

@Service
public class fingerService {
	
	@Autowired
	fingerDAO dao;
	
	// 푸시 팝업 전송
	public boolean SendMessage(String id, long timestamp)
	{
		String token = dao.GetToken(id); // getToken: id를 이용해 db에서 token을 얻어오는 함수 
		
		final String title = id + "님!";
		final String body = "등록하신 기기에서 지문 인증 요청이 발생했어요.";
		final String click_action = "android.intent.action.AUTH_FINGERPRINT"; // 동작할 액티비티명
		
		try {
			FCMSender fcm = new FCMSender();
			fcm.send(FCMSender.msgBuilder()
						.setTitle(title)
						.setBody(body)
						.setToken(token)
						.setClickAction(click_action)
						.putData("timestamp", String.format("%d", timestamp))
						.build());
		} catch(Exception e) {
			System.out.println("FCM Exception : " + e);
		}
		
		return true;
	}
	
	public boolean SetFingerEnrolled(String id, long timestamp) throws Exception
	{
		dao.open();
		dao.setInitFactor(id, Factor.FINGERPRINT, true);
		dao.commit();
		dao.close();
		
		return true;
	}
}