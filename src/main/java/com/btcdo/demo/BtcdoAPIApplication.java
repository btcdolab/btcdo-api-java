package com.btcdo.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.btcdo.demo" })
public class BtcdoAPIApplication {

	static final Logger logger = LoggerFactory.getLogger(BtcdoAPIApplication.class);

	public static void main(String[] args) {
		logger.info("start {} {}...", BtcdoAPIApplication.class.getSimpleName(),
				BtcdoAPIApplication.class.getPackage().getImplementationVersion());
		SpringApplication.run(BtcdoAPIApplication.class, args);
	}
}
