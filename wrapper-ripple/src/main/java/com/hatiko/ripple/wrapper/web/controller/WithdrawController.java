package com.hatiko.ripple.wrapper.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.wrapper.service.RippleService;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;
import com.hatiko.ripple.wrapper.web.model.WithdrawRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/withdraw")
@RestController
public class WithdrawController {

	private final RippleService rippleService;

	@PostMapping
	public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest withdrawRequest) {
		log.info("Request to withdraw | to : {}, amount : {}, memo : {}", withdrawRequest.getTo(),
				withdrawRequest.getQuantity(), withdrawRequest.getMemo());
		TransactionResponse response = rippleService.withdraw(withdrawRequest);
		log.info("Response from withdraw | trxId : {}", response.getTrxId());
		return ResponseEntity.ok(response);
	}
}
