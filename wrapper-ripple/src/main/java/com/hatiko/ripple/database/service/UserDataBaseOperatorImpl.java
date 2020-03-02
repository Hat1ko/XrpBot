package com.hatiko.ripple.database.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.database.converter.UserConverter;
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
	ObjectMapper objectMapper;

	@Override
	public List<UserDTO> getAllUsers() {

		log.info("Getting all userDTO from db");

		return userRepo.findAll().stream().map(e -> UserConverter.toUserDTO(e)).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserByUsername(String username) {

		log.info("Getting userEntity by username | username : {}", username);

		Optional<UserEntity> entity = userRepo.findOneByUsername(username);

		log.info("Transform of userEntity DTO userDTO");

		if (entity.isEmpty()) {
			return new UserDTO();
		}

		UserEntity userEntity = entity.get();

		return UserConverter.toUserDTO(userEntity);

//		return UserDTO.builder().username(userEntity.getUsername()).password(userEntity.getPassword())
//				.publicKey(userEntity.getPublicKey()).privateKey(userEntity.getPrivateKey()).build();
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {

		UserEntity userEntity = UserConverter.toUserEntity(userDTO);
//				UserEntity.builder().username(userDTO.getUsername()).password(userDTO.getPassword())
//				.publicKey(userDTO.getPublicKey()).privateKey(userDTO.getPrivateKey()).build();

		log.info("Saving user to db : {}", objectMapper.convertValue(userEntity, String.class));

		UserEntity entityResponse = userRepo.save(userEntity);

		log.info("Response entity from db after saveing : {}", objectMapper.convertValue(entityResponse, String.class));

		UserDTO response = UserConverter.toUserDTO(entityResponse);
//				UserDTO.builder().username(entityResponse.getUsername())
//				.password(entityResponse.getPassword()).publicKey(entityResponse.getPublicKey())
//				.privateKey(entityResponse.getPrivateKey()).build();

		return response;
	}

	@Override
	public Boolean checkLogIn(String username, String password) {

		Optional<UserEntity> userEntity = userRepo.findOneByUsername(username);

		log.info("Check for log in | username : {}, password : {}");
		
		if (userEntity.isPresent()) {
			return userEntity.get().getPassword().contentEquals(password);
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Boolean checkRegistryStatus(String username) {

		List<UserEntity> users = userRepo.findAllLikeUsername(username);
		
		log.info("Check for user being registered by username = {}", username);
		
		users = users.stream().filter(e -> e.getUsername().equals(username)).collect(Collectors.toList());
		
		Boolean response = users.size() > 0;
		
		log.info("Registry state : {}", response);
		
		return response;
	}

}
