package com.hatiko.ripple.telegram.bot.core.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.LongTermOperationService;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetTransactionInfoMessageHandler implements TelegramMessageHandler {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties; 
	private final KeyboardPreparator keyboardPreparator;
	private final LongTermOperationService operationService;
	private final RippleService rippleService;
	
	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if(!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButton().getGetTransactionInfo())) {
			return;
		}
		
		
		Long chatId = telegramUpdate.getMessage().getChat().getId();
		Integer messageId = telegramUpdate.getMessage().getId();
				
		String operation = "getTransactionInfo";
		
		Method method;
		try {
			method = RippleService.class.getDeclaredMethod("getTransactionByHash", String.class);
			operationService.addOpearion(chatId, messageId, operation, rippleService, method, 1);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage());
		} catch (SecurityException e) {
			log.error(e.getMessage());
		}
		
		String text = "Insert your transaction hash";
		
		Integer sentMessageId = xrpLongPollingBot.sendMessage(chatId, text, null);
		
	}
}
