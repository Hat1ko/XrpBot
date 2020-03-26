package com.hatiko.ripple.database.rest.controller.user;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	private final UserDataBaseOperator userDataBaseOperator;
	
	@PostMapping
	public ResponseEntity<StatusDTO> checkRegistryStatus(@RequestBody UsernameDTO usernameDTO){
		
		String username = Optional.ofNullable(usernameDTO).orElseGet(null).getUsername();
		
		log.info("Check registry status for username");
		
		Boolean status = userDataBaseOperator.checkRegistryStatus(username);

		log.info("Response status from check registry status : {}", status);
		
		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
