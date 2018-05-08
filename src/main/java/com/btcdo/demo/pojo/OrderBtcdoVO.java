package com.btcdo.demo.pojo;

import java.math.BigDecimal;

import com.btcdo.demo.enums.OrderStatus;
import com.btcdo.demo.enums.OrderType;

public class OrderBtcdoVO {

	private long id;

	private OrderType type;

	private OrderStatus status;

	private int index;

	private String symbol;

	private BigDecimal amount;

	private BigDecimal price;

	private long targetOrderId;

	private long seqId;

	private long refOrderId;

	private long refSeqId;

	private long userId;

	private BigDecimal filledAmount;

	private BigDecimal fee;

	private BigDecimal triggerOn;

	private int features;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getTargetOrderId() {
		return targetOrderId;
	}

	public void setTargetOrderId(long targetOrderId) {
		this.targetOrderId = targetOrderId;
	}

	public long getSeqId() {
		return seqId;
	}

	public void setSeqId(long seqId) {
		this.seqId = seqId;
	}

	public long getRefOrderId() {
		return refOrderId;
	}

	public void setRefOrderId(long refOrderId) {
		this.refOrderId = refOrderId;
	}

	public long getRefSeqId() {
		return refSeqId;
	}

	public void setRefSeqId(long refSeqId) {
		this.refSeqId = refSeqId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getFilledAmount() {
		return filledAmount;
	}

	public void setFilledAmount(BigDecimal filledAmount) {
		this.filledAmount = filledAmount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getTriggerOn() {
		return triggerOn;
	}

	public void setTriggerOn(BigDecimal triggerOn) {
		this.triggerOn = triggerOn;
	}

	public int getFeatures() {
		return features;
	}

	public void setFeatures(int features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "OrderBtcdoVO [id=" + id + ", type=" + type + ", status=" + status + ", index=" + index + ", symbol="
				+ symbol + ", amount=" + amount + ", price=" + price + ", targetOrderId=" + targetOrderId + ", seqId="
				+ seqId + ", refOrderId=" + refOrderId + ", refSeqId=" + refSeqId + ", userId=" + userId
				+ ", filledAmount=" + filledAmount + ", fee=" + fee + ", triggerOn=" + triggerOn + ", features="
				+ features + "]";
	}


}
