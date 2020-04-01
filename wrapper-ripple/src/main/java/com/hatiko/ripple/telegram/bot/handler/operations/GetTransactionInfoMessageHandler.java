package com.hatiko.ripple.telegram.bot.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
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
		
		Integer sentMessageId = responseMessageOperator.responseGetTransactionInfo(null, chatId, 0);
	}
}