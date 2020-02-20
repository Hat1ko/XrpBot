package com.hatiko.ripple.wrapper.utils;

import com.hatiko.ripple.wrapper.web.model.BalanceResponse;

public class BalanceTestUtil {
	public static final String GET_BALANCE_URL = "/api/wrapper/balance";
	
	public static final Double EXPECTED_RIPPLE_BALANCE = 1.1;
	
	public static BalanceResponse getDefaultBalanceResponse() {
		return new BalanceResponse(EXPECTED_RIPPLE_BALANCE);
	}
}
