package io.cryptex.ms.wrapper.ripple.integration.cryptopayment.uri;

import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.properties.CryptoPaymentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class CryptoPaymentUriBuilder {

	private final CryptoPaymentProperties cryptoPaymentProperties;

	public URI getLastProcessedDataUri() {
		return UriComponentsBuilder
				.fromUriString(cryptoPaymentProperties.getPath().getTransaction().getRipple().getProcessedData())
				.build().encode().toUri();
	}

	public URI getTransactionNewListUri() {
		return UriComponentsBuilder
				.fromUriString(cryptoPaymentProperties.getPath().getTransaction().getRipple().getNewList())
				.build().encode().toUri();
	}

}
