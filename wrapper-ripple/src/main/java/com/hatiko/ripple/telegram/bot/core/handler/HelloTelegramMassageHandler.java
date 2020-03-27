package com.hatiko.ripple.telegram.bot.core.handler;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloTelegramMassageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getCommand().getHello())
				&& !telegramUpdate.getMessage().getText().startsWith(actionProperties.getCommand().getStart())) {
			return;
		}

		Long chatId = telegramUpdate
				.getMessage()
				.getChat()
				.getId();
		
		String text = String.format("Hello, %s", telegramUpdate.getMessage().getFrom().getFirstName());

		log.info(telegramUpdate.getMessage().getFrom().getUserName());
		
		xrpLongPollingBot.sendTextMessage(chatId, text);
	}
}
