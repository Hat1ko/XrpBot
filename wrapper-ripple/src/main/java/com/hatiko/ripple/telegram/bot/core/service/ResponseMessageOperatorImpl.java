package com.hatiko.ripple.telegram.bot.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseMessageOperatorImpl implements ResponseMessageOperator {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;

	public ResponseMessageOperatorImpl(@Lazy XrpLongPollingBot xrpLongPollingBot, ActionProperties actionProperties,
			KeyboardPreparator keyboardPreparator) {
		this.xrpLongPollingBot = xrpLongPollingBot;
		this.actionProperties = actionProperties;
		this.keyboardPreparator = keyboardPreparator;
	}

	@Override
	public Integer responseStart(String firstName, Long chatId) {

		String text = String.format("Hello, %s", firstName);

		return xrpLongPollingBot.sendMessage(chatId, text, keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseHello(String firstName, Long chatId) {

		String text = String.format("Hello, %s", firstName);

		return xrpLongPollingBot.sendMessage(chatId, text, keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseMain(Long chatId) {

		String text = "You are at main now";

		return xrpLongPollingBot.sendMessage(chatId, text, keyboardPreparator.getMainKeyboard());
	}

	@Override
	public Integer responseNext(Long chatId) {

		String text = "You stay unlogged in";
		return xrpLongPollingBot.sendMessage(chatId, text, keyboardPreparator.getMainKeyboard());
	}

	@Override
	public Integer responseHelp(Long chatId) {

		String text = "We will help you";
		return xrpLongPollingBot.sendMessage(chatId, text, null);
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
			responseMessage = "Insert your wallet (public key)";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = String.format("Your balance is %s", ((BalanceResponse) responseObject).getAmount());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetBalance(), chatId);
	}

	@Override
	public Integer responseGetLastTransactions(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = "Insert your walletAddress (public key)";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = "Insert number of transactions";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			List<TransactionResponse> transactions = (ArrayList<TransactionResponse>) responseObject;
			responseMessage = String.format("Num of transactions is %s", transactions.size());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetLastTransactions(), chatId);
	}

	@Override
	public Integer responseWithdraw(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = "Insert your wallet adress(public key)";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = "Insert private key";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			responseMessage = "Insert distanation account(public key)";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(3)) {
			responseMessage = "Insert memo";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(4)) {
			responseMessage = "Insert sum (min ->0.000001)";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		return responseErrorMessage(actionProperties.getMethodName().getWithdraw(), chatId);
	}

	@Override
	public Integer responseGetTransactionInfo(Object responseObject, Long chatId, Integer operationCounter) {

		String responseMessage;
		
		if (operationCounter.equals(0)) {
			responseMessage = "Insert transaction hash";
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = String.format("Sum of transaction is %s",
					((TransactionResponse) responseObject).getAmount());
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, keyboardPreparator.getMainKeyboard());
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetTransactionInfo(), chatId);
	}

	@Override
	public Integer responseErrorMessage(String operation, Long chatId) {
		// TODO Auto-generated method stub
		return null;
	}
}
