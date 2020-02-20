package com.hatiko.ripple.wrapper.integration.blockchain.uri;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class RippleBlockchainUriBuilder {
	
	private final RippleBlockchainProperties rippleBlockchainProperties;

	public URI getRequestUri() {
		return UriComponentsBuilder.fromUriString(rippleBlockchainProperties.getUri()).build().toUri();
	}
}
