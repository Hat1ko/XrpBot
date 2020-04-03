package com.hatiko.ripple.wrapper.integration.blockchain.rest;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.hatiko.ripple.wrapper.exception.InnerServiceException;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleAccountInfoRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleLastTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTransactionsRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTrxByHashRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleWithdrawRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleAccountInfoResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTransactionsResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleTrxByHashResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.uri.RippleBlockchainUriBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RippleCommunicationServiceImpl implements RippleCommunicationService {

    private final RestTemplate rippleBlockchainRestTemplate;
    private final RippleBlockchainUriBuilder rippleBlockchainUriBuilder;

    @Override
    public RippleAccountInfoResponse getAccountInfo(RippleAccountInfoRequest rippleAccountInfoRequest) {
        RippleAccountInfoRequest.Param param = rippleAccountInfoRequest.getParams().get(0);
        URI uri = rippleBlockchainUriBuilder.getRequestUri();
        HttpEntity<RippleAccountInfoRequest> httpEntity = new HttpEntity<>(rippleAccountInfoRequest, buildDefaultHttpHeaders());
        try {
            log.info("Request Ripple blockchain to get account info. Account = {}", param.getAccount());
            ResponseEntity<RippleAccountInfoResponse> rippleAccountInfoResponse = rippleBlockchainRestTemplate.postForEntity(uri, httpEntity, RippleAccountInfoResponse.class);
            log.info("Response from Ripple blockchain | Status code : {}", rippleAccountInfoResponse.getStatusCode());
            return rippleAccountInfoResponse.getBody();
        } catch (ResourceAccessException e) {
            String message = String.format("No response from Ripple blockchain on get account info | Account = %s, Error = %s",
                    param.getAccount(), e.getMessage());
            log.error(message);
            throw new InnerServiceException(message);
        }
    }

    @Override
    public RippleWithdrawResponse withdraw(RippleWithdrawRequest rippleWithdrawRequest) {
        URI uri = rippleBlockchainUriBuilder.getRequestUri();
        HttpEntity<RippleWithdrawRequest> httpEntity = new HttpEntity<>(rippleWithdrawRequest, buildDefaultHttpHeaders());
        try {
            log.info("Request Ripple blockchain to withdraw Ripple.");
            ResponseEntity<RippleWithdrawResponse> transactionResponse = rippleBlockchainRestTemplate.postForEntity(uri, httpEntity, RippleWithdrawResponse.class);
            log.info("Response from Ripple blockchain | status code : {}", transactionResponse.getStatusCode());
            return transactionResponse.getBody();
        } catch (ResourceAccessException e) {
            String message = String.format("No response from Ripple blockchain on withdraw Ripple | Error: %s",
                    e.getMessage());
            log.error(message);
            throw new InnerServiceException(message);
        }
    }

    @Override
    public RippleTrxByHashResponse getTransactionByHash(RippleTrxByHashRequest rippleTrxByHashRequest) {
        RippleTrxByHashRequest.Param param = rippleTrxByHashRequest.getParams().get(0);
        URI uri = rippleBlockchainUriBuilder.getRequestUri();
        HttpEntity<RippleTrxByHashRequest> httpEntity = new HttpEntity<>(rippleTrxByHashRequest, buildDefaultHttpHeaders());
        try {
            log.info("Request Ripple blockchain to get transaction by hash | hash : {}", param.getTransactionHash());
            ResponseEntity<RippleTrxByHashResponse> response = rippleBlockchainRestTemplate.postForEntity(uri, httpEntity, RippleTrxByHashResponse.class);
            log.info("Response from Riple blockchain | status code : {}", response.getStatusCode());
            return response.getBody();
        } catch (ResourceAccessException e) {
            String message = String.format("No response from Ripple blockchain on get transaction by hash | Hash = %s. Error = %s",
                    param.getTransactionHash(), e.getMessage());
            log.error(message);
            throw new InnerServiceException(message);
        }
    }

    @Override
    public RippleTransactionsResponse getTransactions(RippleTransactionsRequest rippleTransactionsRequest) {
        RippleTransactionsRequest.Param param = rippleTransactionsRequest.getParams().get(0);
        URI uri = rippleBlockchainUriBuilder.getRequestUri();
        HttpEntity<RippleTransactionsRequest> httpEntity = new HttpEntity<>(rippleTransactionsRequest, buildDefaultHttpHeaders());
        try {
            log.info("Request to ripple blockchain to get transactions after lastSequence. Account = {}, ledger index min = {}", param.getAccount(), param.getLedgerIndexMin());
            ResponseEntity<RippleTransactionsResponse> rippleTransactionsResponse = rippleBlockchainRestTemplate.postForEntity(uri, httpEntity, RippleTransactionsResponse.class);
            log.info("Response from ripple blockchain on get transactions. Size list = {}",
                    rippleTransactionsResponse.getBody().getResult().getTransactions().size());
            return rippleTransactionsResponse.getBody();
        } catch (ResourceAccessException e) {
            String message = String.format("No response from Ripple blockchain on get transactions : %s", e.getMessage());
            log.error(message);
            throw new InnerServiceException(message);
        }
    }
    
    @Override
    public RippleTransactionsResponse getLastTransactions(RippleLastTransactionsRequest rippleLastTransactionsRequest) {
    	RippleLastTransactionsRequest.Param param = rippleLastTransactionsRequest.getParams().get(0);
        URI uri = rippleBlockchainUriBuilder.getRequestUri();
        HttpEntity<RippleLastTransactionsRequest> httpEntity = new HttpEntity<>(rippleLastTransactionsRequest, buildDefaultHttpHeaders());
        try {
            log.info("Request to ripple blockchain to get transactions after lastSequence. Account = {}, limit = {}", param.getAccount(), param.getLimit());
            ResponseEntity<RippleTransactionsResponse> rippleTransactionsResponse = rippleBlockchainRestTemplate.postForEntity(uri, httpEntity, RippleTransactionsResponse.class);
            log.info("Response from ripple blockchain on get transactions. Size list = {}",
                    rippleTransactionsResponse.getBody().getResult().getTransactions().size());
            return rippleTransactionsResponse.getBody();
        } catch (ResourceAccessException e) {
            String message = String.format("No response from Ripple blockchain on get transactions : %s", e.getMessage());
            log.error(message);
            throw new InnerServiceException(message);
        }
    }
}
