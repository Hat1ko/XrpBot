package com.hatiko.ripple.telegram.bot.core.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AutoReplyTelegramMessageHandler implements TelegramMessageHandler {
	
	private final XrpLongPollingBot xrpLongPollingBot;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {
		// TODO Auto-generated method stub
		
	}

}
