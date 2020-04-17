package com.hatiko.ripple.telegram.bot;

import com.hatiko.ripple.telegram.bot.dto.telegram.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.properties.XrpBotProperties;
import com.hatiko.ripple.telegram.bot.transformer.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class XrpLongPollingBot extends TelegramLongPollingBot {

	private final List<TelegramMessageHandler> telegramMessageHandlers;

	private final XrpBotProperties xrpBotProperties;
	private final Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer;

	@Autowired
	public XrpLongPollingBot(List<TelegramMessageHandler> telegramMessageHandlers, XrpBotProperties xrpBotProperties,
			Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer) {

		this.telegramMessageHandlers = telegramMessageHandlers;
		this.xrpBotProperties = xrpBotProperties;
		this.updateToTelegramUpdateTransformer = updateToTelegramUpdateTransformer;
	}

	@Override
	public void onUpdateReceived(Update update) {

		log.info("Update recived | chatId : {}", update.getMessage().getChatId());
		TelegramUpdate telegramUpdate = updateToTelegramUpdateTransformer.transform(update);
		telegramMessageHandlers.forEach(telegramMessageHandler -> telegramMessageHandler.handle(telegramUpdate));
	}

	@Override
	public String getBotUsername() {
		
		log.info("Returning bot username");
		return xrpBotProperties.getUsername();
	}

	@Override
	public String getBotToken() {
		
		log.info("Returning bot token");
		return xrpBotProperties.getToken();
	}

	public synchronized Integer sendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboard) {

		log.info("Sending message | chatId : {}, text : {}", chatId, text);
		
		SendMessage sendMessage = new SendMessage();

		sendMessage.enableMarkdown(Boolean.TRUE);
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);
		sendMessage.setReplyMarkup(keyboard);

		try {
			return execute(sendMessage).getMessageId();
		} catch (TelegramApiException e) {
			log.error("Error while sending message : {}", e.getMessage());
		}
		return null;
	}

	public synchronized Boolean deleteMessage(Long chatId, Integer messageId) {
		
		log.info("Deleting message | chatId : {}, messageId : {}", chatId, messageId);
		
		DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);

		try {
			return execute(deleteMessage);
		} catch (TelegramApiException e) {
			log.error("Error while deleting message : {}", e.getMessage());
		}
		return null;
	}
}
