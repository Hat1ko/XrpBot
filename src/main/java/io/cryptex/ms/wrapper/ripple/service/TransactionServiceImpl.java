package io.cryptex.ms.wrapper.ripple.service;

import io.cryptex.ms.wrapper.ripple.converter.RippleTransactionConverter;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleAccountInfoRequest;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleAccountInfoRequest.Param;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleTransactionsRequest;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleTransactionsResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleTransactionsResponse.Result.Trx;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.properties.RippleBlockchainProperties;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.rest.RippleCommunicationService;
import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.dto.LastProcessedTransactionData;
import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.rest.CryptoPaymentCommunicationService;
import io.cryptex.ms.wrapper.ripple.integration.properties.WalletProperties;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionServiceImpl implements TransactionService {

    private final RippleCommunicationService rippleCommunicationService;
    private final CryptoPaymentCommunicationService cryptoPaymentCommunicationService;
    private final WalletProperties walletProperties;
    private final RippleBlockchainProperties rippleBlockchainProperties;

    @Override
    public List<TransactionResponse> getNewBlockchainTransactionsToProcess(Long lastProcessedSequence) {
        RippleAccountInfoRequest rippleAccountInfoRequest = RippleAccountInfoRequest.builder()
                .method(rippleBlockchainProperties.getMethod().getAccountInfo())
                .params(Collections.singletonList(Param.builder()
                        .account(walletProperties.getAccount())
                        .build())
                )
                .build();

        Long actualAccountSequence = rippleCommunicationService.getAccountInfo(rippleAccountInfoRequest)
                .getResult().getAccountData().getSequence();

        RippleTransactionsRequest request = RippleTransactionsRequest.builder()
                .method(rippleBlockchainProperties.getMethod().getAccountTransactions())
                .params(Collections
                        .singletonList(RippleTransactionsRequest.Param.builder()
                                .account(walletProperties.getAccount())
                                .limit(actualAccountSequence - lastProcessedSequence - 1)
                                .build())
                )
                .build();

        RippleTransactionsResponse rippleTransactionsResponse;
        try {
            rippleTransactionsResponse = rippleCommunicationService.getTransactions(request);
        } catch (Exception e) {
            log.error("Error while connecting to Ripple node when getting unprocessed transactions | Message : {}",
                    e.getMessage());
            log.debug("Retry request to get new transactions");
            rippleTransactionsResponse = rippleCommunicationService.getTransactions(request);
        }

        List<TransactionResponse> trxToProcess = rippleTransactionsResponse
                .getResult()
                .getTransactions()
                .stream()
                .map(Trx::getTx)
                .map(transaction -> RippleTransactionConverter.toTransactionResponse(transaction, walletProperties))
                .collect(Collectors.toList());

        log.debug("Amount of new transactions to process : {}", trxToProcess.size());

        return trxToProcess;
    }

    @Override
    public LastProcessedTransactionData getLastProcessedTransactionData() {
        return cryptoPaymentCommunicationService.getLastProcessedTransactionData();
    }

    @Override
    public void sendNewTransactionsList(List<TransactionResponse> transactions) {
        cryptoPaymentCommunicationService.sendNewTransactionsToProcess(transactions);
    }

}
