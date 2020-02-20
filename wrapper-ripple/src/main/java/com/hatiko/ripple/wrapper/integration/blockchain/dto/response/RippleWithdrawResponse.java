package com.hatiko.ripple.wrapper.integration.blockchain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RippleWithdrawResponse {

	@JsonProperty("result")
	private Result result;
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Result{
		
		@JsonProperty("engine_result")
		private String engineResult;
		
		@JsonProperty("engine_result_code")
		private Long engineResultCode;
		
		@JsonProperty("engine_result_message")
		private String engineResultMessage;
		
		@JsonProperty("status")
		private String status;
		
		@JsonProperty("tx_blob")
		private String txBlob;
		
		@JsonProperty("tx_json")
		private TxJson txJson;
		
		@Getter
		@Setter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class TxJson{
		
			@JsonProperty("Account")
			private String account;
			
			@JsonProperty("Amount")
			private String amount;
			
			@JsonProperty("Destination")
			private String destination;
			
			@JsonProperty("Fee")
			private String fee;
			
			@JsonProperty("Sequence")
			private Long sequence;
			
			@JsonProperty("SigningPubKey")
			private String signingPubKey;
			
			@JsonProperty("TransactionType")
			private String transactionType;
			
			@JsonProperty("TxnSignature")
			private String txnSignature;
			
			@JsonProperty("hash")
			private String hash;
			
			@JsonProperty("Memos")
			private List<Memos> memos;
			
			@Getter
			@Setter
			@Builder
			@AllArgsConstructor
			@NoArgsConstructor(access = AccessLevel.PRIVATE)
			public static class Memos {
				
				@JsonProperty("Memo")
				private Memo memo;
				
				@Getter
				@Setter
				@Builder
				@AllArgsConstructor
				@NoArgsConstructor(access = AccessLevel.PRIVATE)
				public static class Memo{
					
					@JsonProperty("MemoData")
					private String memoData;
				}
			}
		}
	}
}
