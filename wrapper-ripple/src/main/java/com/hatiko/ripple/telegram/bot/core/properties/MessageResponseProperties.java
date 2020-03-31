package com.hatiko.ripple.telegram.bot.core.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("telegram.bot.message.response")
public class MessageResponseProperties {

	private List<String> getBalanceList;
	private List<String> getTransactionInfoList;
	private List<String> getLastTransactionsList;	
	private List<String> withdrawList;
	
	private String start;
	private String hello;
	private String next;
	private String help;
	private String main;
	private String logIn;
	private String register;
	private String getBalance;
	private String generateMemo;
	private String getTransactionInfo;
	private String getLastTransactions;
	private String withdraw;
	private String error;
	
	private String separator; 

	@PostConstruct
	private void prepareLists() {
		
		getBalanceList = Arrays.asList(getBalance.split(separator));
		getTransactionInfoList = Arrays.asList(getTransactionInfo.split(separator));
		getLastTransactionsList = Arrays.asList(getLastTransactions.split(separator));
		withdrawList = Arrays.asList(withdraw.split(separator));
	}
	
	public List<String> getGetBalance(){
		return getBalanceList;
	}
	
	public List<String> getGetTransactionInfo(){
		return getTransactionInfoList;
	}
	
	public List<String> getGetLastTransactions(){
		return getLastTransactionsList;
	}
	
	public List<String> getWithdraw(){
		return withdrawList;
	}
}
