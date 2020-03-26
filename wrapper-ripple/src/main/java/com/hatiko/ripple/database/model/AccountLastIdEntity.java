package com.hatiko.ripple.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity(name = "account_last_id")
public class AccountLastIdEntity {
	
	@Id 
	private Integer id;
	
	@Column(name = "public_key",length = 64, unique = true)
	private String publicKey;
	
	@Column(name = "last_id")
	private Integer lastId;
	
	@Column(name = "last_ledger")
	private Integer lastLedger;
}
