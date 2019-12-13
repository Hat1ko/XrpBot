package io.cryptex.ms.wrapper.ripple.service;

import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.dto.LastProcessedTransactionData;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;

import java.util.List;

/**
 * The interface provides 3 methods to work with CryptoPament MS
 * Its purpose is sending unprocessed transactions to CryptoPayment MS
 */
public interface TransactionService {

	/**
	 * Gets unprocessed transactions that appeared after specialized variable
	 * @param last processed sequence
	 * @return Transaction Dtos that appear to be unprocessed
	 */
	List<TransactionResponse> getNewBlockchainTransactionsToProcess(Long lastSequence);

	/**
	 * Gets the last processed sequence from CryptoPayment MS
	 * @return
	 */
	LastProcessedTransactionData getLastProcessedTransactionData();

	/**
	 * Sends unprocessed transactions to CryptoPayment MS
	 * @param Trnsaction Dtos that should be processed by CryptoPayment MS
	 */
	void sendNewTransactionsList(List<TransactionResponse> transactions);

}