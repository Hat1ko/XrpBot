package com.hatiko.ripple.telegram.bot.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.telegram.bot.service.SessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloMassageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final ResponseMessageOperator responseMessageOperator;
	private final XrpDatabaseOperator databaseOperator; 
	private final SessionService sessionService;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getHello())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();

		sessionService.deleteSession(chatId);
		
		Integer sentMessageId = responseMessageOperator.responseHello(telegramUpdate.getMessage().getFrom().getFirstName(),
				chatId);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}
}
