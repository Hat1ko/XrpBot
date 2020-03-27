	package com.hatiko.ripple.telegram.bot.core.handler;

import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;

public interface TelegramMessageHandler {
	void handle(TelegramUpdate telegramUpdate);
}
