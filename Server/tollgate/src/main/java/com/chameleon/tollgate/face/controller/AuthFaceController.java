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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.chameleon.tollgate.face.dto.FacePack;
import com.chameleon.tollgate.face.service.AuthFaceService;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.chameleon.tollgate.rest.AuthList;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.SessionList;
import com.chameleon.tollgate.rest.SessionTime;
import com.chameleon.tollgate.rest.exception.AuthError;
import com.chameleon.tollgate.rest.exception.InvalidRequestException;
=======
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c
=======
>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c


>>>>>>> 6cb2a903a463f81eb0c87c577ff2e04a6e51037c

@RestController
public class AuthFaceController {
	private static final String path = "D:/";	
	
	@Autowired
	AuthFaceService service;

	SessionList sessions;
	AuthList status;
	
	AuthFaceController(){
		this.status = new AuthList();
		this.sessions = new SessionList();
	}
	
	// 얼굴 해시 등록
	@PostMapping(path=Register.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> registerFace(@PathVariable("id") String id, @RequestBody FacePack entry) throws Exception {
		System.out.println(entry.getHashValue());
		
		Response<Boolean> respon = new Response<Boolean>(HttpStatus.OK, service.SetFace(id, entry.getHashValue()), entry.getTimestamp());
		return new ResponseEntity<>(respon, HttpStatus.OK);
	}
	
	// 인증 시작 요청
	@GetMapping(path=Auth.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> SendSignal(@PathVariable("id") String id, long timestamp) throws Exception {
		this.sessions.add(id, timestamp);
		service.SendSignal(id, timestamp);
		this.status.add(id);
		boolean result = status.waitVerify(id);
		this.status.remove(id);
		this.sessions.remove(id);		

		if(result) {
			System.out.println("Verified");
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.OK, result, timestamp),
					HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.BAD_REQUEST, result, timestamp),
					HttpStatus.BAD_REQUEST);
	}
	
		
	@PostMapping(path=Auth.FACEID+"{id}")
	public ResponseEntity<Response<Boolean>> VerifyFace(@PathVariable("id") String id, @RequestBody FacePack entry) throws Exception{
		if(!this.sessions.isExist(new SessionTime(id, entry.getTimestamp())))
			throw new InvalidRequestException(AuthError.NO_SESSION);
		
		if(service.VerifyFace(id, entry)) {
			this.status.verify(id, true);
			return new ResponseEntity<>(
					new Response<Boolean>(HttpStatus.OK, true, entry.getTimestamp()),
					HttpStatus.OK);
		}
		
		this.status.verify(id, false);
		
		return new ResponseEntity<>(
				new Response<Boolean>(HttpStatus.BAD_REQUEST, false, entry.getTimestamp()),
				HttpStatus.BAD_REQUEST);
	}
	

}
