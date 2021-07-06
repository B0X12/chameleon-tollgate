package com.chameleon.tollgate.face.service;

import org.springframework.stereotype.Service;

@Service
public class TrainFaceService {

	TrainFaceService(){
		final String title = "얼굴 인증";
		final String body = "얼굴 인증 요청이 발생했습니다.";
		final String click_action = "android.intent.action.AUTH_FACE";
	}
	
}
