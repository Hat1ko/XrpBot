	package com.hatiko.ripple.telegram.bot.handler;

import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;

public interface TelegramMessageHandler {
	void handle(TelegramUpdate telegramUpdate);
}
