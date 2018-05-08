package com.btcdo.demo.enums;

public enum OrderStatus {

	/**
	 * Order is submitted and pending into / already in order-sequence queue.
	 */
	SUBMITTED(false),

	/**
	 * Order is sequenced and pending into / already in order-message queue.
	 */
	SEQUENCED(false),

	/**
	 * Limit order is fully filled (do not in order book) or market order is done:
	 * maximum amount bought / sold.
	 */
	FULLY_FILLED(true),

	/**
	 * ONLY for limit order: order is partially cancelled.
	 */
	PARTIAL_CANCELLED(true),

	/**
	 * ONLY for limit order: order is fully cancelled.
	 */
	FULLY_CANCELLED(true),

	/**
	 * ONLY for cancel-type order: The cancel order execute ok and the target limit
	 * order was fully cancelled or partial cancelled.
	 */
	CANCELLED_OK(true),

	/**
	 * ONLY for cancel-type order: The cancel order execute failed because the
	 * target limit order was fully filled.
	 */
	CANCELLED_FAILED(true);

	public final boolean isFinalStatus;

	private OrderStatus(boolean isFinalStatus) {
		this.isFinalStatus = isFinalStatus;
	}
}
