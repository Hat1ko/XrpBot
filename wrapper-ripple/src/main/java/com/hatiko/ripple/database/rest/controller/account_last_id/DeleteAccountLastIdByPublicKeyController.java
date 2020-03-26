package com.hatiko.ripple.database.rest.controller.account_last_id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/account-last-id")
public class DeleteAccountLastIdByPublicKeyController {

	private final XrpDatabaseOperator databaseOperator;

	@DeleteMapping
	public ResponseEntity<StatusDTO> deleteAccountLastIdByPublicKey(
			@Valid @NotNull @RequestParam("public_key") String publicKey) {
		
		log.info("DELETE request for AccountLastId | public key : {}", publicKey);
		
		Boolean status = databaseOperator.deleteAccountLastIdByPublicKey(publicKey);

		log.info("Response for delete AccountLastId | public key : {}, status :{}", publicKey, status);
		
		StatusDTO response = StatusDTO.builder().status(status).build();
		
		return ResponseEntity.ok(response);
	}
}
