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
	private Command command;
	private Button button;
	
	@PostConstruct
	private void prepareCommandsList() {
		
		commands = new ArrayList<>();
		
		commands.add(command.getStart());
		commands.add(button.getHello());
		commands.add(button.getHelp());
		commands.add(button.getNext());
		commands.add(button.getMain());
		commands.add(button.getLogIn());
		commands.add(button.getRegister());
		commands.add(button.getGetBalance());
		commands.add(button.getGenerateMemo());
		commands.add(button.getGetTransactionInfo());
		commands.add(button.getGetLastTransactions());
		commands.add(button.getWithdraw());
	}
	
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Command {
		
		private String start;
		private String hello;
		private String help;
		private String next;
		private String main;
		private String register;
		private String logIn;
		private String getBalance;
		private String generateMemo;
		private String getTransactionInfo;
		private String getLastTransactions;
		private String withdraw;
	}
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Button {
		
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

