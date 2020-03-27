package com.hatiko.ripple.telegram.bot.core.properties;

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
public class CommandsProperties {

	private String hello;
	private String help;
	private String getBalance;
	private String generateMemo;
	private String getTransactionInfo;
	private String withdraw;
}
