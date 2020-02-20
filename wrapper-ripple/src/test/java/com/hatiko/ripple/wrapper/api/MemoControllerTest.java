package com.hatiko.ripple.wrapper.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class MemoControllerTest extends BasicApiTest {

	private static final String GET_MEMO_URL = "/api/wrapper/memo";

	@Before
	public void setup() {
		super.setUp();
	}

	@Test
	public void shouldReturnMemoResponseWhenDataIsCorrectThenOk() throws Exception {

		performGetInteraction(GET_MEMO_URL)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.walletMemo").exists())
				.andExpect(jsonPath("$.walletMemo").isNotEmpty());
	}

}
