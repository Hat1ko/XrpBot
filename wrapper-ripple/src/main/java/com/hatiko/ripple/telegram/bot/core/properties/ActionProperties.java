package com.hatiko.ripple.telegram.bot.core.properties;

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

	private Command command;
	private Button button;
	
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
		private String logIn;
		private String getBalance;
		private String generateMemo;
		private String getTransactionInfo;
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
		private String logIn;
		private String getBalance;
		private String generateMemo;
		private String getTransactionInfo;
		private String withdraw;
	}
}

