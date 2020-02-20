package com.hatiko.ripple.wrapper.constants;

public enum TransactionType {

	DEPOSIT, WITHDRAW;

	public static TransactionType getTransactionType(String account, String accountAddress) {

		if (accountAddress.equals(account)) {
			return TransactionType.WITHDRAW;
		} else {
			return TransactionType.DEPOSIT;
		}
	}
}
