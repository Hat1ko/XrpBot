package io.cryptex.ms.wrapper.ripple.api;

import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleAccountInfoRequest;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.request.RippleWithdrawRequest;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleAccountInfoResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleWithdrawResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.uri.RippleBlockchainUriBuilder;
import io.cryptex.ms.wrapper.ripple.service.SignatureServiceImpl;
import io.cryptex.ms.wrapper.ripple.utils.WithdrawTestUtil;
import io.cryptex.ms.wrapper.ripple.web.model.WithdrawRequest;
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

		RippleAccountInfoRequest rippleAccountInfoRequest = WithdrawTestUtil
				.getDefaultRippleAccountInfoRequest(rippleBlockchainProperties);

		RippleWithdrawRequest rippleWithdrawRequest = WithdrawTestUtil
				.getDefaultSendTransactionRequest(rippleBlockchainProperties);

		RippleAccountInfoResponse rippleAccountInfoResponse = WithdrawTestUtil.getDefaultGetAccountInfoResponse();

		when(signatureService.signTransaction(Mockito.any(String.class), Mockito.any(Long.class), Mockito.any(String.class),
				Mockito.any(String.class), Mockito.any(Long.class))).thenReturn(WithdrawTestUtil.EXPECTED_TX_BLOB);

		mockResponseWithBodyAndSuccess(rippleServiceServer, HttpMethod.POST,
				rippleBlockchainUriBuilder.getRequestUri().toString(), rippleAccountInfoResponse,
				rippleAccountInfoRequest);

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
	public void shouldThrowRuntimeExceptionWhenHttpStatusIs5xxThenOk() throws Exception {
		WithdrawRequest withdrawRequest = WithdrawRequest.builder().to(WithdrawTestUtil.WITHDRAW_REQUEST_TO)
				.memo(WithdrawTestUtil.WITHDRAW_REQUEST_MEMO).build();

		RippleWithdrawResponse rippleWithdrawResponse = WithdrawTestUtil.getDefaultRippleWithdrawResponse();

		RippleAccountInfoRequest rippleAccountInfoRequest = WithdrawTestUtil
				.getDefaultRippleAccountInfoRequest(rippleBlockchainProperties);

		RippleWithdrawRequest rippleWithdrawRequest = WithdrawTestUtil
				.getDefaultSendTransactionRequest(rippleBlockchainProperties);

		RippleAccountInfoResponse rippleAccountInfoResponse = WithdrawTestUtil.getDefaultGetAccountInfoResponse();

		when(signatureService.signTransaction(Mockito.any(String.class), Mockito.any(Long.class), Mockito.any(String.class),
				Mockito.any(String.class), Mockito.any(Long.class))).thenReturn(WithdrawTestUtil.EXPECTED_TX_BLOB);
		
		mockResponseWithBodyAndSuccess(rippleServiceServer, HttpMethod.POST,
				rippleBlockchainUriBuilder.getRequestUri().toString(), rippleAccountInfoResponse,
				rippleAccountInfoRequest);

		mockResponseWithBodyAndSuccess(rippleServiceServer, HttpMethod.POST,
				rippleBlockchainUriBuilder.getRequestUri().toString(), rippleWithdrawResponse, rippleWithdrawRequest);

		performPostInteraction(WithdrawTestUtil.WITHDRAW_URL, withdrawRequest)
				.andExpect(status().is5xxServerError());
	}
}
