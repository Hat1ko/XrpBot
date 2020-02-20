package com.hatiko.ripple.wrapper.integration.cryptopayment.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.hatiko.ripple.wrapper.exception.InnerServiceException;
import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.integration.cryptopayment.uri.CryptoPaymentUriBuilder;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CryptoPaymentCommunicationServiceImpl implements CryptoPaymentCommunicationService {

	private final RestTemplate cryptoPaymentRestTemplate;
	private final CryptoPaymentUriBuilder cryptoPaymentUriBuilder;

	@Override
	public LastProcessedTransactionData getLastProcessedTransactionData() {
		URI uri = cryptoPaymentUriBuilder.getLastProcessedDataUri();
		try {
			log.info("Request to cryptopayment to get last processed transaction data");
			ResponseEntity<LastProcessedTransactionData> lastProcessedTransactionData = cryptoPaymentRestTemplate.getForEntity(uri, LastProcessedTransactionData.class);
			log.info("Response from cryptopayment on get last processed transaction data : transaction index = {} ",
					lastProcessedTransactionData.getBody().getTransactionIndex());
			return lastProcessedTransactionData.getBody();
		} catch (ResourceAccessException e) {
			String message = String.format("No response from cryptopayment on last processed transaction data. Error = %s.", e.getMessage());
			log.error(message);
			throw new InnerServiceException(message);
		}

	}

	@Override
	public void sendNewTransactionsToProcess(List<TransactionResponse> transactions) {
		URI uri = cryptoPaymentUriBuilder.getTransactionNewListUri();
		HttpEntity<List<TransactionResponse>> httpEntity = new HttpEntity<>(transactions, buildDefaultHttpHeaders());
		try {
			log.info("Request to cryptopayment to post new transaction list size = {}", transactions.size());
			HttpStatus httpStatus = cryptoPaymentRestTemplate.postForEntity(uri, httpEntity, Object.class).getStatusCode();
			log.info("Response from cryptopayment on new transaction list : status = {}", httpStatus);
		} catch (ResourceAccessException e) {
			String message = String.format("No response from cryptopayment on post new transaction list. Error = %s", e.getMessage());
			log.error(message);
			throw new InnerServiceException(message);
		}
	}

}
