package com.chameleon.tollgate.demo.dao;

import com.chameleon.tollgate.demo.DemoResult;

public interface IDemoDAO {
	DemoResult getUser(String name);
}