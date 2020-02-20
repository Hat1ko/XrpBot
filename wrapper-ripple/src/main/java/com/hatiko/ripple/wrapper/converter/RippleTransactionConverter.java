package com.hatiko.ripple.wrapper.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import com.hatiko.ripple.wrapper.constants.TransactionType;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.TransactionBody;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse.Result.TxJson;
import com.hatiko.ripple.wrapper.integration.properties.WalletProperties;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RippleTransactionConverter {

    public static TransactionResponse toTransactionResponse(TransactionBody transactionBody, WalletProperties walletProperties) {
        String memo = Optional.ofNullable(transactionBody.getMemos())
                .map(memos -> memos.get(0).getMemo())
                .map(memo1 -> decodeMemo(memo1.getMemoData()))
                .orElse("");
        return TransactionResponse.builder()
                .transactionType(TransactionType.getTransactionType(transactionBody.getAccount(), walletProperties.getAccount()))
                .from(transactionBody.getAccount())
                .amount(RippleBalanceConverter.toDouble(transactionBody.getAmount()))
                .to(transactionBody.getDestination())
                .memo(memo)
                .trxId(transactionBody.getHash())
                .timestamp(transactionBody.getDate())
                .transactionIndex(transactionBody.getLedgerIndex())
                .build();
    }

    public static TransactionResponse toTransactionResponse(TxJson txJson, WalletProperties walletProperties) {
        String memo = decodeMemo(txJson.getMemos().get(0).getMemo().getMemoData());
        return TransactionResponse.builder()
                .transactionType(TransactionType.getTransactionType(txJson.getAccount(), walletProperties.getAccount()))
                .from(txJson.getAccount())
                .amount(RippleBalanceConverter.toDouble(txJson.getAmount()))
                .to(txJson.getDestination())
                .memo(memo)
                .trxId(txJson.getHash())
                .transactionIndex(txJson.getSequence())
                .build();
    }

    private static String decodeMemo(String encodedMemo) {
        try {
            return new String(Hex.decodeHex(encodedMemo.toCharArray()), UTF_8);
        } catch (Exception e) {
            log.error("Error while on decode memo. Memo = {}. Error = {}", encodedMemo, e.getMessage());
            return "";
        }
    }

}
