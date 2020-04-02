package com.hatiko.ripple.telegram.bot.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageDeletionService {

	private final XrpDatabaseOperator databaseOperator;
	private final LongTermOperationService operationService;
	private final XrpLongPollingBot xrpLongPollingBot;

	public MessageDeletionService(XrpDatabaseOperator databaseOperator, LongTermOperationService operationService,
			@Lazy XrpLongPollingBot xrpLongPollingBot) {
		this.databaseOperator = databaseOperator;
		this.operationService = operationService;
		this.xrpLongPollingBot = xrpLongPollingBot;
	}

	public void deleteMessages(Long chatId) {

		MessageIdDTO requestToDelete = databaseOperator.getMessageId(chatId);
		for (Integer messageId = requestToDelete.getLastDeleted(); messageId <= requestToDelete
				.getLastSent(); messageId++) {
			xrpLongPollingBot.deleteMessage(chatId, messageId);
		}
		databaseOperator.updateMessageId(chatId, requestToDelete.getLastSent(), requestToDelete.getLastSent());
	}

	@Scheduled(cron = "${telegram.bot.every-day-delete.cron}")
	public void deleteEachChat() {

		databaseOperator.getAllMessageIds().stream().forEach(m -> {
			deleteMessages(m.getCahtId());
			operationService.removeOperation(m.getCahtId());
		});
	}
}
