package io.cryptex.ms.wrapper.ripple.integration.cryptopayment.rest;

import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.dto.LastProcessedTransactionData;
import io.cryptex.ms.wrapper.ripple.integration.rest.CommunicationService;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;

import java.util.List;

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
