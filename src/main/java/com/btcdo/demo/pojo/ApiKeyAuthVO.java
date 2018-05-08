package com.btcdo.demo.pojo;

import java.beans.Transient;

import com.btcdo.demo.util.IpUtil;

public class ApiKeyAuthVO {

	private long userId;

	private String apiKey;

	private String apiSecret;

	private String description;

	private int netAddress;

	private int netmask;

	private boolean canTrade;

	private boolean canWithdraw;

	private boolean enabled;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNetAddress() {
		return netAddress;
	}

	public void setNetAddress(int netAddress) {
		this.netAddress = netAddress;
	}

	public int getNetmask() {
		return netmask;
	}

	public void setNetmask(int netmask) {
		this.netmask = netmask;
	}

	public boolean isCanTrade() {
		return canTrade;
	}

	public void setCanTrade(boolean canTrade) {
		this.canTrade = canTrade;
	}

	public boolean isCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(boolean canWithdraw) {
		this.canWithdraw = canWithdraw;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * IP address allowed, such as 12.34.56.0/24. Default to empty "".
	 */
	public void setIpRestriction(String ipRestriction) {
		int[] ns = IpUtil.parseIpRestriction(ipRestriction);
		this.netAddress = ns[0];
		this.netmask = ns[1];
	}

	@Transient
	public String getIpRestriction() {
		return IpUtil.getIPRestriction(this.netAddress, this.netmask);
	}

	@Override
	public String toString() {
		return "ApiKeyAuthVO [userId=" + userId + ", apiKey=" + apiKey + ", apiSecret=" + apiSecret + ", description="
				+ description + ", netAddress=" + netAddress + ", netmask=" + netmask + ", canTrade=" + canTrade
				+ ", canWithdraw=" + canWithdraw + ", enabled=" + enabled + "]";
	}

}
