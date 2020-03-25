package com.hatiko.ripple.telegram.bot.core.transformer;

import java.time.LocalDateTime;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.hatiko.ripple.telegram.bot.core.model.TelegramMessage;

public class MessageToTelegramMessageTransformer implements Transformer<Message, TelegramMessage> {

	@Override
	public TelegramMessage transform(Message message) {

		return TelegramMessage.builder()
				.id(message.getMessageId())
				.creationDate(LocalDateTime.now())
				.text(message.getText())
				.build();
	}
}
