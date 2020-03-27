package com.hatiko.ripple.telegram.bot.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.properties.CommandProperties;
import com.hatiko.ripple.telegram.bot.core.properties.XrpBotProperties;
import com.hatiko.ripple.telegram.bot.core.service.TelegramUpdateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequiredArgsConstructor
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot {

//	@Lazy
//	@Autowired
	private final List<TelegramMessageHandler> telegramMessageHandlers;

	private final TelegramUpdateService telegramUpdateService;
	private final XrpBotProperties xrpBotProperties;
	private final CommandProperties commandProperties;

	// instead of requiredArgcConstructor as cycling so obliged to use lazy
	// invokation
	@Autowired
	public XrpLongPollingBot(@Lazy List<TelegramMessageHandler> telegramMessageHandlers,
			TelegramUpdateService telegramUpdateService, XrpBotProperties xrpBotProperties,
			CommandProperties commandProperties) {

		this.telegramMessageHandlers = telegramMessageHandlers;
		this.telegramUpdateService = telegramUpdateService;
		this.xrpBotProperties = xrpBotProperties;
		this.commandProperties = commandProperties;
	}

	@Override
	public void onUpdateReceived(Update update) {
		TelegramUpdate telegramUpdate = telegramUpdateService.save(update);
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
