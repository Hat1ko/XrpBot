package com.hatiko.ripple.telegram.bot.handler.operations;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;
import com.hatiko.ripple.telegram.bot.dto.session.ChatSession;
import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.service.LongTermOperationService;
import com.hatiko.ripple.telegram.bot.service.ResponseMessageOperator;
import com.hatiko.ripple.telegram.bot.service.SessionService;
import com.hatiko.ripple.wrapper.service.RippleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WithdrawMessageHandler implements TelegramMessageHandler {

    private final ActionProperties actionProperties;
    private final LongTermOperationService operationService;
    private final SessionService sessionService;
    private final RippleService rippleService;
    private final ResponseMessageOperator responseMessageOperator;
    private final XrpDatabaseOperator databaseOperator;
    private final AutoReplyMessageHandler autoReplyMessageHandler;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {

        if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getWithdraw())) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        Integer messageId = telegramUpdate.getMessage().getId();

        log.info("Withdraw button triggered by chatId = {}, messageId = {}", chatId, messageId);

        databaseOperator.updateMessageId(chatId, messageId, null);

        try {
            Method method = RippleService.class.getDeclaredMethod(actionProperties.getMethodName().getWithdraw(),
                    String.class, String.class, String.class, String.class, Double.class);
            operationService.addOpearion(chatId, messageId, actionProperties.getMethodName().getWithdraw(),
                    rippleService, method, 5);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
            return;
        } catch (SecurityException e) {
            log.error(e.getMessage());
            return;
        }

        if (sessionService.checkSessionExist(chatId)) {

            ChatSession session = sessionService.getSession(chatId).get();

            String publicKey = session.getPublicKey();
            String privateKey = session.getPrivateKey();

            Object response;
            response = operationService.insertArgument(publicKey, chatId);
            response = operationService.insertArgument(privateKey, chatId);

            autoReplyMessageHandler.operateObject(response, chatId);
            return;
        }

        Integer sentMessageId = responseMessageOperator.responseWithdraw(null, chatId, 0);
        databaseOperator.updateMessageId(chatId, sentMessageId, null);
    }
}
