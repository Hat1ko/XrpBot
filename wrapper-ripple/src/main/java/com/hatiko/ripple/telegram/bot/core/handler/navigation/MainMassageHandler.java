package com.hatiko.ripple.telegram.bot.core.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MainMassageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollngBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButton().getMain())) {
			return;
		}

		Integer messageId = xrpLongPollngBot.sendMessage(telegramUpdate.getMessage().getChat().getId(), null,
				keyboardPreparator.getMainKeyboard());
	}
}
