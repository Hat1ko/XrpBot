package com.hatiko.ripple.telegram.bot.database.converter;

import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.model.AccountLastIdEntity;

public final class AccountLastIdConverter {

	public static AccountLastIdEntity toEntity(AccountLastIdDTO accountLastIdDTO) {
		return AccountLastIdEntity.builder()
				.publicKey(accountLastIdDTO.getPublicKey())
				.lastId(accountLastIdDTO.getLastId())
				.lastLedger(accountLastIdDTO.getLastLedger())
				.build();
	}

	public static AccountLastIdDTO toDTO(AccountLastIdEntity accountLastIdEntity) {
		return AccountLastIdDTO.builder()
				.publicKey(accountLastIdEntity.getPublicKey())
				.lastId(accountLastIdEntity.getLastId())
				.lastLedger(accountLastIdEntity.getLastLedger())
				.build();
	}
}
