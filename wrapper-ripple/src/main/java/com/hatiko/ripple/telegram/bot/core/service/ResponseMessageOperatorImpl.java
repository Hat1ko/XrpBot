package com.hatiko.ripple.telegram.bot.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.properties.ResponseMessageProperties;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseMessageOperatorImpl implements ResponseMessageOperator {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final ResponseMessageProperties messageProperties;

	public ResponseMessageOperatorImpl(@Lazy XrpLongPollingBot xrpLongPollingBot, ActionProperties actionProperties,
			KeyboardPreparator keyboardPreparator, ResponseMessageProperties messageProperties) {
		this.xrpLongPollingBot = xrpLongPollingBot;
		this.actionProperties = actionProperties;
		this.keyboardPreparator = keyboardPreparator;
		this.messageProperties = messageProperties;
	}

	@Override
	public Integer responseStart(String firstName, Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getStart(), firstName),
				keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseHello(String firstName, Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getHello(), firstName),
				keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseMain(Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getMain(), keyboardPreparator.getMainKeyboard());
	}

	@Override
	public Integer responseNext(Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getNext(), keyboardPreparator.getMainKeyboard());
	}

	@Override
	public Integer responseHelp(Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getHelp(), null);
	}

	@Override
	public Integer responseLogIn(Long chatId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseRegister(Long chatId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseGenerateMemo(String walletMemo, Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, walletMemo, keyboardPreparator.getMainKeyboard());
	}

	@Override
	public Integer responseGetBalance(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetBalance().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = String.format(messageProperties.getGetBalance().get(operationCounter),
					((BalanceResponse) responseObject).getAmount());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetBalance(), chatId);
	}

	@Override
	public Integer responseGetLastTransactions(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetLastTransactions().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = messageProperties.getGetLastTransactions().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			List<TransactionResponse> transactions = (ArrayList<TransactionResponse>) responseObject;
			responseMessage = String.format(messageProperties.getGetLastTransactions().get(operationCounter),
					transactions.size());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetLastTransactions(), chatId);
	}

	@Override
	public Integer responseWithdraw(Object responseObject, Long chatId, Integer operationCounter) {

		if (operationCounter > -1 && operationCounter < 5) {
			return xrpLongPollingBot.sendMessage(chatId, messageProperties.getWithdraw().get(operationCounter), null);
		}
		return responseErrorMessage(actionProperties.getMethodName().getWithdraw(), chatId);
	}

	@Override
	public Integer responseGetTransactionInfo(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage;

		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetTransactionInfo().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = String.format(messageProperties.getGetTransactionInfo().get(operationCounter),
					((TransactionResponse) responseObject).getAmount());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetTransactionInfo(), chatId);
	}

	@Override
	public Integer responseErrorMessage(String operation, Long chatId) {

		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getError(), operation),
				keyboardPreparator.getMainKeyboard());
	}
}
