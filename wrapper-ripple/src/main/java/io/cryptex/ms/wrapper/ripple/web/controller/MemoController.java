package io.cryptex.ms.wrapper.ripple.web.controller;

import io.cryptex.ms.wrapper.ripple.service.RippleService;
import io.cryptex.ms.wrapper.ripple.web.model.MemoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/memo")
@RestController
public class MemoController {
	
	private final RippleService rippleService;
	
	@GetMapping
	public ResponseEntity<MemoResponse> getMemoResponse() {
		log.info("Request to get payment Memo");
		MemoResponse memoResponse = rippleService.generateMemo();
		log.info("Response for get payment memo | Generated memo: {}", memoResponse.getWalletMemo());
		return ResponseEntity.ok(memoResponse);
	}	
}
