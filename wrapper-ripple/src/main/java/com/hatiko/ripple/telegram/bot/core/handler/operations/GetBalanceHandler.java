package com.hatiko.ripple.telegram.bot.core.handler.operations;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetBalanceHandler implements TelegramMessageHandler {
	
	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keboardPreparator;
	private final LongTermOperationService operationService;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {
		
		if(!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButton().getGetBalance())) {
			return;
		}
		
		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		
//		operationService.addOpearion(chatId, messageId, "getBalance", method, 1);
		
		String text = "Insert your wallet (public key)";
		
		Integer SentMessageId = xrpLongPollingBot.sendMessage(chatId, text, null);
	}
}
