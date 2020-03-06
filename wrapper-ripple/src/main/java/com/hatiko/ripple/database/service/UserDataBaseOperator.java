package com.hatiko.ripple.database.service;

import java.util.List;

import com.hatiko.ripple.database.dto.UserDTO;

public interface UserDataBaseOperator {
	
	List<UserDTO> getAllUsers();
	
	UserDTO getUserByUsername(String userName);
	UserDTO registerNewUser(UserDTO userDTO);
	
	Boolean checkLogIn(String username, String password);
	Boolean checkRegistryStatus(String username);
}
