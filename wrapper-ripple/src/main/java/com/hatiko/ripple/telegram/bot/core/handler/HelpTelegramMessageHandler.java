package com.hatiko.ripple.telegram.bot.core.handler;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUser;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelpTelegramMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties commandProperties;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(commandProperties.getCommand().getHelp())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		String text;

		text = "We will help you";

		TelegramUser user = telegramUpdate.getMessage().getFrom();
		xrpLongPollingBot.sendTextMessage(chatId, text);
	}
}
