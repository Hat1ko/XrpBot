package com.hatiko.ripple.telegram.bot.core.handler.operations;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AutoReplyHandler implements TelegramMessageHandler {
	
	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {
		// TODO Auto-generated method stub
		
	}
}
