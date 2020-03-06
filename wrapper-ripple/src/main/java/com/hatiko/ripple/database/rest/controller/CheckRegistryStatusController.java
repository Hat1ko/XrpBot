package com.hatiko.ripple.database.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.database.rest.dto.request.UsernameDTO;
import com.hatiko.ripple.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.database.service.UserDataBaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/db/registry")
public class CheckRegistryStatusController {

	UserDataBaseOperator userDataBaseOperator;
	ObjectMapper objectMapper;
	
	@PostMapping
	public ResponseEntity<StatusDTO> checkRegistryStatus(@RequestBody UsernameDTO usernameDTO){
		
		String username = usernameDTO.getUsername();
		
		log.info("Check registry status for username : {}", usernameDTO);
		
		Boolean status = userDataBaseOperator.checkRegistryStatus(username);

		log.info("Response status from check registry status : {}", status);
		
		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
