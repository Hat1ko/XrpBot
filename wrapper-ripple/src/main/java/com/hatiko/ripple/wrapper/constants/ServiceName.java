package com.hatiko.ripple.wrapper.constants;

public enum ServiceName {
	CRYPTOPAYMENT("be-cryptopayment");
	
	private String providerName;
	
	ServiceName(String providerName) {
		this.providerName = providerName;
	}
	
	public String getProviderName() {
		return providerName;
	}
}
