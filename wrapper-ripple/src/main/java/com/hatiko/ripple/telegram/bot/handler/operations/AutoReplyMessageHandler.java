package com.hatiko.ripple.telegram.bot.handler.operations;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
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
		if (argv.contains(".")
				|| operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getWithdraw())) {
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

		Integer sentMessageId;
		Object response;

		try {
			response = operationService.insertArgument(argvInteger == null ? argv : argvInteger, chatId);
		} catch (NullPointerException e) {
			log.error("Response is null");
			sentMessageId = responseMessageOperator.responseErrorMessage("autoReply", chatId);
			// TODO: finaly save index to db
			return;
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
		// TODO: finaly save index to db
	}
}
