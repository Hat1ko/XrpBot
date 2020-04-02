package com.hatiko.ripple.telegram.bot.database.rest.controller.account_last_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/account-last-id")
public class UpdateAccountLastIdController {

	private final XrpDatabaseOperator databaseOperator;

	@PutMapping
	public ResponseEntity<AccountLastIdDTO> updateAccountLastId(@RequestBody AccountLastIdDTO request) {

		log.info("PUT request to update data | public key : {}, last id : {}, last ledger : {}", request.getPublicKey(),
				request.getLastId(), request.getLastLedger());

		AccountLastIdDTO response = databaseOperator.updateData(request);

		log.info("Response to update data | public key : {}, last id : {}, last ledger : {}", response.getPublicKey(),
				response.getLastId(), response.getLastLedger());
		
		return ResponseEntity.ok(response);
	}
}
