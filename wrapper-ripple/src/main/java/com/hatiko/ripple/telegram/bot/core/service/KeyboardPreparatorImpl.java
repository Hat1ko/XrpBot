package com.hatiko.ripple.telegram.bot.core.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeyboardPreparatorImpl implements KeyboardPreparator {

	private final ActionProperties actionProperties;
	
	@Override
	public ReplyKeyboardMarkup startKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplyKeyboardMarkup mainKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplyKeyboardMarkup logInKeyboard() {
		// TODO Auto-generated method stub
		return null;
	}
}
