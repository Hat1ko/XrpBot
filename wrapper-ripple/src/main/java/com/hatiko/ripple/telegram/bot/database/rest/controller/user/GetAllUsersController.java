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
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUserByUsername(@Valid @NotNull @RequestParam(name = "username", required = false) String username) {

		log.info("GET Request | Getting user from userDataBaseOperator by username : {}", username);

		List<UserDTO> response;
		
		if(username.isBlank() || username.isEmpty()) {

			response = xrpDatabaseOperator.getAllUsers();
			log.info("GET Reponse | num of users : {}", response.size());
		}else {
			UserDTO userDTO = xrpDatabaseOperator.getUserByUsername(username);
			
			response = new ArrayList<>();
			response.add(userDTO);
			log.info("GET Response user by username");
			
		}

		return ResponseEntity.ok(response);
	}
}
