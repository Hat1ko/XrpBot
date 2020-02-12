package io.cryptex.ms.wrapper.ripple.utils;

import io.cryptex.ms.wrapper.ripple.web.model.BalanceResponse;

public class BalanceTestUtil {
	public static final String GET_BALANCE_URL = "/api/wrapper/balance";
	
	public static final Double EXPECTED_RIPPLE_BALANCE = 1.1;
	
	public static BalanceResponse getDefaultBalanceResponse() {
		return new BalanceResponse(EXPECTED_RIPPLE_BALANCE);
	}
}
