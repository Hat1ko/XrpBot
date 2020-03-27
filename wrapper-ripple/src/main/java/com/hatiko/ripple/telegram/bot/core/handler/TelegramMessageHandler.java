	package com.hatiko.ripple.telegram.bot.core.handler;

import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;

public interface TelegramMessageHandler {
	void handle(TelegramUpdate telegramUpdate);
}
