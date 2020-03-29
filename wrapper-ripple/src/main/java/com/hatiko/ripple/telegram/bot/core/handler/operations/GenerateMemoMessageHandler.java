package com.hatiko.ripple.telegram.bot.core.handler.operations;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GenerateMemoMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final RippleService rippleService;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if(!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getGenerateMemo())) {
			return;
		}
		
		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		
		String walletMemo = rippleService.generateMemo().getWalletMemo();
		
		Integer sentMessage = xrpLongPollingBot.sendMessage(chatId, walletMemo, keyboardPreparator.getMainKeyboard()); 
	}
}
