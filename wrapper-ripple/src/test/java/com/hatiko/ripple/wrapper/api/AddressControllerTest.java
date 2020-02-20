package com.hatiko.ripple.wrapper.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.hatiko.ripple.wrapper.integration.properties.WalletProperties;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AddressControllerTest extends BasicApiTest {

	private static final String GET_ADDRESS_URL = "/api/wrapper/address";

	@Autowired
	private WalletProperties walletProperties;

	@Before
	public void setup() {
		super.setUp();
	}

	@Test
	public void shouldReturnAddressResponseWhenDataIsCorrectThenOk() throws Exception {

		performGetInteraction(GET_ADDRESS_URL).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.walletAddress").exists())
				.andExpect(jsonPath("$.walletAddress").isNotEmpty())
				.andExpect(jsonPath("$.walletAddress").value(walletProperties.getAccount()));
	}

}
