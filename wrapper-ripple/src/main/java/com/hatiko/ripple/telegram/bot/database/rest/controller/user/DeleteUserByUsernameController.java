package com.hatiko.ripple.telegram.bot.database.rest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.rest.dto.request.LogInDTO;
import com.hatiko.ripple.telegram.bot.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "db/user")
public class DeleteUserByUsernameController {

	private final XrpDatabaseOperator userDataBaseOperator;
	
	@DeleteMapping
	public ResponseEntity<StatusDTO> deleteUserByUsername(@RequestBody LogInDTO logInDTO) {
		
		String username = logInDTO.getUsername();
		String password = logInDTO.getPassword();
		
		log.info("DELETE Request to delete user | Username : ", username);
		
		Boolean status = userDataBaseOperator.deleteUserByUsername(username, password);
		
		log.info("DELETE Response status of deleting user : {} | username : {}", status, username);
		
		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
