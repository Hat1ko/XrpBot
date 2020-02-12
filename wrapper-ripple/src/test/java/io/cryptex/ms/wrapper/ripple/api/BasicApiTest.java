package io.cryptex.ms.wrapper.ripple.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cryptex.ms.wrapper.ripple.integration.blockchain.properties.RippleBlockchainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public abstract class BasicApiTest {

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected RestTemplate cryptoPaymentRestTemplate;

	@Autowired
	protected RestTemplate rippleBlockchainRestTemplate;

	@Autowired
	protected RippleBlockchainProperties rippleBlockchainProperties;
	
	protected MockMvc mockMvc;

	protected MockRestServiceServer rippleServiceServer;
	protected MockRestServiceServer cryptoPaymentServiceServer;

	protected void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.cryptoPaymentServiceServer = MockRestServiceServer.bindTo(cryptoPaymentRestTemplate).build();
		this.rippleServiceServer = MockRestServiceServer.bindTo(rippleBlockchainRestTemplate).build();
	}

	protected ResultActions performGetInteraction(String uri) throws Exception {

		return mockMvc
				.perform(get(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE));
	}

	protected ResultActions performPostInteraction(String uri, Object dataRequest) throws Exception {

		return mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dataRequest)).accept(MediaType.APPLICATION_JSON_VALUE));
	}

	protected void mockPostResponseWithHttpStatusOk(MockRestServiceServer mockRestServiceServer, String uri) {

		mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri)).andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK));
	}

	protected void mockResponseWithSuccess(MockRestServiceServer mockRestServiceServer, HttpMethod httpMethod,
			String uri, Object dataResponse) throws Exception {

		String stringifiedDataResponse = objectMapper.writeValueAsString(dataResponse);

		mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri)).andExpect(method(httpMethod))
				.andRespond(withSuccess(stringifiedDataResponse, MediaType.APPLICATION_JSON));

	}

	protected void mockResponseWithBodyAndSuccess(MockRestServiceServer mockRestServiceServer, HttpMethod httpMethod,
			String uri, Object dataResponse, Object requestBody) throws Exception {

		String stringifiedDataResponse = objectMapper.writeValueAsString(dataResponse);
		String stringifiedRequestBody = objectMapper.writeValueAsString(requestBody);

		mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri))
				.andExpect(content().string(stringifiedRequestBody))
				.andExpect(method(httpMethod))
				.andRespond(withSuccess(stringifiedDataResponse, MediaType.APPLICATION_JSON));

	}

}
