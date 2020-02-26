package com.hatiko.ripple.database.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.dto.UserEntity;
import com.hatiko.ripple.database.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataBaseOperatorImpl implements UserDataBaseOperator {

	UserDataBaseOperator userDataBaseOperator;
	UserRepo userRepo;

	@Override
	public List<UserDTO> getAllUsers() {

	log.info("Getting all userDTO from db");	
		
		return userRepo.findAll().stream()
				.map(e -> UserDTO.builder().username(e.getUsername()).password(e.getPassword())
						.publicKey(e.getPublicKey()).privateKey(e.getPrivateKey()).build())
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserByUsername(String username) {

		log.info("Getting userEntity by username | username : {}", username);

		UserEntity userEntity = userRepo.findOneByUsername(username);
		
		log.info("Transform of userEntity DTO userDTO");
		
		return UserDTO.builder().username(userEntity.getUsername()).password(userEntity.getPassword())
				.publicKey(userEntity.getPublicKey()).privateKey(userEntity.getPrivateKey()).build();
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {

		UserEntity userEntity = UserEntity.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
				.publicKey(userDTO.getPublicKey()).privateKey(userDTO.getPrivateKey()).build();

		UserEntity entityResponse = userRepo.save(userEntity);

		UserDTO response = UserDTO.builder().username(entityResponse.getUsername())
				.password(entityResponse.getPassword()).publicKey(entityResponse.getPublicKey())
				.privateKey(entityResponse.getPrivateKey()).build();
		
		return response;
	}

	@Override
	public Boolean checkLogIn(String username, String password) {
		
		UserEntity userEntity = userRepo.findOneByUsername(username);
		
		return userEntity.getPassword().contentEquals(password);
	}

	@Override
	public Boolean checkRegistryStatus(String username) {

		return null;
	}

}
