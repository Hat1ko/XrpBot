package com.hatiko.ripple.wrapper.integration.blockchain.rest;

import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleAccountInfoRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleLastTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTrxByHashRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleWithdrawRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleAccountInfoResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTrxByHashResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse;
import com.hatiko.ripple.wrapper.integration.rest.CommunicationService;

/**
 * Describes logic for communication with XRP Ledger (Ripple blockchain)
 *
 */
public interface RippleCommunicationService extends CommunicationService {
	
	/**
	 * Sends request to get account info
	 * @return current actual about account set in application.properties
	 */
	RippleAccountInfoResponse getAccountInfo(RippleAccountInfoRequest rippleAccountInfoRequest);
	
	/**
	 * Sends request to XRP Ledger (Ripple blockchain) to get transaction information by hash
	 * @param rippleTrxByHashRequest hash of the transaction to find
	 * @return response to process
	 */
	RippleTrxByHashResponse getTransactionByHash(RippleTrxByHashRequest rippleTrxByHashRequest);
	
	/**
	 * Sends request to sign and submit specified transaction
	 * @param withdrawRequestDto required information to make a transaction (destination, memo, amount)
	 * @param nextSequence next transaction sequence
	 * @return transaction hash
	 */
	RippleWithdrawResponse withdraw(RippleWithdrawRequest requestToSubmit);
	
	/**
	 * Gets last transactions | limit is counted as the subtraction of the next transaction sequence 
	 * and last processed block minus 1s  
	 * @param lastSequence last processed transaction sequence
	 * @return 
	 */
	RippleTransactionsResponse getTransactions(RippleTransactionsRequest request);

	RippleTransactionsResponse getLastTransactions(RippleLastTransactionsRequest rippleLastTransactionsRequest);
}
