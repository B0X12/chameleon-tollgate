package com.chameleon.tollgate.face.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chameleon.tollgate.define.url.Auth;
import com.chameleon.tollgate.define.url.Register;
import com.chameleon.tollgate.face.AuthFaceStatus;
import com.chameleon.tollgate.face.utils;
import com.chameleon.tollgate.face.dto.Test;
import com.chameleon.tollgate.face.service.AuthFaceService;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.chameleon.tollgate.pattern.AuthStatus;
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c



@RestController
public class AuthFaceController {
	private static final String path = "D:/";
	
	
	@Autowired
	AuthFaceService service;

	ArrayList<AuthFaceStatus> status;
	AuthFaceController(){
		status = new ArrayList<AuthFaceStatus>();
		
	}
	
	// �� �ؽ� ���
	@PostMapping(path=Register.FACEID+"{id}")
	public boolean registerFace(@PathVariable("id") String id, @RequestBody Test body) throws Exception {
		System.out.println(body.body);
		return service.SetFace(id, body.body);
	}
	
	// ���� ���� ��û
	@GetMapping(path=Auth.FACEID+"{id}")
	public boolean SendSignal(@PathVariable("id") String id) throws Exception {
		service.SendSignal(id);
		AuthFaceStatus stat = new AuthFaceStatus(id);
		status.add(stat);
		// mobile���� ��� ��ȯ���� ���
		while(!stat.isVerified());
		boolean result = stat.isSuccess();
		status.remove(stat);
		
		// client�� ���� ���
		return result;
	}
	
		
	@PostMapping(path=Auth.FACEID+"{id}")
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
		
		// mobile�� ���� ���
		return false;
	}
	

}
