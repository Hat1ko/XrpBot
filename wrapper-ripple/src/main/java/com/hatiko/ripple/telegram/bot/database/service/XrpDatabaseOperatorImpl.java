package com.hatiko.ripple.telegram.bot.database.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.telegram.bot.database.converter.AccountLastIdConverter;
import com.hatiko.ripple.telegram.bot.database.converter.MessageIdConverter;
import com.hatiko.ripple.telegram.bot.database.converter.UserConverter;
import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.model.AccountLastIdEntity;
import com.hatiko.ripple.telegram.bot.database.model.MessageIdEntity;
import com.hatiko.ripple.telegram.bot.database.model.UserEntity;
import com.hatiko.ripple.telegram.bot.database.repo.AccountLastIdRepository;
import com.hatiko.ripple.telegram.bot.database.repo.MessageIdRepository;
import com.hatiko.ripple.telegram.bot.database.repo.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class XrpDatabaseOperatorImpl implements XrpDatabaseOperator {

	private final UserRepository userRepo;
	private final AccountLastIdRepository accountLastIdRepo;
	private final MessageIdRepository messageIdRepo;
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
	public Boolean registerNewUser(String username, String password, String publicKey, String privateKey) {

		UserDTO userDTO = UserDTO.builder().username(username).password(password).publicKey(publicKey)
				.privateKey(privateKey).build();
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

		return Boolean.TRUE;
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
	public AccountLastIdDTO getAccountLastIdByPublicKey(String publicKey) {

		log.info("Gettion account by public key : {}", publicKey);

		return AccountLastIdConverter.toDTO(accountLastIdRepo.findOneByPublicKey(publicKey));
	}

	@Override
	public AccountLastIdDTO updateData(AccountLastIdDTO accountLastIdDTO) {

		AccountLastIdEntity entity = AccountLastIdConverter.toEntity(accountLastIdDTO);

		AccountLastIdEntity searchForId = accountLastIdRepo.findOneByPublicKey(accountLastIdDTO.getPublicKey());

		log.info("Searching for id response | public key : {}, id : {}", searchForId.getPublicKey(),
				searchForId.getId());

		entity.setId(searchForId.getId());

		AccountLastIdEntity response = accountLastIdRepo.save(entity);

		log.info("Updated data | id : {}, publicKey : {}, lastId : {}, lastLedger : {}");

		return AccountLastIdConverter.toDTO(response);
	}

	@Override
	public Boolean deleteAccountLastIdByPublicKey(String publicKey) {

		AccountLastIdEntity entityToDelete = accountLastIdRepo.findOneByPublicKey(publicKey);

		log.info("Deleting account_last_id | public key : {}", publicKey);

		if (Optional.ofNullable(entityToDelete).isEmpty()) {

			log.info("instance doesnt exist | public key : {}", publicKey);
			return Boolean.TRUE;
		}

		accountLastIdRepo.delete(entityToDelete);

		Boolean status = Optional.ofNullable(accountLastIdRepo.findOneByPublicKey(publicKey)).isEmpty();

		log.info("Deletion status | public key : {}, status : {}", publicKey, status);

		return status;
	}

	@Override
	public List<MessageIdDTO> getAllMessageIds() {

		log.info("Getting all messageIds");

		List<MessageIdDTO> response = messageIdRepo.findAll().stream().map(MessageIdConverter::toDTO)
				.collect(Collectors.toList());

		log.info("Gettion all messageIds | found messageIds : {}", response.size());

		return response;
	}

	@Override
	public MessageIdDTO getMessageId(Long chatId) {

		log.info("Getting messageId by chatId = {}", chatId);

		MessageIdEntity entity = messageIdRepo.findById(chatId).orElseGet(MessageIdEntity::new);
		MessageIdDTO dto = MessageIdConverter.toDTO(entity);

		log.info("Getting messageId by chatId | Found chatId : {}, lastSent : {}, lastDeleted : {}", dto.getCahtId(),
				dto.getLastSent(), dto.getLastDeleted());

		return dto;
	}

	@Override
	public MessageIdDTO updateMessageId(Long chatId, Integer lastSent, Integer lastDeleted) {

		log.info("Updating messageId | chatId : {}, lastSent : {}, lastDeleted : {}", chatId, lastSent, lastDeleted);
		
		MessageIdEntity entity = messageIdRepo.findById(chatId).orElseGet(MessageIdEntity::new);

		entity.setChatId(chatId);
		if (Optional.ofNullable(lastSent).isPresent()) {
			entity.setLastSent(lastSent);
		}
		if (Optional.ofNullable(lastDeleted).isPresent()) {
			entity.setLastDeleted(lastDeleted);
		}

		return MessageIdConverter.toDTO(messageIdRepo.save(entity));
	}

	@Override
	public Boolean deleteMessageId(Long chatId) {

		log.info("Deleting messageId | chatId : {}", chatId);
		
		Optional<MessageIdEntity> entity = messageIdRepo.findById(chatId);
		if (entity.isEmpty()) {
			return Boolean.TRUE;
		}

		messageIdRepo.delete(entity.get());
		
		Boolean status = messageIdRepo.findById(chatId).isEmpty();
		log.info("Deleting messageId | Response status : {}", status);
		
		return status;
	}

}
