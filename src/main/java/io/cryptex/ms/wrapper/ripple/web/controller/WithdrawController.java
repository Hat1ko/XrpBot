package io.cryptex.ms.wrapper.ripple.web.controller;

import io.cryptex.ms.wrapper.ripple.service.RippleService;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;
import io.cryptex.ms.wrapper.ripple.web.model.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/withdraw")
@RestController
public class WithdrawController {

	private final RippleService rippleService;

	@PostMapping
	public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequest withdrawRequest) {
		log.info("Request to withdraw | to : {}, amount : {}, memo : {}", withdrawRequest.getTo(),
				withdrawRequest.getAmount(), withdrawRequest.getMemo());
		TransactionResponse response = rippleService.withdraw(withdrawRequest);
		log.info("Response from withdraw | trxId : {}", response.getTrxId());
		return ResponseEntity.ok(response);
	}
}
