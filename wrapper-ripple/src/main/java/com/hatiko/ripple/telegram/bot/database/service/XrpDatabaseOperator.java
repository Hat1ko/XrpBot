package com.hatiko.ripple.telegram.bot.database.service;

import java.util.List;

import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;

public interface XrpDatabaseOperator {
	
	List<UserDTO> getAllUsers();
	
	UserDTO getUserByUsername(String userName);
	Boolean registerNewUser(String username, String password, String publicKey, String privateKey);
	
	Boolean checkLogIn(String username, String password);
	Boolean checkRegistryStatus(String username);
	Boolean deleteUserByUsername(String username, String password);
	
	List<AccountLastIdDTO> getAllAccountLastIds();
	
	AccountLastIdDTO getAccountLastIdbyPublicKey(String publicKey);
	AccountLastIdDTO updateData(AccountLastIdDTO accountLastIdDTO);
	
	Boolean deleteAccountLastIdByPublicKey(String publicKey);
	
	List<MessageIdDTO> getAllMessageIds();
	MessageIdDTO getMessageId(Long chatId);
	MessageIdDTO updateMessageId(Long chatId, Integer lastSent, Integer lastDeleted);
	Boolean deleteMessageId(Long chatId);

}
