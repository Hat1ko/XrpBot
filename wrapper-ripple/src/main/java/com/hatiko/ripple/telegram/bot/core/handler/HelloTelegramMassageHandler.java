package com.hatiko.ripple.telegram.bot.core.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.properties.CommandProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloTelegramMassageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final CommandProperties commandProperties;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(commandProperties.getHello())
				&& !telegramUpdate.getMessage().getText().startsWith(commandProperties.getStart())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		String text = String.format("Hello, {}", telegramUpdate.getMessage().getFrom().getUserName());

		xrpLongPollingBot.sendTextMessage(chatId, text);
	}
}
