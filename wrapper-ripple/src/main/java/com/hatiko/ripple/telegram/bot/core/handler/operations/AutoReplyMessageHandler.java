package com.hatiko.ripple.telegram.bot.core.handler.operations;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AutoReplyMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final LongTermOperationService operationService;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (actionProperties.getButtonOperations().stream()
				.filter(e -> telegramUpdate.getMessage().getText().startsWith(e)).count() > 0) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		String argv = telegramUpdate.getMessage().getText();

		Long argvInteger = null;
		try {
			argvInteger = Long.valueOf(argv);
		}catch(Exception e) {
			log.info("Argument is not a number");
		}
		
		Object response = operationService.insertArgument(argvInteger == null ? argv : argvInteger, chatId);
		
		String responseMessage = null;

		if(response instanceof Integer) {
			Integer numOfArgs = (Integer) response;
			
			if(operationService.getMethodName(chatId).equals(actionProperties.getMethodName().getGetLastTransactions())) {
				if(numOfArgs.equals(1)) {
					String text = "Insert number of transactions";
					Integer sentMessage = xrpLongPollingBot.sendMessage(chatId, text, null);
				}
			}
			
		}
		if(response instanceof BalanceResponse) {
			responseMessage = String.format("Your balance is %s", ((BalanceResponse) response).getAmount());
		}
		if(response instanceof TransactionResponse) {
			responseMessage = String.format("Sum of transaction is %s", ((TransactionResponse) response).getAmount());
		}
		if(response instanceof ArrayList) {
			responseMessage = String.format("Num of transactions is %s", ((ArrayList<TransactionResponse>) response).size());
		}
		Integer sentMessageId = xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
	}
}
