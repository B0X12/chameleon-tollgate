package com.chameleon.tollgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.chameleon.tollgate.util.tollgateLog.TollgateLog;

@SpringBootApplication
public class TollgateApplication {
	public static void main(String[] args) {
		SpringApplication.run(TollgateApplication.class, args);
	}
}
