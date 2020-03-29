package com.hatiko.ripple.telegram.bot.core.properties;

import java.util.ArrayList;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("telegram.bot.action")
public class ActionProperties {

	private List<String> buttonOperations;
	private List<String> methodNames;
	private ButtonOperation buttonOperation;
	private MethodName methodName;

	@PostConstruct
	private void prepare() {
		prepareCommandsList();
	}
	
	private void prepareCommandsList() {
		
		buttonOperations = new ArrayList<>();
		
		buttonOperations.add(buttonOperation.getStart());
		buttonOperations.add(buttonOperation.getHello());
		buttonOperations.add(buttonOperation.getHelp());
		buttonOperations.add(buttonOperation.getNext());
		buttonOperations.add(buttonOperation.getMain());
		buttonOperations.add(buttonOperation.getLogIn());
		buttonOperations.add(buttonOperation.getRegister());
		buttonOperations.add(buttonOperation.getGetBalance());
		buttonOperations.add(buttonOperation.getGenerateMemo());
		buttonOperations.add(buttonOperation.getGetTransactionInfo());
		buttonOperations.add(buttonOperation.getGetLastTransactions());
		buttonOperations.add(buttonOperation.getWithdraw());
	}
	
	private void prepareMethodNames() {
		
		methodNames = new ArrayList<>();
		
		methodNames.add(methodName.getStart());
		methodNames.add(methodName.getHello());
		methodNames.add(methodName.getHelp());
		methodNames.add(methodName.getNext());
		methodNames.add(methodName.getMain());
		methodNames.add(methodName.getLogIn());
		methodNames.add(methodName.getRegister());
		methodNames.add(methodName.getGetBalance());
		methodNames.add(methodName.getGenerateMemo());
		methodNames.add(methodName.getGetTransactionInfo());
		methodNames.add(methodName.getGetLastTransactions());
		methodNames.add(methodName.getWithdraw());
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ButtonOperation {
		
		private String start;
		private String hello;
		private String help;
		private String next;
		private String main;
		private String logIn;
		private String register;
		private String getBalance;
		private String generateMemo;
		private String getTransactionInfo;
		private String getLastTransactions;
		private String withdraw;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MethodName {
		
		private String start;
		private String hello;
		private String help;
		private String next;
		private String main;
		private String logIn;
		private String register;
		private String getBalance;
		private String generateMemo;
		private String getTransactionInfo;
		private String getLastTransactions;
		private String withdraw;
	}
}

