package com.hatiko.ripple.wrapper.web.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import com.hatiko.ripple.wrapper.constants.TransactionType;

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