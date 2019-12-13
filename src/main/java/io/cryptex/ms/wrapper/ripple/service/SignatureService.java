package io.cryptex.ms.wrapper.ripple.service;

/**
 * 
 * The interface provides an only method
 * to interact with JS to sign a transaction
 */
public interface SignatureService {
	/**
	 * @param to destination
	 * @param amount amount of money
	 * @param memo UUID
	 * @param nextSequence sequence of the transaction next
	 * @return
	 */
	String signTransaction(String to, Long amount, String memo, String nextSequence, Long timeout);
}
