package com.hatiko.ripple.telegram.bot.dto.session;

import java.time.LocalDateTime;

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
public class ChatSession {

	private Long chatId;
	private String username;
	private String publicKey;
	private String privateKey;
	private LocalDateTime creationTime;
}
