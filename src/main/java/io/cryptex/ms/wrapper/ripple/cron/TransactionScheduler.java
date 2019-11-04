package io.cryptex.ms.wrapper.ripple.cron;

import io.cryptex.ms.wrapper.ripple.integration.cryptopayment.dto.LastProcessedTransactionData;
import io.cryptex.ms.wrapper.ripple.service.TransactionService;
import io.cryptex.ms.wrapper.ripple.web.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
			log.error("Error while getting new transactions from blockchain to be processed | Error : {}",
					e.getMessage());
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
