package com.hatiko.ripple.wrapper.utils;

import java.util.Collections;

import com.hatiko.ripple.wrapper.converter.RippleBalanceConverter;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTrxByHashResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.TransactionBody;

public class TrxByHashTestUtil {
	
	public static final String HASH = "2011F46987F95B6125FD011036A758F8EC93A1AA4ADEE4835B4B936EAB2FDDC6";
	public static final String GET_TRX_BY_HASH_URL = "/api/wrapper/transaction?id=" + HASH;

	private static final String AMOUNT = "150000000";
	public static final String EXPECTED_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
	public static final Double EXPECTED_AMOUNT = RippleBalanceConverter.toDouble(AMOUNT);
	public static final String EXPECTED_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
	public static final String EXPECTED_FEE = "10";
	public static final String MEMO = "41424137303235364232314536373637464230323342383435374345433335454234393034334533424241463930444336443138413830383832433336323538";
	public static final String EXPECTED_MEMO = "ABA70256B21E6767FB023B8457CEC35EB49043E3BBAF90DC6D18A80882C36258";
	public static final Long EXPECTED_LEDGER_INDEX = 21L;
	public static final String EXPECTED_TRANSACTION_SIGNATURE = "304402204C9E2A6C4C8ECC77DF326091087ED2BAE0FC5C4A9A53E2E05E863E0016F4E710022076D0FAE69A27C4DFA574B7A93AAEE5D8EC38B46C89BCEE28C81A0EF1E934EE08";
	public static final String EXPECTED_HASH = "2011F46987F95B6125FD011036A758F8EC93A1AA4ADEE4835B4B936EAB2FDDC6";
	
	public static RippleTrxByHashResponse getDefaultTrxByHashResponse() {
		
		TransactionBody.Memos.Memo memo = TransactionBody.Memos.Memo.builder().memoData(MEMO).build();
		TransactionBody.Memos memos = TransactionBody.Memos.builder().memo(memo).build();
		
		TransactionBody result = TransactionBody.builder()
				.account(EXPECTED_ACCOUNT)
				.amount(AMOUNT)
				.destination(EXPECTED_DESTINATION)
				.fee(EXPECTED_FEE).hash(EXPECTED_HASH)
				.memos(Collections.singletonList(memos))
				.ledgerIndex(EXPECTED_LEDGER_INDEX)
				.transactionSignature(EXPECTED_TRANSACTION_SIGNATURE)
				.build();
		
		return RippleTrxByHashResponse.builder().result(result).build();
	}
}
