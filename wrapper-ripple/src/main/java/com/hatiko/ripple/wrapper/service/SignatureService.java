package com.hatiko.ripple.wrapper.service;

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
	 * @return
	 */
	String signTransaction(String to, Double amount, String memo);
}
