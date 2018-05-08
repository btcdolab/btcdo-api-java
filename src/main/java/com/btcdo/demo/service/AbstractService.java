package com.btcdo.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BtcdoClient client;
	
}
