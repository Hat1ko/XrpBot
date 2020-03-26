package com.hatiko.ripple.database.rest.controller.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/db/user")
public class GetUserByUsernameController {

	private final XrpDatabaseOperator userDataBaseOperator;

	@GetMapping
	public ResponseEntity<UserDTO> getUserByUsername(@Valid @NotNull @RequestParam("username") String username) {

		log.info("Getting user from userDataBaseOperator by username : {}", username);

		UserDTO response = userDataBaseOperator.getUserByUsername(username);

		log.info("Responsing user by username");

		return ResponseEntity.ok(response);
	}
}
