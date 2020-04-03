package com.hatiko.ripple.telegram.bot.database.rest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.dto.UserDTO;
import com.hatiko.ripple.telegram.bot.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/register")
public class RegisterNewUserController {

	private final XrpDatabaseOperator userDataBaseOperator;

	@PostMapping
	public ResponseEntity<StatusDTO> registerNewUser(@RequestBody UserDTO newUser) {

		log.info("POST Request to register a user");

		Boolean status = userDataBaseOperator.registerNewUser(newUser.getUsername(), newUser.getPassword(),
				newUser.getPublicKey(), newUser.getPrivateKey());

		log.info("POST Response from userDataBaseOperator | status : {}", status);

		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
