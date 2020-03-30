package com.hatiko.ripple.telegram.bot.core.handler.operations;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GenerateMemoMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final RippleService rippleService;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText()
				.startsWith(actionProperties.getButtonOperation().getGenerateMemo())) {
			return;
		}

		String walletMemo = rippleService.generateMemo().getWalletMemo();

		Integer messageId = xrpLongPollingBot.sendMessage(telegramUpdate.getMessage().getChat().getId(), walletMemo,
				keyboardPreparator.getMainKeyboard());
	}
}
