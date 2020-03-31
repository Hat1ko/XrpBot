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

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("telegram.bot.message.response")
public class ResponseMessageProperties {

	private List<String> getBalanceList;
	private List<String> getTransactionInfoList;
	private List<String> getLastTransactionsList;	
	private List<String> withdrawList;
	
	@Getter
	private String start;
	@Getter
	private String hello;
	@Getter
	private String next;
	@Getter
	private String help;
	@Getter
	private String main;
	@Getter
	private String logIn;
	@Getter
	private String register;
	private String getBalance;
	private String getTransactionInfo;
	private String getLastTransactions;
	private String withdraw;
	@Getter
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
