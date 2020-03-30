package com.hatiko.ripple.telegram.bot.core.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.core.service.ResponseMessageOperator;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetTransactionInfoMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties; 
	private final LongTermOperationService operationService;
	private final RippleService rippleService;
	private final ResponseMessageOperator responseMessageOperator;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if(!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getGetTransactionInfo())) {
			return;
		}
		
		
		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
				
		Method method;
		try {
			method = RippleService.class.getDeclaredMethod(actionProperties.getMethodName().getGetTransactionInfo(), String.class);
			operationService.addOpearion(chatId, messageId, actionProperties.getMethodName().getGetTransactionInfo(), rippleService, method, 1);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage());
		} catch (SecurityException e) {
			log.error(e.getMessage());
		}
		
		Integer sentMessageId = responseMessageOperator.responseGetTransactionInfo(null, chatId);
	}
}
