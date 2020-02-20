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
public class TransactionBody {

	@JsonProperty("Account")
	private String account;

	@JsonProperty("Amount")
	private String amount;

	@JsonProperty("Destination")
	private String destination;

	@JsonProperty("Fee")
	private String fee;

	@JsonProperty("Memos")
	private List<Memos> memos;

	@JsonProperty("Sequence")
	private Long sequence;

	@JsonProperty("TxnSignature")
	private String transactionSignature;

	@JsonProperty("hash")
	private String hash;
	
	@JsonProperty("date")
	private Long date;

	@JsonProperty("ledger_index")
	private Long ledgerIndex;

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
		public static class Memo {

			@JsonProperty("MemoData")
			private String memoData;
		}

	}
}
