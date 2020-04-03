package com.hatiko.ripple.telegram.bot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties("telegram.bot")
public class XrpBotProperties {

	private String username;
	private String token;
	private Integer creatorId;
	private String url;
}
