package com.chameleon.tollgate.fingerprint.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.chameleon.tollgate.fcm.FCMSender;
import com.chameleon.tollgate.fingerprint.dao.FingerprintDAO;

public class FingerprintService {
	
	@Autowired
	FingerprintDAO dao;
	
	// Ǫ�� �˾� ����
	public boolean SendSignal(String id)
	{
		String token = dao.GetToken(id); // getToken: id�� �̿��� db���� token�� ������ �Լ� 
		
		final String title = "���� ����";
		final String body = "���� ���� ��û�� �߻��Ͽ����ϴ�.";
		final String click_action = "android.intent.action.AUTH_FINGERPRINT"; // ������ ��Ƽ��Ƽ��
		
		try {
			FCMSender fcm = new FCMSender();
			fcm.send(FCMSender.msgBuilder()
						.setTitle(title)
						.setBody(body)
						.setToken(token)
						.setClickAction(click_action)
						.build());
		} catch(Exception e) {
			System.out.println("#FCM Exception : " + e);
		}
		
		return true;
	}
}
