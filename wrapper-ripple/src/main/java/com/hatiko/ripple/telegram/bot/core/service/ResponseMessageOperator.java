package com.hatiko.ripple.telegram.bot.core.service;

import java.util.List;

import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

public interface ResponseMessageOperator {
	
	Integer responseStart();
	Integer responseHello();
	Integer responseMain();
	Integer responseNext();
	Integer responseHelp();

	Integer responseLogIn();
	Integer responseRegister();
	
	Integer responseGenerateMemo(String walletMemo);
	Integer responseGetBalance(Double balance);
	Integer responseGetLastTransactions(List<TransactionResponse> transactionResponse);
	Integer responseWithdraw(TransactionResponse transactionResponse);
	
	Integer responseErrorMessage(String operation);
}
