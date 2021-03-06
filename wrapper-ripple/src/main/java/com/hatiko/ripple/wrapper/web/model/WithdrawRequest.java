package com.hatiko.ripple.wrapper.web.model;

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
public class WithdrawRequest {
	@NotNull
	private String to;
	@NotNull
	private Double quantity;
	@NotNull
	private String memo;
}
