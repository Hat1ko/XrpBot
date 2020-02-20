package com.hatiko.ripple.wrapper.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.service.TransactionService;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionScheduler {

	private final TransactionService transactionService;
	
	@Scheduled(cron = "${wrapper.ripple.scheduler.cron}")
	public void getNewTransactions() {

		LastProcessedTransactionData lastProcessedTransactionData;
		List<TransactionResponse> newTransactionsToProcess;

		try {
			lastProcessedTransactionData = transactionService.getLastProcessedTransactionData();
		} catch (Exception e) {
			log.error("Error while getting last processed sequence | Error : {}", e.getMessage());
			return;
		}

		try {
			newTransactionsToProcess = transactionService
					.getNewBlockchainTransactionsToProcess(lastProcessedTransactionData.getTransactionIndex());
		} catch (Exception e) {
			log.error("Error while getting new transactions from blockchain to be processed | Last processed ledger index = {}. Error = {}",
					lastProcessedTransactionData.getTransactionIndex(), e.getMessage());
			return;
		}

		try {
			if (!newTransactionsToProcess.isEmpty()) {
				transactionService.sendNewTransactionsList(newTransactionsToProcess);
			}
		} catch (Exception e) {
			log.error("Error while sending new transactions to cryptopayment | Error : {}", e.getMessage());
		}

	}
}
