package com.hatiko.ripple.telegram.bot.database.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.telegram.bot.database.converter.AccountLastIdConverter;
import com.hatiko.ripple.telegram.bot.database.converter.UserConverter;
import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.model.AccountLastIdEntity;
import com.hatiko.ripple.telegram.bot.database.model.UserEntity;
import com.hatiko.ripple.telegram.bot.database.repo.AccountLastIdRepository;
import com.hatiko.ripple.telegram.bot.database.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class XrpDatabaseOperatorImpl implements XrpDatabaseOperator {

	private final UserRepo userRepo;
	private final AccountLastIdRepository accountLastIdRepo;
	private final ObjectMapper objectMapper;

	@Override
	public List<UserDTO> getAllUsers() {

		log.info("Getting all userDTO from db");

		return userRepo.findAll().stream().map(UserConverter::toDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserByUsername(String username) {

		log.info("Getting userEntity by username | username : {}", username);

		UserEntity entity = userRepo.findOneByUsername(username);

		try {
			log.info("Response from db : {}", objectMapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		log.info("Transform of userEntity DTO userDTO");

		UserEntity userEntity = Optional.ofNullable(entity).orElseGet(UserEntity::new);

		return UserConverter.toDTO(userEntity);
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {

		UserEntity userEntity = UserConverter.toEntity(userDTO);

		try {
			log.info("Saving user to db : {}", objectMapper.writeValueAsString(userEntity));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		UserEntity entityResponse = userRepo.save(userEntity);

		try {
			log.info("Response entity from db after saveing : {}", objectMapper.writeValueAsString(entityResponse));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		UserDTO response = UserConverter.toDTO(entityResponse);

		return response;
	}

	@Override
	public Boolean checkLogIn(String username, String password) {

		if (Optional.ofNullable(username).isEmpty() || Optional.ofNullable(password).isEmpty()) {
			return Boolean.FALSE;
		}

		UserEntity userEntity = userRepo.findOneByUsername(username);

		log.info("Check for log in | username : {}, password : {}", username, password);

		Boolean status = Optional.ofNullable(userEntity).filter(e -> e.getPassword().equals(password)).isPresent();

		log.info("Login status : {}", status);

		return status;
	}

	@Override
	public Boolean checkRegistryStatus(String username) {

		List<UserEntity> users = userRepo.findAllByUsername(username);

		log.info("Check for user being registered by username = {}", username);

		users = users.stream().filter(e -> e.getUsername().equals(username)).collect(Collectors.toList());

		Boolean response = users.size() > 0;

		log.info("Registry state : {}", response);

		return response;
	}

	@Override
	public Boolean deleteUserByUsername(String username, String password) {

		if (!checkLogIn(username, password)) {
			return Boolean.FALSE;
		}

		UserEntity userToDelete = Optional.ofNullable(userRepo.findOneByUsername(username)).orElseGet(UserEntity::new);

		log.info("Checking if the user exists");

		if (userToDelete.getUsername() == null) {
			return Boolean.FALSE;
		}

		log.info("Deleting user with username : {}", username);

		userRepo.delete(userToDelete);

		return Boolean.TRUE;
	}

	@Override
	public List<AccountLastIdDTO> getAllAccountLastIds() {

		log.info("Getting all account_last_id instances");
		
		return accountLastIdRepo.findAll().stream().map(AccountLastIdConverter::toDTO).collect(Collectors.toList());
	}

	@Override
	public AccountLastIdDTO getAccountLastIdbyPublicKey(String publicKey) {

		log.info("Gettion account by public key : {}", publicKey);
		
		return AccountLastIdConverter.toDTO(accountLastIdRepo.findOneByPublicKey(publicKey));
	}

	@Override
	public AccountLastIdDTO updateData(AccountLastIdDTO accountLastIdDTO) {

		AccountLastIdEntity entity = AccountLastIdConverter.toEntity(accountLastIdDTO);
		
		AccountLastIdEntity searchForId = accountLastIdRepo.findOneByPublicKey(accountLastIdDTO.getPublicKey());

		log.info("Searching for id response | public key : {}, id : {}", searchForId.getPublicKey(), searchForId.getId());
		
		entity.setId(searchForId.getId());
		
		AccountLastIdEntity response = accountLastIdRepo.save(entity);
		
		return AccountLastIdConverter.toDTO(response);
	}

	@Override
	public Boolean deleteAccountLastIdByPublicKey(String publicKey) {
		
		AccountLastIdEntity entityToDelete = accountLastIdRepo.findOneByPublicKey(publicKey);
		
		log.info("Deleting account_last_id | public key : {}", publicKey);
		
		if(Optional.ofNullable(entityToDelete).isEmpty()) {
			
			log.info("instance doesnt exist | public key : {}", publicKey);
			return Boolean.TRUE;
		}
		
		accountLastIdRepo.delete(entityToDelete);
		
		Boolean status = Optional.ofNullable(accountLastIdRepo.findOneByPublicKey(publicKey)).isEmpty();
		
		log.info("Deletion status | public key : {}, status : {}", publicKey, status);
		
		return status;
	}
}
