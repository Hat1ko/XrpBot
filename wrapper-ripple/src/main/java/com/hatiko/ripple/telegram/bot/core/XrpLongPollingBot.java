package com.hatiko.ripple.telegram.bot.core;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.hatiko.ripple.telegram.bot.core.properties.XrpBotProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot{

	private final XrpBotProperties xrpBotProperties;
	
	@Override
	public void onUpdateReceived(Update update) {

	}

	@Override
	public String getBotUsername() {

		return null;
	}

	@Override
	public String getBotToken() {
		
		return null;
	}
}
