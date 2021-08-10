package com.chameleon.tollgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chameleon.tollgate.util.tollgateLog.tollgateLog;

@SpringBootApplication
public class TollgateApplication {
	public static void main(String[] args) {
		SpringApplication.run(TollgateApplication.class, args);
		tollgateLog.setLogPath("C:\\Tollgate\\Logs");
	}
}
