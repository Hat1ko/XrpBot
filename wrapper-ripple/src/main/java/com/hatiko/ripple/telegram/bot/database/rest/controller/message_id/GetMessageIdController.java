package com.hatiko.ripple.telegram.bot.database.rest.controller.message_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class GetMessageIdController {

	private final XrpDatabaseOperator databaseOperator;

	@GetMapping
	public ResponseEntity<MessageIdDTO> getMessageId(@RequestParam("chat_id") Long chatId) {

		log.info("GET request got messageId | chatId : {}", chatId);

		MessageIdDTO response = databaseOperator.getMessageId(chatId);

		log.info("GET Response | chatId : {}, lastSent : {}, lastDeleted : {}", response.getCahtId(),
				response.getLastSent(), response.getLastDeleted());

		return ResponseEntity.ok(response);
	}
}
