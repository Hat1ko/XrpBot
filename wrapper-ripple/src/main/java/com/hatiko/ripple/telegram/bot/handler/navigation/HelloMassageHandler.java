package com.hatiko.ripple.telegram.bot.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloMassageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final ResponseMessageOperator responseMessageOperator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getHello())) {
			return;
		}

		Integer messageId = responseMessageOperator.responseHello(telegramUpdate.getMessage().getFrom().getFirstName(),
				telegramUpdate.getMessage().getChat().getId());
	}
}
