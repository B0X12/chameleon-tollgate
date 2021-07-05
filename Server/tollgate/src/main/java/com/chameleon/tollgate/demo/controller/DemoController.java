package com.chameleon.tollgate.demo.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chameleon.tollgate.demo.DemoResult;
import com.chameleon.tollgate.demo.service.*;
import com.chameleon.tollgate.face.*;

@RestController
public class DemoController {
	@Autowired
	private DemoService demoSvc;

	// URL /demo에 대한 Get 메소드 매칭
	@GetMapping(path="/demo/{name}")
	public DemoResult getTest(@PathVariable("name") String name) { // 매개변수 문자열
		// 매개변수를 적절히 가공해 서비스 객체의 함수로 전송
		return demoSvc.getUser(name);
	}

	// URL /demo에 대한 Post 메소드 매칭
	@PostMapping(path="/demo/{name}")
	public DemoResult postTest(String name) {
		// 매개변수를 적절히 가공해 서비스 객체의 함수로 전송
		return demoSvc.getUser(name);
	}
	
	@PostMapping(path="/fcm")
	public String fcmTest(String title, String body, String token,
			@RequestBody Map<String, String> data, String click_action) {
		FCMService fcm = new FCMService();
		return fcm.send(title, body, token, data, click_action);
	}
	
	// @PostMapping, @DeleteMapping 등이 있다
	@PostMapping(path="/file")
	public String test(@RequestPart MultipartFile file) throws IOException {
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		
//		byte[] a = new byte[(int)file.getSize()];
//		a = file.getBytes();
//		
//		System.out.println(a.length);
//		for(byte i : a) {
//			System.out.print(i);
//		}
		
		
		utils.saveFile(file, "D:/");
		
		return "succeed";
	}
}