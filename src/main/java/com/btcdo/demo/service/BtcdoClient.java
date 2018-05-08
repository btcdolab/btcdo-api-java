package com.btcdo.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.btcdo.demo.RestClient;

@Component
public class BtcdoClient {

	private volatile static RestClient restClientSign;
	
	@Value("${api.setting.btcdo.apiKey}")
	private String apiKey;
	@Value("${api.setting.btcdo.apiSecret}")
	private String secret;
	@Value("${api.setting.btcdo.restApiEndpoint}")
	private String restApiEndpoint;

	public RestClient getRestClientSign() {
		if (restClientSign == null) {
			synchronized (RestClient.class) {
				if (restClientSign == null) {
					restClientSign = new RestClient.Builder(restApiEndpoint).authenticate(apiKey, secret).build();
				}
			}
		}
		return restClientSign;
	}
}
