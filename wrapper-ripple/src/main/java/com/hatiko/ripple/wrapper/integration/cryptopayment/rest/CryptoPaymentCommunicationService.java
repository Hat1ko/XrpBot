package com.hatiko.ripple.wrapper.integration.cryptopayment.rest;

import java.util.List;

import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.integration.rest.CommunicationService;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

/**
 *Describes logic in communication with CryptoPayment MS 
 *
 */
public interface CryptoPaymentCommunicationService extends CommunicationService {

	/**
	 * Gets the last processed transaction sequence
	 * @return sequence of last processed block by CryptoPaymetn MS
	 */
	LastProcessedTransactionData getLastProcessedTransactionData();
	
	/**
	 * Sends the list of unprocessed transactions to CryptoPayment MS
	 * @param transactions list of unprocessed transactions
	 */
	void sendNewTransactionsToProcess(List<TransactionResponse> transactions);
}
