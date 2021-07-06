package com.chameleon.tollgate.demo.service;

import java.util.Map;

import com.chameleon.tollgate.fcm.FCMSender;

public class FCMService implements IFCMService {
	public FCMService() {
		super();
	}
	
	@Override
	public String send(String title, String body, String token, Map<String, String> data, String click_acktion) {
		try {
			System.out.println(data.size());
			System.out.println(data);
			FCMSender fcm = new FCMSender();
			return fcm.send(FCMSender.msgBuilder()
				.setTitle(title) // 제목
				.setBody(body) // 내용
				.setToken(token) // 고유값
				.setData(data)
				.setClickAction(click_acktion)
				.build());
		}
		catch(Exception ex) {
			return ex.getMessage();
		}
	}
}
