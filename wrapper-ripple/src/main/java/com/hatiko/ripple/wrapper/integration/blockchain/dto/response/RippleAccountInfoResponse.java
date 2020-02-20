package com.hatiko.ripple.wrapper.integration.blockchain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RippleAccountInfoResponse{
	
	@JsonProperty("result")
	private Result result;
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Result{
		
		@JsonProperty("account_data")
		private AccountData accountData;
		
		@Getter
		@Setter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class AccountData{
			
			@JsonProperty("Account")
			private String account;
			
			@JsonProperty("Balance")
			private String balance;
			
			@JsonProperty("OwnerCount")
			private Long ownerCount;
			
			@JsonProperty("Sequence")
			private Long sequence;
		}
	}
}
