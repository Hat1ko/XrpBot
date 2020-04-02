package com.hatiko.ripple.telegram.bot.database.converter;

import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.model.UserEntity;

public final class UserConverter {
	
	public static UserDTO toDTO(UserEntity userEntity) {
		return UserDTO.builder().username(userEntity.getUsername()).password(userEntity.getPassword())
				.publicKey(userEntity.getPublicKey()).privateKey(userEntity.getPrivateKey()).build();
	}

	public static UserEntity toEntity(UserDTO userDTO) {
		return UserEntity.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
				.publicKey(userDTO.getPublicKey()).privateKey(userDTO.getPrivateKey()).build();
	}
}
