package com.hatiko.ripple.telegram.bot.database.rest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.rest.dto.request.LogInDTO;
import com.hatiko.ripple.telegram.bot.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("db/login")
public class CheckLogInController {

	private final XrpDatabaseOperator userDataBaseOperator;

	@PostMapping
	public ResponseEntity<StatusDTO> CheckLogIn(@RequestBody LogInDTO logInDTO) {

		String username = logInDTO.getUsername();
		String password = logInDTO.getPassword();

		log.info("Request to check log in | username : {}, password : {}", username, password);

		Boolean status = userDataBaseOperator.checkLogIn(username, password);

		log.info("Response status from check log in : {}", status);

		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
