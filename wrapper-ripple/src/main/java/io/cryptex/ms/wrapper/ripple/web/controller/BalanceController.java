package io.cryptex.ms.wrapper.ripple.web.controller;

import io.cryptex.ms.wrapper.ripple.service.RippleService;
import io.cryptex.ms.wrapper.ripple.web.model.BalanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/wrapper/balance")
@RestController
public class BalanceController {
	
	private final RippleService rippleService;
	
	@GetMapping
	public ResponseEntity<BalanceResponse> getBalance(){
		log.info("Request to get balance");
		BalanceResponse response = rippleService.getWalletBalance();
		log.info("Response for get balance | balance : {}", response.getAmount());
		return ResponseEntity.ok(response);
	}
}
