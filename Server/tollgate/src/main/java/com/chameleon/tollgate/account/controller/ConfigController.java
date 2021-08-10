package com.chameleon.tollgate.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chameleon.tollgate.account.dto.FactorPack;
import com.chameleon.tollgate.account.service.ConfigService;
import com.chameleon.tollgate.database.define.Factor;
import com.chameleon.tollgate.define.Path;
import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentError;
import com.chameleon.tollgate.rest.exception.UnauthorizedUserAgentException;

@RestController
public class ConfigController {
	@Autowired
	ConfigService service;
	
	@GetMapping(Path.INIT_AND + "{id}")
	public ResponseEntity<Response<Boolean>> isInitAndroid(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable String id, long timestamp) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.isInitAndroid(id), timestamp),
				HttpStatus.OK);
	}
	
	@GetMapping(Path.INIT + "{factor}/{id}")
	public ResponseEntity<Response<Boolean>> isInit(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable String factor, @PathVariable String id, long timestamp) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.isInitFactor(id, Factor.findBy(factor)), timestamp),
				HttpStatus.OK);
	}
	
	@PostMapping(Path.INIT + "{id}")
	public ResponseEntity<Response<Boolean>> setInit(@RequestHeader(value = "User-Agent") String userAgent,
			@PathVariable String id, @RequestBody FactorPack entry) throws Exception {
		if (!userAgent.equals(Property.USER_AGENT))
			throw new UnauthorizedUserAgentException(UnauthorizedUserAgentError.UNAUTHERIZED_USER_AGENT);
		
		return new ResponseEntity<>(
				new Response<>(HttpStatus.OK, this.service.setInitFactor(id, Factor.findBy(entry.getFactor()), entry.isValue()), entry.getTimestamp()),
				HttpStatus.OK);
	}
}