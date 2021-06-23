package com.chameleon.tollgate.demo.service;

import java.util.Map;

public interface IFCMService {
	String send(String title, String body, String token, Map<String, String> data);
}
