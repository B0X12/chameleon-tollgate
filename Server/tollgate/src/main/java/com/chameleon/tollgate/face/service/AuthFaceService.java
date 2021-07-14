package com.chameleon.tollgate.face.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.face.dao.AuthFaceDAO;
import com.chameleon.tollgate.face.dto.FacePack;
import com.chameleon.tollgate.fcm.FCMSender;

@Service
public class AuthFaceService implements IAuthFaceService{

	@Autowired
	AuthFaceDAO dao;
	
	@Override
	public boolean isOnDB(String id) throws Exception {
		dao.open(true);
		int is = dao.getCountOf(Table.AUTH_FACE, "id", id);
		dao.close();
		if(is == 0) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean SendSignal(String id, long timestamp) throws Exception {
		dao.open(true);
		String token = dao.getToken(id);
		String hashValue = dao.getFace(id);
		dao.close();
		
		final String title = "얼굴 인증";
		final String body = "얼굴 인증 요청이 발생했습니다.";
		final String click_action = "android.intent.action.AUTH_FACE";
		final String mode = "auth";
		
		FCMSender fcm = new FCMSender();
		fcm.send(FCMSender.msgBuilder()
			.setTitle(title)
			.setBody(body)
			.setToken(token)
			.setClickAction(click_action)
			.putData("hashValue", hashValue)
			.putData("timestamp", String.valueOf(timestamp))
			.putData("mode", mode)
			.build());
		
		return true;
	}
	
	public boolean SetFace(String id, String hashValue) throws Exception {
		dao.open();
		boolean result = dao.setFace(id, hashValue);
		dao.commit();
		dao.close();
		
		return result;
	}
	
	public boolean VerifyFace(String id, FacePack entry) throws Exception{
		dao.open(true);		
		String hashValue = dao.getFace(id);
		dao.close();

		if(hashValue.compareTo(entry.getHashValue()) == 0 && entry.isResult()) 
			return true;
		return false;
	}
}
