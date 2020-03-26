package com.hatiko.ripple.database.rest.controller.account_last_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.converter.AccountLastIdConverter;
import com.hatiko.ripple.database.dto.AccountLastIdDTO;
import com.hatiko.ripple.database.model.AccountLastIdEntity;
import com.hatiko.ripple.database.repo.AccountLastIdRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "db/account-last-id")
public class GetAccountLastIdController {

	private final AccountLastIdRepository repo;

	@GetMapping
	public ResponseEntity<AccountLastIdDTO> getAccountLastId(@RequestParam("public_key") String publicKey) {

		log.info("GET for info about last ids and ledger | public key : {}", publicKey);

		AccountLastIdEntity entity = repo.findOneByPublicKey(publicKey);

		AccountLastIdDTO dto = AccountLastIdConverter.toDTO(entity);

		log.info("Response for info about last ids and ledger | public key : {}, last id : {}, last ledger : {}",
				dto.getPublicKey(), dto.getLastId(), dto.getLastLedger());

		return ResponseEntity.ok(dto);
	}
}
