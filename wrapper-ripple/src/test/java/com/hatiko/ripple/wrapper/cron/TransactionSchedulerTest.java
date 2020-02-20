package com.hatiko.ripple.wrapper.cron;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;
import com.hatiko.ripple.wrapper.integration.blockchain.rest.RippleCommunicationService;
import com.hatiko.ripple.wrapper.integration.cryptopayment.rest.CryptoPaymentCommunicationService;
import com.hatiko.ripple.wrapper.integration.properties.WalletProperties;
import com.hatiko.ripple.wrapper.service.TransactionService;
import com.hatiko.ripple.wrapper.service.TransactionServiceImpl;
import com.hatiko.ripple.wrapper.utils.TransactionSchedulerUtil;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionSchedulerTest {

    private ObjectMapper objectMapper;
    private TransactionService transactionService;
    private RippleCommunicationService rippleCommunicationService;
    private CryptoPaymentCommunicationService cryptoPaymentCommunicationService;
    private WalletProperties walletProperties;
    private RippleBlockchainProperties blockchainProperties;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        rippleCommunicationService = mock(RippleCommunicationService.class, RETURNS_DEEP_STUBS);
        cryptoPaymentCommunicationService = mock(CryptoPaymentCommunicationService.class);
        walletProperties = mock(WalletProperties.class);
        blockchainProperties = mock(RippleBlockchainProperties.class, RETURNS_DEEP_STUBS);

        transactionService = new TransactionServiceImpl(rippleCommunicationService, cryptoPaymentCommunicationService,
                walletProperties, blockchainProperties);
    }

    @Test
    public void shouldReturnExpectedResponseTransactionScheduler() throws IOException {

        RippleTransactionsResponse rippleTransactionsResponse = TransactionSchedulerUtil
                .getDefaultGetTransactionsResponse();

        List<TransactionResponse> expectedTrxList = TransactionSchedulerUtil.getDefaultRippleTransactionDtoList();


        when(rippleCommunicationService.getTransactions(Mockito.any(RippleTransactionsRequest.class)))
                .thenReturn(rippleTransactionsResponse);

        when(transactionService.getLastProcessedTransactionData()).thenReturn(TransactionSchedulerUtil.getDefaultLastProcessedTransactionData());

        when(walletProperties.getAccount()).thenReturn(TransactionSchedulerUtil.EXPECTED_ACCOUNT);
        when(blockchainProperties.getMethod().getAccountInfo()).thenReturn(TransactionSchedulerUtil.ACCOUNT_INFO_METHOD);


        String expectedResponse = objectMapper.writeValueAsString(expectedTrxList);
        String actualResponse = objectMapper.writeValueAsString(transactionService
                .getNewBlockchainTransactionsToProcess(TransactionSchedulerUtil.LAST_PROCESSED_SEQUENCE));

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnGetLastProcessedSequenceWhenExceptionEqualsToExpectedThenOk() {

        when(transactionService.getLastProcessedTransactionData()).thenThrow(new RuntimeException());
        transactionService.getLastProcessedTransactionData();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnGetNewTrxToProcessWhenExceptionEqualsToExpectedThenOk() {

        when(transactionService.getNewBlockchainTransactionsToProcess(Mockito.any())).thenThrow(new RuntimeException());
        transactionService.getNewBlockchainTransactionsToProcess(Mockito.anyLong());
    }
}
