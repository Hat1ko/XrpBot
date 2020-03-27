package com.hatiko.ripple.telegram.bot.core.transformer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.hatiko.ripple.telegram.bot.core.dto.TelegramChat;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramMessage;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UpdateToTelegramUpdateTransformer implements Transformer<Update, TelegramUpdate>{

	private final Transformer<Message, TelegramMessage> messageToTelegramMessageTransformer;
	private final Transformer<Chat, TelegramChat> chatToTelegramChatTransformer;
	private final Transformer<User, TelegramUser> userToTelegramUserTransformer;
	
	@Override
	public TelegramUpdate transform(Update update) {

		TelegramUpdate telegramUpdate = TelegramUpdate.builder()
				.id(update.getUpdateId())
				.creationDate(LocalDateTime.now())
				.build();
		
		Message message = update.getMessage();
		TelegramMessage telegramMessage = messageToTelegramMessageTransformer.transform(message);
		
		Chat chat = message.getChat();
		TelegramChat telegramChat = chatToTelegramChatTransformer.transform(chat); 
		
		User user = message.getFrom();
		TelegramUser telegramUser = userToTelegramUserTransformer.transform(user);
		
		telegramChat.setUser(telegramUser);
		
		telegramMessage.setChat(telegramChat);
		telegramMessage.setFrom(telegramUser);
		
		telegramUpdate.setMessage(telegramMessage);
		
		return telegramUpdate;
	}
}
