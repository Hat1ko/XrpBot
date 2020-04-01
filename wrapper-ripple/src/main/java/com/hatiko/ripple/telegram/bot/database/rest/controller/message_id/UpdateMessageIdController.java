package com.hatiko.ripple.telegram.bot.database.rest.controller.message_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.dto.MessageIdDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/message-id")
public class UpdateMessageIdController {

	private final XrpDatabaseOperator databaseOperator;

	@PutMapping
	public ResponseEntity<MessageIdDTO> updateMessageId(@RequestParam(name = "chat_id") Long chatId,
			@RequestParam(name = "last_sent", required = false) Integer lastSent,
			@RequestParam(name = "last_deleted", required = false) Integer lastDeleted) {

		MessageIdDTO response = databaseOperator.updateMessageId(chatId, lastSent, lastDeleted);
		return ResponseEntity.ok(response);
	}
}
