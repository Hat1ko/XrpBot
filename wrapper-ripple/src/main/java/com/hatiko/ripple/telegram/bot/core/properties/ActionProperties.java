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
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("telegram.bot.action")
public class ActionProperties {

	private List<String> commands;
	private ButtonOperation buttonOperation;
	
	@PostConstruct
	private void prepareCommandsList() {
		
		commands = new ArrayList<>();
		
		commands.add(buttonOperation.getStart());
		commands.add(buttonOperation.getHello());
		commands.add(buttonOperation.getHelp());
		commands.add(buttonOperation.getNext());
		commands.add(buttonOperation.getMain());
		commands.add(buttonOperation.getLogIn());
		commands.add(buttonOperation.getRegister());
		commands.add(buttonOperation.getGetBalance());
		commands.add(buttonOperation.getGenerateMemo());
		commands.add(buttonOperation.getGetTransactionInfo());
		commands.add(buttonOperation.getGetLastTransactions());
		commands.add(buttonOperation.getWithdraw());
	}
	
	@Getter
	@Setter
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
	
}

