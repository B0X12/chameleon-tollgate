package com.chameleon.tollgate.pattern.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.define.url.Auth;
import com.chameleon.tollgate.define.url.Register;
import com.chameleon.tollgate.pattern.AuthStatus;
import com.chameleon.tollgate.pattern.service.AuthService;

@RestController
public class AuthController {
	@Autowired
	AuthService service;
	
	ArrayList<AuthStatus> status;
	
	public AuthController() {
		status = new ArrayList<AuthStatus>();
	}

	@PostMapping(path=Register.PATTERN+"{id}")
	public boolean SetPattern(@PathVariable("id") String id, @RequestBody String pattern) throws Exception {
		return service.SetPattern(id, pattern);
	}
	
	@GetMapping(path=Auth.PATTERN+"{id}")
	public boolean SendSignal(@PathVariable("id") String id) throws Exception {
		service.SendSignal(id);
		AuthStatus stat = new AuthStatus(id);
		status.add(stat);
		while(!stat.isVerified());
		boolean result = stat.isSuccess();
		status.remove(stat);
		return result;
	}
	
	@PostMapping(path=Auth.PATTERN+"{id}")
	public boolean VerifyPattern(@PathVariable("id") String id, String pattern) throws Exception {
		AuthStatus as = null;
		for(AuthStatus i : status)
			if(i.getId().compareTo(id) == 0) {
				as = i;
			}
		if(as == null)
			return false;
		
		if(service.VerifyPattern(id, pattern)) {	
			as.setSuccess(true);
			as.setVerified(true);
			return true;
		}
		as.setVerified(true);
		return false;
	}
}
