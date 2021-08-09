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
	
	// Ǫ�� �˾� ����
	public boolean SendMessage(String id, long timestamp)
	{
		String token = dao.GetToken(id); // getToken: id�� �̿��� db���� token�� ������ �Լ� 
		
		final String title = id + "��!";
		final String body = "����Ͻ� ��⿡�� ���� ���� ��û�� �߻��߾��.";
		final String click_action = "android.intent.action.AUTH_FINGERPRINT"; // ������ ��Ƽ��Ƽ��
		
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