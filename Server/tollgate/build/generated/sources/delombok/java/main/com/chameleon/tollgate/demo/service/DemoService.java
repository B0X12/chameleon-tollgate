package com.chameleon.tollgate.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chameleon.tollgate.demo.DemoResult;
import com.chameleon.tollgate.demo.dao.DemoDAO;

@Service
public class DemoService implements IDemoService {
	@Autowired
	private DemoDAO dao;
	
	@Override
	public DemoResult getUser(String name) {
		return dao.getUser(name);
	}
}