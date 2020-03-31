package com.hatiko.ripple.telegram.bot.handler.operations;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
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
	private final KeyboardPreparator keyboardPreparator;
	private final RippleService rippleService;
	private final ResponseMessageOperator responseMessageOperator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText()
				.startsWith(actionProperties.getButtonOperation().getGenerateMemo())) {
			return;
		}

		String walletMemo = rippleService.generateMemo().getWalletMemo();

		Integer messageId = responseMessageOperator.responseGenerateMemo(walletMemo,
				telegramUpdate.getMessage().getChat().getId());
	}
}
