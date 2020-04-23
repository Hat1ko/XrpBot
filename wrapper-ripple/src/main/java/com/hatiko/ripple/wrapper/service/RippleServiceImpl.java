package com.hatiko.ripple.wrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.hatiko.ripple.wrapper.converter.RippleBalanceConverter;
import com.hatiko.ripple.wrapper.converter.RippleTransactionConverter;
import com.hatiko.ripple.wrapper.exception.InnerServiceException;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleAccountInfoRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleTrxByHashRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleWithdrawRequest;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.request.RippleAccountInfoRequest.Param;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleAccountInfoResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.RippleWithdrawResponse;
import com.hatiko.ripple.wrapper.integration.blockchain.dto.response.TransactionBody;
import com.hatiko.ripple.wrapper.integration.blockchain.properties.RippleBlockchainProperties;
import com.hatiko.ripple.wrapper.integration.blockchain.rest.RippleCommunicationService;
import com.hatiko.ripple.wrapper.integration.properties.WalletProperties;
import com.hatiko.ripple.wrapper.web.model.AddressResponse;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.MemoResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;
import com.hatiko.ripple.wrapper.web.model.WithdrawRequest;

import java.util.Collections;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class RippleServiceImpl implements RippleService {

	private static final String SUCCESS = "success";
	private final RippleCommunicationService rippleCommunicationService;
	private final SignatureService signatureService;
	private final RippleBlockchainProperties rippleBlockchainProperties;
	private final WalletProperties walletProperties;
	private Random random = new Random();

	@Override
	public BalanceResponse getWalletBalance() {

		RippleAccountInfoRequest rippleAccountInfoRequest = RippleAccountInfoRequest.builder()
				.method(rippleBlockchainProperties.getMethod().getAccountInfo())
				.params(Collections.singletonList(Param.builder().account(walletProperties.getAccount()).build()))
				.build();

		RippleAccountInfoResponse rippleAccountInfoResponse;
		try {
			rippleAccountInfoResponse = rippleCommunicationService.getAccountInfo(rippleAccountInfoRequest);
		} catch (Exception e) {
			log.error("Error while connecting to Ripple node when getting WalletBalance | Message : {}",
					e.getMessage());
			log.debug("Retry request to get balance by other uri");
			rippleAccountInfoResponse = rippleCommunicationService.getAccountInfo(rippleAccountInfoRequest);
		}

		return BalanceResponse.builder().amount(
				RippleBalanceConverter.toDouble(rippleAccountInfoResponse.getResult().getAccountData().getBalance()))
				.build();
	}

	@Override
	public BalanceResponse getWalletBalanceByAccountAddress(String accountAddress) {

		walletProperties.setAccount(accountAddress);

		return getWalletBalance();
	}

	@Override
	public AddressResponse getAccountAddress() {
		return AddressResponse.builder().walletAddress(walletProperties.getAccount()).build();
	}

	@Override
	public TransactionResponse withdraw(WithdrawRequest withdrawRequest) {
		String to = withdrawRequest.getTo();
		Double amount = withdrawRequest.getQuantity();
		String memo = withdrawRequest.getMemo();

		String signature = signatureService.signTransaction(to, amount, memo);

		RippleWithdrawRequest requestToSubmit = RippleWithdrawRequest.builder()
				.method(rippleBlockchainProperties.getMethod().getSubmit())
				.params(Collections.singletonList(RippleWithdrawRequest.Param.builder().txBlob(signature).build()))
				.build();

		RippleWithdrawResponse rippleWithdrawResponse;
		try {
			rippleWithdrawResponse = rippleCommunicationService.withdraw(requestToSubmit);
		} catch (Exception e) {
			log.error("Error while connecting to Ripple node when requesting to submit a trx | Message : {}",
					e.getMessage());
			log.debug("Retry request to submit transaction by other uri");
			rippleWithdrawResponse = rippleCommunicationService.withdraw(requestToSubmit);
		}

		String status = rippleWithdrawResponse.getResult().getStatus();
		if (status.equals(SUCCESS)) {
			return RippleTransactionConverter.toTransactionResponse(rippleWithdrawResponse.getResult().getTxJson(),
					walletProperties);
		} else {
			log.error("Error while on withdraw. To = {}, amount = {}, memo = {}. Transaction status = {}", to, amount,
					memo, status);
			throw new InnerServiceException(
					String.format("Error while on withdraw. To = %s, amount = %s, memo = %s. Transaction status = %s",
							to, amount, memo, status));
		}

	}

	@Override
	public TransactionResponse withdrawByCredentials(String publicKey, String privateKey, String destinationKey,
			String memo, Double amount) {

		walletProperties.setAccount(publicKey);
		walletProperties.setSecretKey(privateKey);
		WithdrawRequest request = WithdrawRequest.builder().memo(memo).to(destinationKey).quantity(amount).build();

		return withdraw(request);
	}

	@Override
	public TransactionResponse getTransactionByHash(String transactionHash) {
		RippleTrxByHashRequest request = RippleTrxByHashRequest.builder()
				.method(rippleBlockchainProperties.getMethod().getTransaction())
				.params(Collections
						.singletonList(RippleTrxByHashRequest.Param.builder().transactionHash(transactionHash).build()))
				.build();

		TransactionBody transactionBody;
		try {
			transactionBody = rippleCommunicationService.getTransactionByHash(request).getResult();
		} catch (Exception e) {
			log.error("Error while connecting to Ripple node when getting trx by id | Message : {}", e.getMessage());
			log.debug("Retry request to get transaction by id by other uri");
			transactionBody = rippleCommunicationService.getTransactionByHash(request).getResult();
		}
		return RippleTransactionConverter.toTransactionResponse(transactionBody, walletProperties);
	}

	@Override
	public MemoResponse generateMemo() {
		StringBuilder sb = new StringBuilder();
		while (sb.length() < walletProperties.getMemoLength()) {
			sb.append(Integer.toHexString(random.nextInt()).toUpperCase());
		}
		String memo = sb.toString().substring(0, walletProperties.getMemoLength());
		return MemoResponse.builder().walletMemo(memo).build();
	}

}
