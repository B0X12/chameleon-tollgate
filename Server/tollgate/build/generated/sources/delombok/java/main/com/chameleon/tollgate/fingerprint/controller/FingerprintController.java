package com.chameleon.tollgate.fingerprint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.fingerprint.service.FingerprintService;


@RestController
public class FingerprintController
{
	// ���� ��û�� ID�� Timestamp�� ��ü�� ��Ƽ� Service�� ������
	
	@Autowired
	FingerprintService service;
	
	// ���� ���� ��û
	@GetMapping(path="/fingerprint")
	public boolean SendSignal(@PathVariable("id") String id) throws Exception {
		boolean result = service.SendSignal(id);
		return result; // client�� ��� ���� - true / false
	}
}