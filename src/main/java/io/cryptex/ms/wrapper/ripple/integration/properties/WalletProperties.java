package io.cryptex.ms.wrapper.ripple.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("wrapper.ripple.wallet")
@Component
public class WalletProperties {
	private String uri;
	private String account;
	private String secretKey;
	private String fee;
	private Integer memoLength;
}
