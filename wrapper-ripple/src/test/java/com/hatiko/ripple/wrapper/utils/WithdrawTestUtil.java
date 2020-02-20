package com.hatiko.ripple.wrapper.utils;

import java.util.Arrays;
import java.util.Collections;

import com.hatiko.ripple.wrapper.converter.RippleBalanceConverter;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleAccountInfoRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleWithdrawRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleAccountInfoResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;

public class 	WithdrawTestUtil {

	public static final String WITHDRAW_URL = "/api/wrapper/withdraw";

	public static final String WITHDRAW_REQUEST_TO = "rLBeWGaYFKQ1AeszPyB9AH8YfNXAcq97xB";
	public static final String WITHDRAW_REQUEST_MEMO = "489E2F17F4F24371D88FC36E31CCB08959EDCECDE13E2C4DB055B6F312F6E74E";
	public static final String WITHDRAW_REQUEST_MEMO_DATA = "34383945324631374634463234333731443838464333364533314343423038393539454443454344453133453243344442303535423646333132463645373445";
	public static final Double WITHDRAW_REQUEST_AMOUNT = 15.0;

	public static final String WITHDRAW_RESPONSE_HASH = "14E99BAE7D0AA4CEBBC6A3B83DB8C88ED77A4FB1B10D43B43D13ED1C4658B64E";

	public static final String EXPECTED_ENGINE_RESULT = "tesSUCCESS";
	public static final Long EXPECTED_ENGINE_RESULT_CODE = 0L;
	public static final String EXPECTED_ENGINE_RESULT_MESSAGE = "The transaction was applied. Only final in a validated ledger.";
	public static final String EXPECTED_STATUS = "success";
	public static final String EXPECTED_TX_BLOB = "1200002400000018614000000000E4E1C068400000000000000A73210296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889744730450221009C8D2ABE902A9C6D4503AC0BDECD9A83657CF64B5F72A69796CF35478049E55F02203A4836E954C55EAAA82403EDDB4DD931FA7D5722A17C66AFD6F76BA7762751EE8114F0A4FB8574430D2CA82FC19169F36658F8F657BC8314D26DDB4F53091BBE15C1A2D8D81649C88672DF4FF9EA7D204A4E7C7081289FC683A96E6AEB68A5C8D96C42A5848C0292A746B24B0F306A72E1F1";

	private static final String AMOUNT = "150000000";
	public static final String EXPECTED_ACCOUNT = "r4AQFNpxUbEYgpG7t8GsUHQpkY4JcNKZRC";
	public static final Double EXPECTED_AMOUNT = RippleBalanceConverter.toDouble(AMOUNT);
	public static final String EXPECTED_DESTINATION = WITHDRAW_REQUEST_TO;
	public static final String EXPECTED_FEE = "";
	public static final String EXPECTED_MEMO = WITHDRAW_REQUEST_MEMO;
	public static final Long EXPECTED_LEDGER_INDEX = 24L;
	public static final String EXPECTED_SIGNING_PUB_KEY = "0296BFE89289E1CA79B0A0C602CBB0207CEC7DF05B4BE142E54354DF0A3002F889";
	public static final String EXPECTED_TRANSACTION_TYPE = "Payment";
	public static final String EXPECTED_TXN_SIGNATURE = "30450221009C8D2ABE902A9C6D4503AC0BDECD9A83657CF64B5F72A69796CF35478049E55F02203A4836E954C55EAAA82403EDDB4DD931FA7D5722A17C66AFD6F76BA7762751EE";
	public static final String EXPECTED_HASH = WITHDRAW_RESPONSE_HASH;

	public static final String EXPECTED_BALANCE = "1000";
	public static final Long EXPECTED_OWNER_COUNT = 0L;

	public static RippleWithdrawResponse getDefaultRippleWithdrawResponse() {

		RippleWithdrawResponse rippleWithdrawResponse = new RippleWithdrawResponse();
		RippleWithdrawResponse.Result.TxJson.Memos.Memo memo = RippleWithdrawResponse.Result.TxJson.Memos.Memo
				.builder().memoData(WITHDRAW_REQUEST_MEMO_DATA).build();

		RippleWithdrawResponse.Result.TxJson.Memos memos = RippleWithdrawResponse.Result.TxJson.Memos.builder()
				.memo(memo).build();

		RippleWithdrawResponse.Result.TxJson txJson = RippleWithdrawResponse.Result.TxJson.builder()
				.account(EXPECTED_ACCOUNT).amount(AMOUNT).destination(EXPECTED_DESTINATION).fee(EXPECTED_FEE)
				.hash(EXPECTED_HASH).sequence(EXPECTED_LEDGER_INDEX).signingPubKey(EXPECTED_SIGNING_PUB_KEY)
				.transactionType(EXPECTED_TRANSACTION_TYPE).txnSignature(EXPECTED_TXN_SIGNATURE)
				.memos(Collections.singletonList(memos)).build();

		RippleWithdrawResponse.Result result = RippleWithdrawResponse.Result.builder()
				.engineResult(EXPECTED_ENGINE_RESULT).engineResultCode(EXPECTED_ENGINE_RESULT_CODE)
				.engineResultMessage(EXPECTED_ENGINE_RESULT_MESSAGE).status(EXPECTED_STATUS).txBlob(EXPECTED_TX_BLOB)
				.txJson(txJson).build();

		result.setTxJson(txJson);
		result.setEngineResult(EXPECTED_ENGINE_RESULT);
		result.setEngineResultCode(EXPECTED_ENGINE_RESULT_CODE);
		result.setEngineResultMessage(EXPECTED_ENGINE_RESULT_MESSAGE);
		result.setStatus(EXPECTED_STATUS);
		result.setTxBlob(EXPECTED_TX_BLOB);

		rippleWithdrawResponse.setResult(result);

		return rippleWithdrawResponse;
	}

	public static RippleAccountInfoRequest getDefaultRippleAccountInfoRequest(
			RippleBlockchainProperties blockchainProperties) {

		return RippleAccountInfoRequest.builder().method(blockchainProperties.getMethod().getAccountInfo())
				.params(Arrays.asList(RippleAccountInfoRequest.Param.builder().account(EXPECTED_ACCOUNT).build())).build();
	}

	public static RippleAccountInfoResponse getDefaultGetAccountInfoResponse() {

		RippleAccountInfoResponse.Result.AccountData accountData = RippleAccountInfoResponse.Result.AccountData.builder()
				.account(EXPECTED_ACCOUNT).balance(EXPECTED_BALANCE).ownerCount(EXPECTED_OWNER_COUNT)
				.sequence(EXPECTED_LEDGER_INDEX).build();

		RippleAccountInfoResponse.Result result = RippleAccountInfoResponse.Result.builder().accountData(accountData).build();

		return RippleAccountInfoResponse.builder().result(result).build();
	}

	public static RippleWithdrawRequest getDefaultSendTransactionRequest(
			RippleBlockchainProperties blockchainProperties) {

		return RippleWithdrawRequest.builder().method(blockchainProperties.getMethod().getSubmit())
				.params(Arrays.asList(RippleWithdrawRequest.Param.builder().txBlob(EXPECTED_TX_BLOB).build())).build();
	}

}
