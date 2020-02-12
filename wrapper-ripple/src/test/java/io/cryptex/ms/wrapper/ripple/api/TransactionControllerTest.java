package io.cryptex.ms.wrapper.ripple.api;

import io.cryptex.ms.wrapper.ripple.integration.blockchain.dto.response.RippleTrxByHashResponse;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.uri.RippleBlockchainUriBuilder;
import io.cryptex.ms.wrapper.ripple.utils.TrxByHashTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class TransactionControllerTest extends BasicApiTest {

	@Autowired
	private RippleBlockchainUriBuilder rippleBlockchainUriBuilder;

	@Before
	public void setup() {
		super.setUp();
	}

	@Test
	public void shouldReturnTrxByHashResponseWhenDataIsCorrectThenOk() throws Exception {

		RippleTrxByHashResponse defaultResponse = TrxByHashTestUtil.getDefaultTrxByHashResponse();

		mockResponseWithSuccess(rippleServiceServer, HttpMethod.POST,
				rippleBlockchainUriBuilder.getRequestUri().toString(), defaultResponse);

		performGetInteraction(TrxByHashTestUtil.GET_TRX_BY_HASH_URL).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.from").value(TrxByHashTestUtil.EXPECTED_ACCOUNT))
				.andExpect(jsonPath("$.amount").value(TrxByHashTestUtil.EXPECTED_AMOUNT))
				.andExpect(jsonPath("$.to").value(TrxByHashTestUtil.EXPECTED_DESTINATION))
				.andExpect(jsonPath("$.memo").value(TrxByHashTestUtil.EXPECTED_MEMO))
				.andExpect(jsonPath("$.transactionIndex").value(TrxByHashTestUtil.EXPECTED_LEDGER_INDEX))
				.andExpect(jsonPath("$.trxId").value(TrxByHashTestUtil.EXPECTED_HASH));
	}
}
