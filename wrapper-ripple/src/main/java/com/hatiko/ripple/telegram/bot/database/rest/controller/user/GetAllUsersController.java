package com.hatiko.ripple.telegram.bot.database.rest.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/user")
public class GetAllUsersController {
	
	private final XrpDatabaseOperator xrpDatabaseOperator;
	
//	@GetMapping
//	public ResponseEntity<List<UserDTO>> getAllUsers(){
//		
//		log.info("Request to userDataBaseOperator to get all users");
//		
//		List<UserDTO> response = userDataBaseOperator.getAllUsers();
//		
//		log.info("Reponse | num of users : {}", response.size());
//		
//		return ResponseEntity.ok(response);
//	}
//	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUserByUsername(@Valid @NotNull @RequestParam(name = "username") String username) {

		log.info("Getting user from userDataBaseOperator by username : {}", username);

		List<UserDTO> response;
		
		if(username.isBlank() || username.isEmpty()) {

			response = xrpDatabaseOperator.getAllUsers();
			log.info("Reponse | num of users : {}", response.size());
		}else {
			UserDTO userDTO = xrpDatabaseOperator.getUserByUsername(username);
			
			response = new ArrayList<>();
			response.add(userDTO);
			log.info("Responsing user by username");
			
		}

		return ResponseEntity.ok(response);
	}
}
