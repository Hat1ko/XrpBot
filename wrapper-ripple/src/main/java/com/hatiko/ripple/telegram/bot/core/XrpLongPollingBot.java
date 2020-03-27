package com.hatiko.ripple.telegram.bot.core;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.properties.XrpBotProperties;
import com.hatiko.ripple.telegram.bot.core.service.TelegramUpdateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot {

	@Lazy
	private final List<TelegramMessageHandler> telegramMessageHandlers;

	private final TelegramUpdateService telegramUpdateService;
	private final XrpBotProperties xrpBotProperties;

	@Override
	public void onUpdateReceived(Update update) {
		TelegramUpdate telegramUpdate = telegramUpdateService.save(update);
		telegramMessageHandlers.forEach(telegramMessageHandler -> telegramMessageHandler.handle(telegramUpdate));
	}

	@Override
	public String getBotUsername() {
		return xrpBotProperties.getUsername();
	}

	@Override
	public String getBotToken() {
		return xrpBotProperties.getToken();
	}
}
