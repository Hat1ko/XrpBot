package com.hatiko.ripple.telegram.bot.database.rest.controller.account_last_id;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
public class GetAllAccountLastIdsController {

	private final XrpDatabaseOperator databaseOperator;

	public ResponseEntity<List<AccountLastIdDTO>> getAllAccountLastIds() {
		
		log.info("GET for all account last ids and ledgers");
		
		List<AccountLastIdDTO> response = databaseOperator.getAllAccountLastIds();
		
		log.info("GET Response for all account last ids and ledgers | num of items : {}", response.size());
		
		return ResponseEntity.ok(response);
	}
}
