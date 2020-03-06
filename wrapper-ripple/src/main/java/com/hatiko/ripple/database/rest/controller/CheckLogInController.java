package com.hatiko.ripple.database.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.rest.dto.request.LogInDTO;
import com.hatiko.ripple.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.database.service.UserDataBaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/db/login")
public class CheckLogInController {

	private final UserDataBaseOperator userDataBaseOperator;

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
