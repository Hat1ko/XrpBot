package com.hatiko.ripple.telegram.bot.handler.operations;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.OperationDTO;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.telegram.bot.service.SessionService;
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
	private final XrpDatabaseOperator databaseOperator;
	private final SessionService sessionService;

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

		Integer sentMessageId = null;
		Object response;

		try {
			response = operationService.insertArgument(argvInteger == null ? argv : argvInteger, chatId);
		} catch (NullPointerException e) {
			log.error("Response is null");
			sentMessageId = responseMessageOperator.responseErrorMessage("autoReply", chatId);
			databaseOperator.updateMessageId(chatId, sentMessageId, null);
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
			if(operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getLogIn())) {
				sentMessageId = responseMessageOperator.responseLogIn(chatId, numOfArgs, null);
			}
			if(operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getRegister())) {
				sentMessageId = responseMessageOperator.responseRegister(chatId, numOfArgs, null);
			}
			
		}
		if (response instanceof Boolean) {
			Boolean status = (Boolean) response;
			if (operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getLogIn())) {
				if(status) {
					OperationDTO oper = operationService.getOperation(chatId);
					sessionService.createSession(chatId, (String)oper.getParams().get(0), (String)oper.getParams().get(1));
				}
				sentMessageId = responseMessageOperator.responseLogIn(chatId, 2, status);
			}
			if (operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getRegister())) {
				if(status) {
					OperationDTO oper = operationService.getOperation(chatId);
					sessionService.createSession(chatId, (String)oper.getParams().get(0), (String)oper.getParams().get(1));
				}
				sentMessageId = responseMessageOperator.responseRegister(chatId, 4, status);
			}
		}

		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}
}
