package com.hatiko.ripple.telegram.bot.handler.authentication;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogInMessageHandler implements TelegramMessageHandler {

	private final ResponseMessageOperator messageOperator;
	private final XrpDatabaseOperator databaseOperator;
	private final LongTermOperationService operationService;
	private final ActionProperties actionProperties;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getLogIn())) {
			return;
		}
		
		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		
		log.info("LogIn button triggered by chatId = {}, messageId = {}", chatId, messageId);
		
		databaseOperator.updateMessageId(chatId, messageId, null);
		
		try {
			Method method = XrpDatabaseOperator.class.getDeclaredMethod(actionProperties.getMethodName().getLogIn(),
					String.class, String.class);
			operationService.addOpearion(chatId, messageId, actionProperties.getMethodName().getLogIn(),
					databaseOperator, method, 2);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage());
			return;
		} catch (SecurityException e) {
			log.error(e.getMessage());
			return;
		}

		Integer sentMessageId = messageOperator.responseLogIn(chatId, 0, null);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);
	}
}
