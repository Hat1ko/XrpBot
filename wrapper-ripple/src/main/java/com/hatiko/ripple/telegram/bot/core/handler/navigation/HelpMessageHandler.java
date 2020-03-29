package com.hatiko.ripple.telegram.bot.core.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelpMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getHelp())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		String text;

		text = "We will help you";

		xrpLongPollingBot.sendMessage(chatId, text, null);
	}
}
