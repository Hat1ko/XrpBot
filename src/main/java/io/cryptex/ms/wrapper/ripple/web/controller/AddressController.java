package io.cryptex.ms.wrapper.ripple.web.controller;

import io.cryptex.ms.wrapper.ripple.service.RippleService;
import io.cryptex.ms.wrapper.ripple.web.model.AddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/wrapper/address")
@RestController
public class AddressController {

    private final RippleService rippleService;

    @GetMapping
    public ResponseEntity<AddressResponse> getAccountAddress() {
        log.info("Request to get address");
        AddressResponse response = rippleService.getAccountAddress();
        log.info("Response for get address | walletAddress : {}", response.getWalletAddress());
        return ResponseEntity.ok(response);
    }

}
