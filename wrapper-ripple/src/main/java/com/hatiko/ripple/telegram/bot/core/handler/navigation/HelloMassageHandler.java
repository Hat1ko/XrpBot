package com.hatiko.ripple.telegram.bot.core.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloMassageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButton().getHello())) {
			return;
		}

		Long chatId = telegramUpdate
				.getMessage()
				.getChat()
				.getId();
		
		String text = String.format("Hello, %s", telegramUpdate.getMessage().getFrom().getFirstName());

		xrpLongPollingBot.sendTextMessage(chatId, text);
	}
}
