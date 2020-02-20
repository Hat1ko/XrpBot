package com.hatiko.ripple.wrapper.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleWithdrawRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.uri.RippleBlockchainUriBuilder;
import com.hatiko.ripple.wrapper.service.SignatureServiceImpl;
import com.hatiko.ripple.wrapper.utils.WithdrawTestUtil;
import com.hatiko.ripple.wrapper.web.model.WithdrawRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class WithdrawControllerTest extends BasicApiTest {

    @Autowired
    private RippleBlockchainUriBuilder rippleBlockchainUriBuilder;

    @MockBean
    private SignatureServiceImpl signatureService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(WithdrawControllerTest.class);
        super.setUp();
    }

    @Test
    public void shouldReturnWithdrawResponseWhenDataIsCorrectThenOk() throws Exception {

        WithdrawRequest withdrawRequest = WithdrawRequest.builder().to(WithdrawTestUtil.WITHDRAW_REQUEST_TO)
                .memo(WithdrawTestUtil.WITHDRAW_REQUEST_MEMO).quantity(WithdrawTestUtil.WITHDRAW_REQUEST_AMOUNT).build();

        RippleWithdrawResponse rippleWithdrawResponse = WithdrawTestUtil.getDefaultRippleWithdrawResponse();

        RippleWithdrawRequest rippleWithdrawRequest = WithdrawTestUtil
                .getDefaultSendTransactionRequest(rippleBlockchainProperties);

        when(signatureService.signTransaction(Mockito.any(String.class), Mockito.any(Double.class), Mockito.any(String.class)))
                .thenReturn(WithdrawTestUtil.EXPECTED_TX_BLOB);

        mockResponseWithBodyAndSuccess(rippleServiceServer, HttpMethod.POST,
                rippleBlockchainUriBuilder.getRequestUri().toString(), rippleWithdrawResponse, rippleWithdrawRequest);

        performPostInteraction(WithdrawTestUtil.WITHDRAW_URL, withdrawRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.from").value(WithdrawTestUtil.EXPECTED_ACCOUNT))
                .andExpect(jsonPath("$.amount").value(WithdrawTestUtil.EXPECTED_AMOUNT))
                .andExpect(jsonPath("$.to").value(WithdrawTestUtil.EXPECTED_DESTINATION))
                .andExpect(jsonPath("$.memo").value(WithdrawTestUtil.EXPECTED_MEMO))
                .andExpect(jsonPath("$.transactionIndex").value(WithdrawTestUtil.EXPECTED_LEDGER_INDEX))
                .andExpect(jsonPath("$.trxId").value(WithdrawTestUtil.EXPECTED_HASH));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenHttpStatusIs4xxThenOk() throws Exception {
        WithdrawRequest withdrawRequest = WithdrawRequest.builder().to(WithdrawTestUtil.WITHDRAW_REQUEST_TO)
                .memo(WithdrawTestUtil.WITHDRAW_REQUEST_MEMO).build();

        RippleWithdrawResponse rippleWithdrawResponse = WithdrawTestUtil.getDefaultRippleWithdrawResponse();

        RippleWithdrawRequest rippleWithdrawRequest = WithdrawTestUtil
                .getDefaultSendTransactionRequest(rippleBlockchainProperties);

        when(signatureService.signTransaction(Mockito.any(String.class), Mockito.any(Double.class), Mockito.any(String.class)))
                .thenReturn(WithdrawTestUtil.EXPECTED_TX_BLOB);

        mockResponseWithBodyAndSuccess(rippleServiceServer, HttpMethod.POST,
                rippleBlockchainUriBuilder.getRequestUri().toString(), rippleWithdrawResponse, rippleWithdrawRequest);

        performPostInteraction(WithdrawTestUtil.WITHDRAW_URL, withdrawRequest)
                .andExpect(status().is4xxClientError());
    }
}
