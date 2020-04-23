package com.hatiko.ripple.wrapper.service;

import com.hatiko.ripple.wrapper.web.model.AddressResponse;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.MemoResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;
import com.hatiko.ripple.wrapper.web.model.WithdrawRequest;

/**
 * Describes main logic of the MS
 */
public interface RippleService {
	/**
	 * Gets balance from the Ripple blockchain for the account specified in application.properties
	 * @return balance response that is to be send to CryptoPayment MS
	 */
	BalanceResponse getWalletBalance();
	
	
	BalanceResponse getWalletBalanceByAccountAddress(String accounStAddress);
	/**
	 * Gets account address from application.properties
	 */
	AddressResponse getAccountAddress();
	
	/**
	 * Gets info about transaction 
	 * @param transactionHash hash of some transaction
	 * @return response about transaction that is to be send to CryptoPayment MS
	 */
	TransactionResponse getTransactionByHash(String transactionHash);
	
	/**
	 * Withdraws XRP from account set in application.properties to specified account
	 * @param withdrawRequest information required for transaction(destination, memo, balance)
	 * @return hash of the transaction that is signed and accepted
	 */
	TransactionResponse withdraw(WithdrawRequest withdrawRequest);
	
	TransactionResponse withdrawByCredentials(String publicKey, String privateKey, String destinationKey, String memo,
			Double amount);
	/**
	 * Generates new UUID string
	 * @return memo response to send to CryptoPayment MS
	 */
	MemoResponse generateMemo();




}
