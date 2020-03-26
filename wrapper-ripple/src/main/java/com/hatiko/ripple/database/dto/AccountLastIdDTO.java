package com.hatiko.ripple.database.dto;

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
public class AccountLastIdDTO {
	
	private String publicKey;
	private String lastId;
	private String lastLedger;
}
