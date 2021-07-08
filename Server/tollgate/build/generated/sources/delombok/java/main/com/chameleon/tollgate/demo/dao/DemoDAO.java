package com.chameleon.tollgate.demo.dao;

import org.springframework.stereotype.Component;

import com.chameleon.tollgate.demo.DemoResult;

@Component
public class DemoDAO implements IDemoDAO {
	@Override
	public DemoResult getUser(String name) {
		//DB���� �˻� �� ��� ��ȯ
		DemoResult result = new DemoResult();
		result.setResult(name);
		return result;
	}
}