package io.cryptex.ms.wrapper.ripple.converter;

import io.cryptex.ms.wrapper.ripple.constants.TransactionType;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleWithdrawResponse.Result.TxJson;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.TransactionBody;
import io.cryptex.ms.wrapper.ripple.integration.properties.WalletProperties;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RippleTransactionConverter {

	public static TransactionResponse toTransactionResponse(TransactionBody transactionBody, WalletProperties walletProperties) {
		return TransactionResponse.builder()
				.transactionType(TransactionType.getTransactionType(transactionBody.getAccount(), walletProperties.getAccount()))
				.from(transactionBody.getAccount())
				.amount(RippleBalanceConverter.toDouble(transactionBody.getAmount()))
				.to(transactionBody.getDestination())
				.memo(transactionBody.getMemos().get(0).getMemo().getMemoData())
				.trxId(transactionBody.getHash())
				.timestamp(transactionBody.getDate())
				.transactionIndex(transactionBody.getSequence())
				.build();
	}

	public static TransactionResponse toTransactionResponse(TxJson txJson, WalletProperties walletProperties) {
		return TransactionResponse.builder()
				.transactionType(TransactionType.getTransactionType(txJson.getAccount(), walletProperties.getAccount()))
				.from(txJson.getAccount())
				.amount(RippleBalanceConverter.toDouble(txJson.getAmount()))
				.to(txJson.getDestination())
				.memo(txJson.getMemos().get(0).getMemo().getMemoData())
				.trxId(txJson.getHash())
				.transactionIndex(txJson.getSequence())
				.build();
	}
}
