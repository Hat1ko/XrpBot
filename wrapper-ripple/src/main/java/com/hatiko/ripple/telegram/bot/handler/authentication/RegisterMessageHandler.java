package com.hatiko.ripple.telegram.bot.handler.authentication;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
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
public class RegisterMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final XrpDatabaseOperator databaseOperator;
	private final ResponseMessageOperator messageOperator;
	private final LongTermOperationService operationService;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {
		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getRegister())) {
			return;
		}

		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
		try {
			Method method = XrpDatabaseOperator.class.getDeclaredMethod(actionProperties.getMethodName().getRegister(),
					String.class, String.class, String.class, String.class);
			operationService.addOpearion(chatId, messageId, actionProperties.getMethodName().getRegister(),
					databaseOperator, method, 4);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage());
			return;
		} catch (SecurityException e) {
			log.error(e.getMessage());
			return;
		}

		Integer sentMessageId = messageOperator.responseRegister(chatId, 0, null);
		databaseOperator.updateMessageId(chatId, sentMessageId, null);

	}

}
