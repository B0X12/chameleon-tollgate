package com.chameleon.tollgate.face.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.face.AuthFaceStatus;
import com.chameleon.tollgate.face.dto.Test;
import com.chameleon.tollgate.face.service.AuthFaceService;



@RestController
public class AuthFaceController {
	private static final String path = "D:/";
	
	
	@Autowired
	AuthFaceService service;

	ArrayList<AuthFaceStatus> status;
	AuthFaceController(){
		status = new ArrayList<AuthFaceStatus>();
		
	}
	
	// 얼굴 해시 등록
	@PostMapping(path=Path.REGIST_FACEID+"{id}")
	public boolean registerFace(@PathVariable("id") String id, @RequestBody Test body) throws Exception {
		System.out.println(body.body);
		return service.SetFace(id, body.body);
	}
	
	// 인증 시작 요청
	@GetMapping(path=Path.AUTH_FACEID+"{id}")
	public boolean SendSignal(@PathVariable("id") String id) throws Exception {
		service.SendSignal(id);
		AuthFaceStatus stat = new AuthFaceStatus(id);
		status.add(stat);
		// mobile에서 결과 반환까지 대기
		while(!stat.isVerified());
		boolean result = stat.isSuccess();
		status.remove(stat);
		
		// client로 가는 결과
		return result;
	}
	
		
	@PostMapping(path=Path.AUTH_FACEID+"{id}")
	public boolean FaceResult(@PathVariable("id") String id, @RequestBody Test body) throws Exception{
		AuthFaceStatus as = null;
		for(AuthFaceStatus i : status)
			if(i.getId().compareTo(id) == 0) {
				as = i;
			}
		if(as == null) {
			return false;
			}
		
		if(body.body.compareTo("true") == 0) {
			as.setSuccess(true);
			as.setVerified(true);
			System.out.println("Verified!!!!");
			return true;
		}
		
		// mobile로 가는 결과
		return false;
	}
	

}
