package com.hatiko.ripple.telegram.bot.core.transformer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.hatiko.ripple.telegram.bot.core.model.TelegramUpdate;

@Component
public class UpdateToTelegramUpdateTransformer implements Transformer<Update, TelegramUpdate>{

	@Override
	public TelegramUpdate transform(Update update) {

		return TelegramUpdate.builder()
				.id(update.getUpdateId())
				.creationDate(LocalDateTime.now())
				.build();
	}
}
