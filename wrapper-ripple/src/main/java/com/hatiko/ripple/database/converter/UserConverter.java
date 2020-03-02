package com.hatiko.ripple.database.converter;

import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.dto.UserEntity;

public final class UserConverter {
	
	public static UserDTO toUserDTO(UserEntity userEntity) {
		return UserDTO.builder().username(userEntity.getUsername()).password(userEntity.getPassword())
				.publicKey(userEntity.getPublicKey()).privateKey(userEntity.getPrivateKey()).build();
	}

	public static UserEntity toUserEntity(UserDTO userDTO) {
		return UserEntity.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
				.publicKey(userDTO.getPublicKey()).privateKey(userDTO.getPrivateKey()).build();
	}
}
