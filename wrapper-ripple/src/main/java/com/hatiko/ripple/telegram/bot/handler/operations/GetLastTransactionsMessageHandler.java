package com.hatiko.ripple.telegram.bot.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.telegram.bot.service.SessionService;
import com.hatiko.ripple.wrapper.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetLastTransactionsMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final LongTermOperationService operationService;
	private final SessionService sessionService;
	private final TransactionService transactionService;
	private final ResponseMessageOperator responseMessageOperator;
	private final XrpDatabaseOperator databaseOperator;
	private final AutoReplyMessageHandler autoReplyMessageHandler;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText()
				.startsWith(actionProperties.getButtonOperation().getGetLastTransactions())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();

		try {
			Method method = TransactionService.class
					.getDeclaredMethod(actionProperties.getMethodName().getGetLastTransactions(),
					String.class, Long.class);
			operationService.addOpearion(chatId, messageId, actionProperties.getMethodName().getGetLastTransactions(),
					transactionService, method, 2);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage());
			return;
		} catch (SecurityException e) {
			log.error(e.getMessage());
			return;
		}
		
		if(sessionService.checkSessionExist(chatId)) {
			String publicKey = sessionService.getSession(chatId).get().getPublicKey();
			Object response = operationService.insertArgument(publicKey, chatId);
			autoReplyMessageHandler.operateObject(response, chatId);
			return;
		}
		
		Integer sentMessageId = responseMessageOperator.responseGetLastTransactions(null, chatId, 0);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}
}
