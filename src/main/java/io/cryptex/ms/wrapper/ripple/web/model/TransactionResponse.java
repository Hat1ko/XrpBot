package io.cryptex.ms.wrapper.ripple.web.model;

import io.cryptex.ms.wrapper.ripple.constants.TransactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionResponse {
	@NotNull
	private TransactionType transactionType;
	@NotNull
	private String from;
	@NotNull
	private Double amount;
	@NotNull
	private String to;
	@NotNull
	private Long timestamp;
	@NotNull
	private Long transactionIndex;
	@NotNull
	private String memo;
	@NotNull
	private String trxId;
}