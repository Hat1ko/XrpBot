package com.hatiko.ripple.database.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.rest.dto.request.UsernameDTO;
import com.hatiko.ripple.database.service.UserDataBaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/db/user")
public class GetUserByUsernameController {

	UserDataBaseOperator userDataBaseOperator;
	ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<UserDTO> getUserByUsername(@RequestBody UsernameDTO usernameDTO) {

		String username = usernameDTO.getUsername();

		log.info("Getting user from userDataBaseOperator by username : {}", username);

		UserDTO response = userDataBaseOperator.getUserByUsername(username);

		log.info("Response from userDataBaseOperator : {}", objectMapper.convertValue(response, String.class));

		return ResponseEntity.ok(response);
	}
}
