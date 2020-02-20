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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RippleTransactionsResponse {

	@JsonProperty("result")
	public Result result;

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Result {

		private String account;

		@JsonProperty("ledger_index_max")
		private Long ledgerIndexMax;

		@JsonProperty("ledger_index_min")
		private Long ledgerIndexMin;
		
		@JsonProperty("status")
		private String status;
		
		@JsonProperty("transactions")
		private List<Trx> transactions;

		@Getter
		@Setter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Trx {
			
			@JsonProperty("tx")
			private TransactionBody tx;
			
			@JsonProperty("validated")
			private Boolean validated;

		}
	}
}
