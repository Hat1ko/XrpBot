package com.hatiko.ripple.wrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.wrapper.constants.TransactionType;
import com.hatiko.ripple.wrapper.converter.RippleTransactionConverter;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleLastTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse.Result.Trx;
import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;
import com.hatiko.ripple.wrapper.integration.blockchain.rest.RippleCommunicationService;
import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.integration.cryptopayment.rest.CryptoPaymentCommunicationService;
import com.hatiko.ripple.wrapper.integration.properties.WalletProperties;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

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
    public List<TransactionResponse> getNewBlockchainTransactionsToProcess(Long lastProcessedLedgerIndex) {
        RippleTransactionsRequest request = RippleTransactionsRequest.builder()
                .method(rippleBlockchainProperties.getMethod().getAccountTransactions())
                .params(Collections
                        .singletonList(RippleTransactionsRequest.Param.builder()
                                .account(walletProperties.getAccount())
                                .ledgerIndexMin(lastProcessedLedgerIndex)
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
                .filter(transactionResponse -> transactionResponse.getTransactionType() == TransactionType.DEPOSIT)
                .filter(transactionResponse -> transactionResponse.getTransactionIndex().compareTo(lastProcessedLedgerIndex) > 0)
                .collect(Collectors.collectingAndThen(Collectors.toList(), transactionResponses -> {
                    Collections.reverse(transactionResponses);
                    return transactionResponses;
                }));

        log.debug("Amount of new transactions to process : {}", trxToProcess.size());

        return trxToProcess;
    }

    @Override
    public List<TransactionResponse> getLastTransactions(String walletAddress, Long numOfTransactions) {

        walletProperties.setAccount(walletAddress);
        RippleLastTransactionsRequest request = RippleLastTransactionsRequest.builder()
                .method(rippleBlockchainProperties.getMethod().getAccountTransactions())
                .params(Collections
                        .singletonList(RippleLastTransactionsRequest.Param.builder()
                                .account(walletProperties.getAccount())
                                .limit(numOfTransactions)
                                .build())
                )
                .build();

        RippleTransactionsResponse rippleTransactionsResponse;
        try {
            rippleTransactionsResponse = rippleCommunicationService.getLastTransactions(request);
        } catch (Exception e) {
            log.error("Error while connecting to Ripple node when getting unprocessed transactions | Message : {}",
                    e.getMessage());
            log.debug("Retry request to get new transactions");
            rippleTransactionsResponse = rippleCommunicationService.getLastTransactions(request);
        }

        List<TransactionResponse> trxToProcess = rippleTransactionsResponse
                .getResult()
                .getTransactions()
                .stream()
                .map(Trx::getTx)
                .map(transaction -> RippleTransactionConverter.toTransactionResponse(transaction, walletProperties))
//                .filter(transactionResponse -> transactionResponse.getTransactionType() == TransactionType.DEPOSIT)
//                .filter(transactionResponse -> transactionResponse.getTransactionIndex().compareTo(lastProcessedLedgerIndex) > 0)
                .collect(Collectors.collectingAndThen(Collectors.toList(), transactionResponses -> {
                    Collections.reverse(transactionResponses);
                    return transactionResponses;
                }));

        log.debug("Amount of new transactions to process : {}", trxToProcess.size());

        return trxToProcess;
//    	return null;
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
