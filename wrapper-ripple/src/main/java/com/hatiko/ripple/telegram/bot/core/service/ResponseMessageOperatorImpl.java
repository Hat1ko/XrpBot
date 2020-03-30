package com.hatiko.ripple.telegram.bot.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResponseMessageOperatorImpl implements ResponseMessageOperator {
	
	@Override
	public Integer responseStart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseHello() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseMain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseLogIn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseRegister() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseGenerateMemo(String walletMemo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseGetBalance(Double balance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseGetLastTransactions(List<TransactionResponse> transactionResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseWithdraw(TransactionResponse transactionResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer responseErrorMessage(String operation) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
