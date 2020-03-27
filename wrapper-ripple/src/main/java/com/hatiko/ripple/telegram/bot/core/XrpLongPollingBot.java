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
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.properties.XrpBotProperties;
import com.hatiko.ripple.telegram.bot.core.transformer.Transformer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot {

	private final List<TelegramMessageHandler> telegramMessageHandlers;

	private final XrpBotProperties xrpBotProperties;
	private final ActionProperties commandProperties;
	private final Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer;

	// instead of requiredArgcConstructor as cycling so obliged to use lazy
	// invokation
	@Autowired
	public XrpLongPollingBot(@Lazy List<TelegramMessageHandler> telegramMessageHandlers,
			XrpBotProperties xrpBotProperties, ActionProperties commandProperties,
			Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer) {

		this.telegramMessageHandlers = telegramMessageHandlers;
		this.xrpBotProperties = xrpBotProperties;
		this.commandProperties = commandProperties;
		this.updateToTelegramUpdateTransformer = updateToTelegramUpdateTransformer;
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
		KeyboardButton HelloButton = new KeyboardButton(commandProperties.getCommand().getHello());
		HelloButton.setText(commandProperties.getButton().getHello());
		firstKeyboardRow.add(HelloButton);

		KeyboardRow secondKeyboardRow = new KeyboardRow();
		KeyboardButton HelpButton = new KeyboardButton(commandProperties.getCommand().getHelp());
		HelpButton.setText(commandProperties.getButton().getHelp());
		secondKeyboardRow.add(HelpButton);

		keyboard.add(firstKeyboardRow);
		keyboard.add(secondKeyboardRow);
		keyboardMarkup.setKeyboard(keyboard);

		return keyboardMarkup;
	}
}
