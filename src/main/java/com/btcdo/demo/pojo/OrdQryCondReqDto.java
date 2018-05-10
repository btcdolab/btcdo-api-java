package com.btcdo.demo.pojo;

public class OrdQryCondReqDto {
	
	private long offsetId;
	private int limit;
	private String symbol;

	public long getOffsetId() {
		return offsetId;
	}

	public void setOffsetId(long offsetId) {
		this.offsetId = offsetId;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
