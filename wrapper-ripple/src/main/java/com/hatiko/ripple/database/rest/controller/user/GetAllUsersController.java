package com.hatiko.ripple.database.rest.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.service.UserDataBaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/db/user")
public class GetAllUsersController {
	
	private final UserDataBaseOperator userDataBaseOperator;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		
		log.info("Request to userDataBaseOperator to get all users");
		
		List<UserDTO> response = userDataBaseOperator.getAllUsers();
		
		log.info("Reponse | num of users : {}", response.size());
		
		return ResponseEntity.ok(response);
	}
	
}
