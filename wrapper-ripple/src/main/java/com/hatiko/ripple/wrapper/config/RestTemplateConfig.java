package com.hatiko.ripple.wrapper.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.hatiko.ripple.wrapper.exception.integration.CryptoPaymentErrorHandler;
import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;
import com.hatiko.ripple.wrapper.integration.cryptopayment.properties.CryptoPaymentProperties;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean(name = "cryptoPaymentRestTemplate")
	public RestTemplate cryptoPaymentRestTemplate(RestTemplateBuilder restTemplateBuilder,
												  CryptoPaymentProperties cryptoPaymentProperties) {
		restTemplateBuilder = restTemplateBuilder.errorHandler(new CryptoPaymentErrorHandler())
				.rootUri(cryptoPaymentProperties.getUri())
				.setConnectTimeout(Duration.ofMillis(cryptoPaymentProperties.getTimeout().getConnect()))
				.setReadTimeout(Duration.ofMillis(cryptoPaymentProperties.getTimeout().getRead()));
		return restTemplateBuilder.build();
	}

	@Bean(name = "rippleBlockchainRestTemplate")
	public RestTemplate rippleBlockchainRestTemplate(RestTemplateBuilder restTemplateBuilder,
			RippleBlockchainProperties rippleBlockchainProperties) {
		return restTemplateBuilder.rootUri(rippleBlockchainProperties.getUri())
				.setConnectTimeout(Duration.ofMillis(rippleBlockchainProperties.getTimeout().getConnect()))
				.setReadTimeout(Duration.ofMillis(rippleBlockchainProperties.getTimeout().getRead()))
				.build();
	}
}
