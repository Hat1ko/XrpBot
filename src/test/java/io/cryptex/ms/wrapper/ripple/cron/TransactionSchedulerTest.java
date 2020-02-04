package io.cryptex.ms.wrapper.ripple.cron;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleTransactionsRequest;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleTransactionsResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.properties.RippleBlockchainProperties;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.rest.RippleCommunicationService;
import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.rest.CryptoPaymentCommunicationService;
import io.cryptex.ms.wrapper.ripple.integration.properties.WalletProperties;
import io.cryptex.ms.wrapper.ripple.service.TransactionService;
import io.cryptex.ms.wrapper.ripple.service.TransactionServiceImpl;
import io.cryptex.ms.wrapper.ripple.utils.TransactionSchedulerUtil;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;
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
