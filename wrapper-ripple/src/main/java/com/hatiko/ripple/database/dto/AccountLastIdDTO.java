package com.hatiko.ripple.database.dto;

import lombok.NoArgsConstructor;

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
