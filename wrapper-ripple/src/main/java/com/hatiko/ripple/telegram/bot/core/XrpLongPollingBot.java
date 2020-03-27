package com.hatiko.ripple.telegram.bot.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.model.TelegramChat;
import com.hatiko.ripple.telegram.bot.core.model.TelegramMessage;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUser;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.properties.XrpBotProperties;
import com.hatiko.ripple.telegram.bot.core.transformer.Transformer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequiredArgsConstructor
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot {

//	@Lazy
//	@Autowired
	private final List<TelegramMessageHandler> telegramMessageHandlers;

//	private final TelegramUpdateService telegramUpdateService;
	private final XrpBotProperties xrpBotProperties;
	private final ActionProperties commandProperties;
	private final Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer;
	private final Transformer<Message, TelegramMessage> messageToTelegramMessageTransformer;
	private final Transformer<Chat, TelegramChat> chatToTelegramChatTransformer;
	private final Transformer<User, TelegramUser> userToTelegramUserTransformer;
	
	// instead of requiredArgcConstructor as cycling so obliged to use lazy
	// invokation
	@Autowired
	public XrpLongPollingBot(@Lazy List<TelegramMessageHandler> telegramMessageHandlers,
			XrpBotProperties xrpBotProperties, ActionProperties commandProperties, 
			Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer,
			Transformer<Message, TelegramMessage> messageToTelegramMessageTransformer,
			Transformer<Chat, TelegramChat> chatToTelegramChatTransformer,
			Transformer<User, TelegramUser> userToTelegramUserTransformer) {

		this.telegramMessageHandlers = telegramMessageHandlers;
		this.xrpBotProperties = xrpBotProperties;
		this.commandProperties = commandProperties;
		this.updateToTelegramUpdateTransformer = updateToTelegramUpdateTransformer;
		this.messageToTelegramMessageTransformer = messageToTelegramMessageTransformer;
		this.chatToTelegramChatTransformer = chatToTelegramChatTransformer;
		this.userToTelegramUserTransformer = userToTelegramUserTransformer;
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		TelegramUpdate telegramUpdate = updateToTelegramUpdateTransformer.transform(update);
		telegramMessageHandlers.forEach(telegramMessageHandler -> telegramMessageHandler.handle(telegramUpdate));
	}

	@Override
	public String getBotUsername() {
		return xrpBotProperties.getUsername();
	}

	@Override
	public String getBotToken() {
		return xrpBotProperties.getToken();
	}

	public synchronized void sendTextMessage(Long chatId, String text) {

		SendMessage sendMessage = new SendMessage();

		sendMessage.enableMarkdown(Boolean.TRUE);
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);

		sendMessage.setReplyMarkup(getCustomReplyKeyboardMarkup());

		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			log.error("TelegramApiException : {}", e.getMessage());
		}
	}

	private ReplyKeyboardMarkup getCustomReplyKeyboardMarkup() {

		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setSelective(Boolean.TRUE);
		keyboardMarkup.setResizeKeyboard(Boolean.TRUE);
		keyboardMarkup.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow firstKeyboardRow = new KeyboardRow();
		firstKeyboardRow.add(new KeyboardButton(commandProperties.getHello()));

		KeyboardRow secondKeyboardRow = new KeyboardRow();
		secondKeyboardRow.add(new KeyboardButton(commandProperties.getHelp()));

		keyboard.add(firstKeyboardRow);
		keyboard.add(secondKeyboardRow);
		keyboardMarkup.setKeyboard(keyboard);

		return keyboardMarkup;
	}
}
