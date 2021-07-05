package com.chameleon.tollgate.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.account.dto.Account;
import com.chameleon.tollgate.account.dto.MapPC;
import com.chameleon.tollgate.account.service.AccountService;

@RestController
public class AccountController {
	@Autowired
	   private AccountService accountService;

	   @PostMapping(path = "/account/login")
	   public boolean logIn(@RequestBody Account account) {
	      boolean result = accountService.logIn(account);
	      return result;
	   }
	   
	   @PostMapping(path = "/account/signup")
	   public boolean signUp(@RequestBody Account account) {
	      boolean result = accountService.signUp(account);
	      return result;
	   }
	   
	   @PostMapping(path = "/account/map/pc")
	   public boolean mappingPC(@RequestBody MapPC map_pc) {
	      boolean result = accountService.mappingPC(map_pc);
	      return result;
	   }

}
