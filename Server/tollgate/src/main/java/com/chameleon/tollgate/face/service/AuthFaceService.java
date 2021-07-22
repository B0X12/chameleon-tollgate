package com.chameleon.tollgate.face.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.database.define.Table;
import com.chameleon.tollgate.face.dao.AuthFaceDAO;
import com.chameleon.tollgate.fcm.FCMSender;

import org.python.*;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

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
	public boolean SendSignal(String id) throws Exception {
		dao.open(true);
		String token = dao.getToken(id);
		String hash = dao.getFace(id);
		dao.close();
		
		final String title = "얼굴 인증";
		final String body = "얼굴 인증 요청이 발생했습니다.";
		final String click_action = "android.intent.action.AUTH_FACE";
		
		FCMSender fcm = new FCMSender();
		
		System.out.println(hash);
		fcm.send(FCMSender.msgBuilder()
				.setTitle(title)
				.setBody(body)
				.setToken(token)
				.putData("hash", hash)
				.setClickAction(click_action)
				.build());
		
		return true;
	}
	
	public boolean SetFace(String id, String hash) throws Exception {
		dao.open();
		boolean result = dao.setFace(id, hash);
		dao.commit();
		dao.close();
		
		return result;
	}
	
}
