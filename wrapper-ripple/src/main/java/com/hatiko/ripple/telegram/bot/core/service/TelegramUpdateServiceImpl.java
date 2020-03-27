package com.hatiko.ripple.telegram.bot.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.hatiko.ripple.telegram.bot.core.model.TelegramChat;
import com.hatiko.ripple.telegram.bot.core.model.TelegramMessage;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUser;
import com.hatiko.ripple.telegram.bot.core.repository.TelegramChatRepository;
import com.hatiko.ripple.telegram.bot.core.repository.TelegramMessageRepository;
import com.hatiko.ripple.telegram.bot.core.repository.TelegramUpdateRepository;
import com.hatiko.ripple.telegram.bot.core.repository.TelegramUserRepository;
import com.hatiko.ripple.telegram.bot.core.transformer.ChatToTelegramChatTransformer;
import com.hatiko.ripple.telegram.bot.core.transformer.Transformer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TelegramUpdateServiceImpl implements TelegramUpdateService {

	private final Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer;
	private final Transformer<Message, TelegramMessage> messageToTelegramMessageTransformer;
	private final Transformer<User, TelegramUser> userToTelegramUserTransformer;
	private final Transformer<Chat, TelegramChat> chatToTelegramChatTransformer;

	private final TelegramUpdateRepository telegramUpdateRepository;
	private final TelegramMessageRepository telegramMessageRepository;
	private final TelegramUserRepository telegramUserRepository;
	private final TelegramChatRepository telegramChatRepository;

	@Override
	public TelegramUpdate save(Update update) {

		TelegramUser telegramUser = telegramUserRepository.findById(update.getMessage().getFrom().getId())
				.orElseGet(() -> telegramUserRepository
						.save(userToTelegramUserTransformer.transform(update.getMessage().getFrom())));

		TelegramChat telegramChat = telegramChatRepository.findById(update.getMessage().getChat().getId())
				.orElseGet(() -> {
					TelegramChat transformedChat = chatToTelegramChatTransformer
							.transform(update.getMessage().getChat());
					transformedChat.setUser(telegramUser);
					return telegramChatRepository.save(transformedChat);
				});
		
		TelegramMessage telegramMessage = messageToTelegramMessageTransformer.transform(update.getMessage());
		telegramMessage.setFrom(telegramUser);
		telegramMessage.setChat(telegramChat);
		
		//mb gonna use later
		/*TelegramMessage savedTelegramMessage =*/ telegramMessageRepository.save(telegramMessage);
		
		TelegramUpdate telegramUpdate = updateToTelegramUpdateTransformer.transform(update);
		telegramUpdate.setMessage(telegramMessage);
		
		return telegramUpdateRepository.save(telegramUpdate);
	}
}
