package com.hatiko.ripple.wrapper.integration.cryptopayment.uri;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.hatiko.ripple.wrapper.integration.cryptopayment.properties.CryptoPaymentProperties;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class CryptoPaymentUriBuilder {

	private final CryptoPaymentProperties cryptoPaymentProperties;

	public URI getLastProcessedDataUri() {
		return UriComponentsBuilder.fromUriString(cryptoPaymentProperties.getUri())
				.path(cryptoPaymentProperties.getPath().getTransaction().getRipple().getProcessedData())
				.build()
				.encode()
				.toUri();
	}

	public URI getTransactionNewListUri() {
		return UriComponentsBuilder.fromUriString(cryptoPaymentProperties.getUri())
				.path(cryptoPaymentProperties.getPath().getTransaction().getRipple().getNewList())
				.build()
				.encode()
				.toUri();
	}

}
