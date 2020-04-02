package com.hatiko.ripple.telegram.bot.service;

public interface ResponseMessageOperator {
	
	Integer responseStart(String firstName, Long chatId);
	Integer responseHello(String firstName, Long chatId);
	Integer responseMain(Long chatId);
	Integer responseNext(Long chatId);
	Integer responseHelp(Long chatId);

	Integer responseLogIn(Long chatId, Integer operationCounter, Boolean logInStatus);
	Integer responseLogOut(Long chatId);
	Integer responseRegister(Long chatId, Integer operationCounter, Boolean registerStatus);
	
	Integer responseGenerateMemo(String walletMemo, Long chatId);
	Integer responseGetBalance(Object responseObject, Long chatId, Integer operationCounter);
	Integer responseGetLastTransactions(Object responseObject, Long chatId, Integer operationCounter);
	Integer responseWithdraw(Object responseObject, Long chatId, Integer operationCounter);
	Integer responseGetTransactionInfo(Object responseObject, Long chatId, Integer operationCounter);
	
	Integer responseErrorMessage(String operation, Long chatId);
}
