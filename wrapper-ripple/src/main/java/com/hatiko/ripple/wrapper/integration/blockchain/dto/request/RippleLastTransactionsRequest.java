package com.hatiko.ripple.wrapper.integration.blockchain.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RippleLastTransactionsRequest {

	@JsonProperty("method")
	private String method;
	
	@JsonProperty("params")
	private List<Param> params;

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Param {
		
		@JsonProperty("account")
		private String account;
		
		@JsonProperty("limit")
		private Long limit;
	}
}
