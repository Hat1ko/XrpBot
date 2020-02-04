package io.cryptex.ms.wrapper.ripple.integration.blockchain.uri;

import io.cryptex.ms.wrapper.ripple.integration.blockchain.properties.RippleBlockchainProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class RippleBlockchainUriBuilder {
	
	private final RippleBlockchainProperties rippleBlockchainProperties;

	public URI getRequestUri() {
		return UriComponentsBuilder.fromUriString(rippleBlockchainProperties.getUri()).build().toUri();
	}
}
