package com.hatiko.ripple.telegram.bot.service;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageDeletionService {
	
	private final SessionService sessionService;
	private final XrpDatabaseOperator databaseOperator;
	private final XrpLongPollingBot xrpLongPollingBot;
	
	public void deleteMessages(Long chatId) {
		
		MessageIdDTO requestToDelete = databaseOperator.getMessageId(chatId);
		for(Integer messageId = requestToDelete.getLastDeleted(); messageId <= requestToDelete.getLastSent(); messageId++) {
			xrpLongPollingBot.deleteMessage(chatId, messageId);
		}
		databaseOperator.updateMessageId(chatId, requestToDelete.getLastSent(), requestToDelete.getLastSent());
	}
	
	
}
