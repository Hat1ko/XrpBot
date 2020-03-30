package com.hatiko.ripple.telegram.bot.core.service;

import java.util.List;

import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

public interface ResponseMessageOperator {
	
	Integer responseStart(String firstName, Long chatId);
	Integer responseHello(String firstName, Long chatId);
	Integer responseMain(Long chatId);
	Integer responseNext(Long chatId);
	Integer responseHelp(Long chatId);

	Integer responseLogIn(Long chatId);
	Integer responseRegister(Long chatId);
	
	Integer responseGenerateMemo(String walletMemo, Long chatId);
	Integer responseGetBalance(Object responseObject, Long chatId, Integer operationCounter);
	Integer responseGetLastTransactions(List<TransactionResponse> transactionResponse, Long chatId);
	Integer responseWithdraw(TransactionResponse transactionResponse, Long chatId);
	
	Integer responseErrorMessage(String operation, Long chatId);
}
