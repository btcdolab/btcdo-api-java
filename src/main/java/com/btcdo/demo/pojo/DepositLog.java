package com.btcdo.demo.pojo;

import java.math.BigDecimal;

public class DepositLog {

	/**
	 * Status for deposit:
	 * 
	 * PENDING -> DEPOSITED
	 * 
	 * PENDING -> DEPOSITED -> CANCELLED
	 * 
	 * PENDING -> WAITING_FOR_APPROVAL -> DEPOSITED
	 * 
	 * PENDING -> WAITING_FOR_APPROVAL -> DENIED
	 */
	public static enum DepositStatus {
		/**
		 * Waiting for more block confirm.
		 */
		PENDING,

		/**
		 * Already deposited.
		 */
		DEPOSITED,

		/**
		 * Already cancelled.
		 */
		CANCELLED,

		/**
		 * Waiting for approval.
		 */
		WAITING_FOR_APPROVAL,

		/**
		 * Denied by manager.
		 */
		DENIED;
	}

	public DepositStatus status;

	public long userId;

	public String currency;

	public String toAddress;

	public String uniqueId;

	public BigDecimal amount;

	public int confirms;

	public int minimumConfirms;

	public int deposited;

	public int cancelled;

	@Override
	public String toString() {
		return String.format(
				"{DepositLog: status=%s, currency=%s, toAddress=%s, uniqueId=%s, amount=%s, confirms=%d/%d, deposited=%s, cancelled=%s}",
				this.status, this.currency, this.toAddress, this.uniqueId, this.amount, this.confirms,
				this.minimumConfirms, this.deposited, this.cancelled);
	}
}
