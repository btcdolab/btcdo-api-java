package com.btcdo.demo.pojo;

import java.math.BigDecimal;

import com.btcdo.demo.enums.OrderType;

public class OrderBeanReqDto {

	/**
	 * Order type.
	 */
	private OrderType orderType;

	/**
	 * Symbol like "BTC_USD".
	 */
	private String symbol;

	/**
	 * Buy or sell price.
	 */
	private BigDecimal price;

	/**
	 * Buy or sell amount.
	 */
	private BigDecimal amount;

	private long targetOrderId;

	private int customFeatures;

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getCustomFeatures() {
		return customFeatures;
	}

	public void setCustomFeatures(int customFeatures) {
		this.customFeatures = customFeatures;
	}

	public long getTargetOrderId() {
		return targetOrderId;
	}

	public void setTargetOrderId(long targetOrderId) {
		this.targetOrderId = targetOrderId;
	}

	@Override
	public String toString() {
		return "OrderBeanReqDto [orderType=" + orderType + ", symbol=" + symbol + ", price=" + price + ", amount="
				+ amount + ", targetOrderId=" + targetOrderId + ", customFeatures=" + customFeatures + "]";
	}

}
