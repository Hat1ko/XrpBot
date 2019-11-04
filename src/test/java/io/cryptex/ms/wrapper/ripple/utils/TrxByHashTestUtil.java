package io.cryptex.ms.wrapper.ripple.utils;

import io.cryptex.ms.wrapper.ripple.converter.RippleBalanceConverter;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleTrxByHashResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.TransactionBody;

import java.util.Collections;

public class TrxByHashTestUtil {
	
	public static final String HASH = "2011F46987F95B6125FD011036A758F8EC93A1AA4ADEE4835B4B936EAB2FDDC6";
	public static final String GET_TRX_BY_HASH_URL = "/api/wrapper/transaction?id=" + HASH;

	private static final String AMOUNT = "150000000";
	public static final String EXPECTED_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
	public static final Double EXPECTED_AMOUNT = RippleBalanceConverter.toDouble(AMOUNT);
	public static final String EXPECTED_DESTINATION = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
	public static final String EXPECTED_FEE = "10";
	public static final String EXPECTED_MEMO = "25AA23CBE9EB95CDB739F126B02652CD7775720432D06DB46C86907CA23FC316";
	public static final Long EXPECTED_SEQUENCE = 21L;
	public static final String EXPECTED_TRANSACTION_SIGNATURE = "304402204C9E2A6C4C8ECC77DF326091087ED2BAE0FC5C4A9A53E2E05E863E0016F4E710022076D0FAE69A27C4DFA574B7A93AAEE5D8EC38B46C89BCEE28C81A0EF1E934EE08";
	public static final String EXPECTED_HASH = "2011F46987F95B6125FD011036A758F8EC93A1AA4ADEE4835B4B936EAB2FDDC6";
	
	public static RippleTrxByHashResponse getDefaultTrxByHashResponse() {
		
		TransactionBody.Memos.Memo memo = TransactionBody.Memos.Memo.builder().memoData(EXPECTED_MEMO).build();
		TransactionBody.Memos memos = TransactionBody.Memos.builder().memo(memo).build();
		
		TransactionBody result = TransactionBody.builder()
				.account(EXPECTED_ACCOUNT)
				.amount(AMOUNT)
				.destination(EXPECTED_DESTINATION)
				.fee(EXPECTED_FEE).hash(EXPECTED_HASH)
				.memos(Collections.singletonList(memos))
				.sequence(EXPECTED_SEQUENCE)
				.transactionSignature(EXPECTED_TRANSACTION_SIGNATURE)
				.build();
		
		return RippleTrxByHashResponse.builder().result(result).build();
	}
}
