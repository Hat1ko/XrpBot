package io.cryptex.ms.wrapper.ripple.integration.blockchain.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@RequiredArgsConstructor
@ConfigurationProperties("blockchain")
@Component
public class RippleBlockchainProperties {

	private String nodeList;
	private final Random random = new Random();
	
	private String uri;
	private String path;
	private Method method;
	private Timeout timeout;
	private TransactionType transactionType;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@ConfigurationProperties("method")
	public static class Method{
		
		private String accountInfo;
		private String accountTransactions;
		private String submit;
		private String transaction;
		private String sign;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@ConfigurationProperties("timeout")
	public static class Timeout{
		private Long connect;
		private Long read;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@ConfigurationProperties("transaction-type")
	public static class TransactionType{
		private String payment;
		private String accountSet;
		
	}
	
	public String getUri() {
		List<String> nodes = Arrays.asList(nodeList.split(","));
		Integer randomIndex = random.nextInt(nodes.size());
		return nodes.get(randomIndex);
	}

}
