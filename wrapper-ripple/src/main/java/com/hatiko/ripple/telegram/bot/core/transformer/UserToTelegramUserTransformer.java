package com.hatiko.ripple.telegram.bot.core.transformer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import com.hatiko.ripple.telegram.bot.core.dto.TelegramUser;

@Component
public class UserToTelegramUserTransformer implements Transformer<User, TelegramUser> {

	@Override
	public TelegramUser transform(User user) {

		return TelegramUser.builder()
				.id(user.getId())
				.creationDate(LocalDateTime.now())
				.userName(user.getUserName())
				.bot(user.getBot())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.languageCode(user.getLanguageCode())
				.build();
	}
}
