package com.btcdo.demo.service.user;

import java.util.List;

import com.btcdo.demo.pojo.DepositLog;
import com.btcdo.demo.pojo.SpotAccountReqDto;

public interface UserService {

	List<SpotAccountReqDto> getAccount();

	List<DepositLog> getDepositLog(String currency);

}
