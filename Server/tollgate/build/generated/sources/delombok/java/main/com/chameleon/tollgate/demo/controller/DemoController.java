package com.chameleon.tollgate.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chameleon.tollgate.demo.DemoResult;
import com.chameleon.tollgate.demo.service.*;

@RestController
public class DemoController {
	@Autowired
	private DemoService demoSvc;

	
	// URL /demo�� ���� Get �޼ҵ� ��Ī
	@GetMapping(path="/demo/{name}")
	public DemoResult getTest(@PathVariable("name") String name) { // �Ű����� ���ڿ�
		// �Ű������� ������ ������ ���� ��ü�� �Լ��� ����
		return demoSvc.getUser(name);
	}

	// URL /demo�� ���� Post �޼ҵ� ��Ī
	@PostMapping(path="/demo/{name}")
	public DemoResult postTest(String name) {
		// �Ű������� ������ ������ ���� ��ü�� �Լ��� ����
		return demoSvc.getUser(name);
	}
	
	@PostMapping(path="/fcm")
	public String fcmTest(String title, String body, String token,
			@RequestBody Map<String, String> data, String click_action) {
		FCMService fcm = new FCMService();
		return fcm.send(title, body, token, data, click_action);
	}
	// @PostMapping, @DeleteMapping ���� �ִ�
}