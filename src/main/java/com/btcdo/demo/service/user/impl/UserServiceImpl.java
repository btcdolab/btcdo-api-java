package com.btcdo.demo.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.btcdo.demo.pojo.DepositLog;
import com.btcdo.demo.pojo.SpotAccountReqDto;
import com.btcdo.demo.service.AbstractService;
import com.btcdo.demo.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("userServiceBtcdo")
public class UserServiceImpl extends AbstractService implements UserService {

	@Override
	public List<SpotAccountReqDto> getAccount() {
		Map<String, List<SpotAccountReqDto>> resp = client.getRestClientSign()
				.get(new TypeReference<Map<String, List<SpotAccountReqDto>>>() {
				}, "/v1/user/accounts", null);
		List<SpotAccountReqDto> accounts = resp.get("accounts");
		return accounts;
	}

	@Override
	public List<DepositLog> getDepositLog(String currency) {
		Map<String, String> query = new HashMap<String, String>();
		query.put("pending", "false");
		Map<String, List<DepositLog>> resp = client.getRestClientSign()
				.get(new TypeReference<Map<String, List<DepositLog>>>() {
				}, "/v1/user/deposits/" + currency, query);
		List<DepositLog> depositLogs = resp.get("deposits");
		return depositLogs;
	}

}
