package com.hatiko.ripple.telegram.bot.handler.operations;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GenerateMemoMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final RippleService rippleService;
	private final ResponseMessageOperator responseMessageOperator;
	private final XrpDatabaseOperator databaseOperator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText()
				.startsWith(actionProperties.getButtonOperation().getGenerateMemo())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		String walletMemo = rippleService.generateMemo().getWalletMemo();

		Integer sentMessageId = responseMessageOperator.responseGenerateMemo(walletMemo,
				chatId);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}
}
