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
@ConfigurationProperties("telegram.bot.message.response")
public class MessageResponseProperties {

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
}
