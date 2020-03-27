package com.hatiko.ripple.telegram.bot.core.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface KeyboardPreparator {
	public ReplyKeyboardMarkup startKeyboard();
	public ReplyKeyboardMarkup mainKeyboard();
	public ReplyKeyboardMarkup logInKeyboard();
}
