package com.hatiko.ripple.telegram.bot.database.converter;

import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.model.MessageIdEntity;

public final class MessageIdConverter {

	public static MessageIdDTO toDTO(MessageIdEntity entity) {
		return MessageIdDTO.builder().cahtId(entity.getChatId()).lastDeleted(entity.getLastDeleted())
				.lastSent(entity.getLastSent()).build();
	}

	public static MessageIdEntity toEntity(MessageIdDTO dto) {
		return MessageIdEntity.builder().chatId(dto.getCahtId()).lastDeleted(dto.getLastDeleted())
				.lastSent(dto.getLastDeleted()).build();
	}
}
