
package com.hatiko.ripple.wrapper.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.wrapper.service.RippleService;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/wrapper/transaction")
@RestController
public class TransactionController {

	private final RippleService rippleService;

	@GetMapping
	public ResponseEntity<TransactionResponse> getTransactionByHash(@Valid @NotNull @RequestParam("id") String id) {
		log.info("Request to get transaction by id | id : {}", id);
		TransactionResponse response = rippleService.getTransactionByHash(id);
		log.info("Response from get transaction by id | id in response : {}", response.getTrxId());
		return ResponseEntity.ok(response);
	}
}
