package io.cryptex.ms.wrapper.ripple.integration.cryptopayment.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("ms.crypto.payment")
@Component
public class CryptoPaymentProperties {
	
	private String uri;
	private Path path;
	private Timeout timeout;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@ConfigurationProperties("path")
	public static class Path{
		
		private Transaction transaction;
		
		@Getter
		@Setter
		@NoArgsConstructor
		@ConfigurationProperties("transaction")
		public static class Transaction{
			
			private Ripple ripple;
			
			@Getter
			@Setter
			@NoArgsConstructor
			@ConfigurationProperties("ripple")
			public static class Ripple{
				
				private String processedSequence;
				private String newList;
				
			}
		}
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@ConfigurationProperties("path")
	public static class Timeout{
		private Integer connect;
		private Integer read;
	}
}
