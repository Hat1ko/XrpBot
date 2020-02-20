package com.hatiko.ripple.wrapper.converter;

import org.junit.Test;

import com.hatiko.ripple.wrapper.converter.RippleBalanceConverter;

import static org.assertj.core.api.Assertions.assertThat;

public class RippleBalanceConverterTest {

	private static final Double DOUBLE = 15.0;
	private static final String ATOMIC_UNITS = "15000000";

	@Test
	public void shouldRippleBalanceConvertToDecimalRequestBeEqualsToExpectedResponse() {
		assertThat(RippleBalanceConverter.toDouble(ATOMIC_UNITS)).isEqualTo(DOUBLE);
	}
	
	@Test
	public void shouldRippleBalanceConvertToAtomicUnitsRequestBeEqualsToExpectedResponse() {

		assertThat(RippleBalanceConverter.toAtomicUnits(DOUBLE)).isEqualTo(Long.parseLong(ATOMIC_UNITS));
	}
}
