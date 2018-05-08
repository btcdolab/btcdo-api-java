package com.btcdo.demo.pojo;

import java.math.BigDecimal;

import com.btcdo.demo.enums.AccountTypeVO;

public class SpotAccountReqDto {

	private long userId;

	private String currency;

	private AccountTypeVO type;

	private BigDecimal balance;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public AccountTypeVO getType() {
		return type;
	}

	public void setType(AccountTypeVO type) {
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "SpotAccountReqDto [userId=" + userId + ", currency=" + currency + ", type=" + type + ", balance="
				+ balance + "]";
	}


}
