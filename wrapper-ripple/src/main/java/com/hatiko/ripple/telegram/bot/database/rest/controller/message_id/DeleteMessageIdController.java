package com.hatiko.ripple.telegram.bot.database.rest.controller.message_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.telegram.bot.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.telegram.bot.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "db/message-id")
public class DeleteMessageIdController {

	private final XrpDatabaseOperator databaseOperator;
	
	@DeleteMapping
	public ResponseEntity<StatusDTO> deleteMessageId(@RequestParam("chat_id") Long chatId){
		
		log.info("DELETE request for message_id | chatId : {}", chatId);
		
		Boolean status = databaseOperator.deleteMessageId(chatId);
		
		log.info("DELETE response | Deletion status is {}", status);
		
		StatusDTO response = StatusDTO.builder().status(status).build();
		
		return ResponseEntity.ok(response);
	}
}
