package com.chameleon.tollgate.demo.service;

import java.util.Map;

import com.chameleon.tollgate.fcm.FCMSender;

public class FCMService implements IFCMService {
	public FCMService() {
		super();
	}
	
	@Override
	public String send(String title, String body, String token, Map<String, String> data) {
		try {
			System.out.println(data.size());
			System.out.println(data);
			FCMSender fcm = new FCMSender();
			return fcm.send(FCMSender.msgBuilder()
				.setTitle(title)
				.setBody(body)
				.setToken(token)
				.setData(data)
				.build());
		}
		catch(Exception ex) {
			return ex.getMessage();
		}
	}
}
