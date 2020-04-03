package com.hatiko.ripple.telegram.bot.database.rest.controller.account_last_id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "db/account-last-id")
public class GetAccountLastIdByPublicKeyController {

	private final XrpDatabaseOperator databaseOperator;

	@GetMapping
	public ResponseEntity<AccountLastIdDTO> getAccountLastIdByPublicKey(@Valid @NotNull @RequestParam("public_key") String publicKey) {

		log.info("GET for info about last ids and ledger | public key : {}", publicKey);

		AccountLastIdDTO dto = databaseOperator.getAccountLastIdbyPublicKey(publicKey);

		log.info("GET Response for info about last ids and ledger | public key : {}, last id : {}, last ledger : {}",
				dto.getPublicKey(), dto.getLastId(), dto.getLastLedger());

		return ResponseEntity.ok(dto);
	}
}
