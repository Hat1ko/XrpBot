package com.hatiko.ripple.telegram.bot.handler.authentication;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.telegram.bot.service.SessionService;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogOutMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final XrpDatabaseOperator databaseOperator;
	private final ResponseMessageOperator messageOperator;
	private final SessionService sessionService;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getLogOut())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();

		log.info("LogOut button triggered by chatId = {}, messageId = {}", chatId, messageId);
		
		databaseOperator.updateMessageId(chatId, messageId, null);

		sessionService.deleteSession(chatId);

		Integer sentMessageId = messageOperator.responseLogOut(chatId);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}

}
