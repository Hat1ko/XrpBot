package com.hatiko.ripple.telegram.bot.handler.authentication;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogOutMessageHandler implements TelegramMessageHandler {@Override
	
	public void handle(TelegramUpdate telegramUpdate) {
		// TODO Auto-generated method stub
		
	}

}
