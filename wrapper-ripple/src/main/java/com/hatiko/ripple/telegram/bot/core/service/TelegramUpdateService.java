package com.hatiko.ripple.telegram.bot.core.service;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;

public interface TelegramUpdateService {
	public TelegramUpdate save(Update update);
}
