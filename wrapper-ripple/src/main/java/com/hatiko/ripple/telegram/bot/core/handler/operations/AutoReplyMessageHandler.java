package com.hatiko.ripple.telegram.bot.core.handler.operations;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.core.service.ResponseMessageOperator;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AutoReplyMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final LongTermOperationService operationService;
	private final ResponseMessageOperator responseMessageOperator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (actionProperties.getButtonOperations().stream()
				.filter(e -> telegramUpdate.getMessage().getText().startsWith(e)).count() > 0) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		String argv = telegramUpdate.getMessage().getText();

		Object argvInteger = null;
		if (argv.contains(".")) {
			try {
				argvInteger = Double.valueOf(argv);
			} catch (Exception e) {
				log.info("Argument is not a number");
			}
		} else {
			try {
				argvInteger = Long.valueOf(argv);
			} catch (Exception e) {
				log.info("Argument is not a number");
			}
		}
		
		Object response = operationService.insertArgument(argvInteger == null ? argv : argvInteger, chatId);

		Integer sentMessageId;
		
		if (response instanceof Integer) {
			Integer numOfArgs = (Integer) response;

			if (operationService.getMethodName(chatId)
					.equals(actionProperties.getMethodName().getGetLastTransactions())) {
				sentMessageId = responseMessageOperator.responseGetLastTransactions(null, chatId, numOfArgs);
			}
			if (operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getWithdraw())) {
				sentMessageId = responseMessageOperator.responseWithdraw(null, chatId, numOfArgs);
			}
		}
		if (response instanceof BalanceResponse) {
			sentMessageId = responseMessageOperator.responseGetBalance((BalanceResponse) response, chatId, 1);
		}
		if (response instanceof TransactionResponse) {
			sentMessageId = responseMessageOperator.responseGetTransactionInfo(response, chatId, 1);
		}
		if (response instanceof ArrayList) {
			sentMessageId = responseMessageOperator.responseGetLastTransactions(response, chatId, 2);
		}
	}
}
