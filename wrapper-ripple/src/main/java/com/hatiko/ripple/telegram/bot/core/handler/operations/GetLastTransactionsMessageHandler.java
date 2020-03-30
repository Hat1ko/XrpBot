package com.hatiko.ripple.telegram.bot.core.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.core.service.ResponseMessageOperator;
import com.hatiko.ripple.wrapper.service.RippleService;
import com.hatiko.ripple.wrapper.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetLastTransactionsMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final LongTermOperationService operationService;
	private final TransactionService transactionService;
	private final ResponseMessageOperator responseMessageOperator;
	
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
		
		Integer sentMessageId = responseMessageOperator.responseGetLastTransactions(null, chatId, 0);
	}
}
